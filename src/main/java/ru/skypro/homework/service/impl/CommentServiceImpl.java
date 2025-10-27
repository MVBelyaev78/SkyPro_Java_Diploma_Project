package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.AdvertisementEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.ResourceNotFoundException;
import ru.skypro.homework.component.mapping.CommentMapping;
import ru.skypro.homework.repository.AdvertisementRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;
    private final CommentRepository commentRepository;
    private final CommentMapping commentMapping;

    /**
     * Получение комментариев для объявления по его идентификатору.
     *
     * @param adId идентификатор объявления
     * @return объект Comments, содержащий количество и список комментариев
     */
    @Override
    public Comments getComments(Long adId) {
        log.info("Получение комментариев для объявления с ID: {}", adId);

        List<CommentEntity> commentEntities = commentRepository.findAllByIdAdvertisement_Id(adId);
        List<Comment> comments = commentEntities.stream()
                .map(commentMapping::fromEntity)
                .collect(Collectors.toList());

        return new Comments(comments.size(), comments);
    }

    /**
     * Добавление нового комментария к объявлению.
     *
     * @param adId идентификатор объявления
     * @param comment объект CreateOrUpdateComment с данными нового комментария
     * @return созданный комментарий
     */
    @Override
    public Comment addComment(Long adId, CreateOrUpdateComment comment) {
        log.info("Добавление комментария к объявлению с ID: {}", adId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntity author = userRepository.findByEmail(username);
        if (author == null) {
            throw new ResourceNotFoundException("Пользователь не найден");
        }
        AdvertisementEntity advertisement = advertisementRepository.findById(adId)
                .orElseThrow(() -> new RuntimeException("Объявление с ID " + adId + " не найдено"));

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setNmText(comment.getText());
        commentEntity.setDtCreate(ZonedDateTime.now(ZoneId.of("Europe/Moscow")));
        commentEntity.setIdAdvertisement(advertisement);
        commentEntity.setIdAuthor(author);

        CommentEntity savedComment = commentRepository.save(commentEntity);
        log.info("Комментарий успешно добавлен");

        return commentMapping.fromEntity(savedComment);
    }

    /**
     * Удаление комментария по его идентификатору.
     *
     * @param adId идентификатор объявления (не используется в данной реализации)
     * @param commentId идентификатор комментария для удаления
     */
    @Override
    public void rmComment(Long adId, Long commentId) {
        //comments.removeIf(comment -> comment.getId() == commentId);
    }

    /**
     * Обновление существующего комментария.
     *
     * @param adId идентификатор объявления (не используется в данной реализации)
     * @param commentId идентификатор комментария для обновления
     * @param comment объект CreateOrUpdateComment с новыми данными комментария
     * @return обновленный комментарий или null, если комментарий не найден
     */
    @Override
    public Comment updateComment(Long adId, Long commentId, CreateOrUpdateComment comment) {
        log.info("Обновление комментария с ID: {} для объявления с ID: {}", commentId, adId);

        CommentEntity commentEntity = commentRepository.findById(Math.toIntExact(commentId))
                .orElseThrow(() -> new RuntimeException("Комментарий с ID " + commentId + " не найден"));

        if (!commentEntity.getIdAdvertisement().getId().equals((long) adId)) {
            throw new RuntimeException("Комментарий не принадлежит указанному пользователю");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity currentUser = userRepository.findByEmail(username);
        if (currentUser == null) {
            throw new ResourceNotFoundException("Пользователь не найден");
        }
        commentEntity.setNmText(comment.getText());

        CommentEntity updatedComment = commentRepository.save(commentEntity);
        log.info("Комментарий с ID: {} успешно сохранен", commentId);

        return commentMapping.fromEntity(updatedComment);
    }
}
