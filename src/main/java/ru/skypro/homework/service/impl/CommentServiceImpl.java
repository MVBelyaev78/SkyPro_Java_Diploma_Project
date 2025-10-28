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

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

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

        return commentMapping.fromEntities(commentRepository.findAllByIdAdvertisement_Id(adId));
    }

    /**
     * Добавление нового комментария к объявлению.
     *
     * @param adId    идентификатор объявления
     * @param comment объект CreateOrUpdateComment с данными нового комментария
     * @return созданный комментарий
     */
    @Override
    public Optional<Comment> addCommentUser(Long adId, CreateOrUpdateComment comment, UserEntity userEntity) {
        log.info("Добавление комментария к объявлению с ID: {}", adId);

        final Optional<AdvertisementEntity> advertisementEntity = advertisementRepository.findById(adId);
        if (advertisementEntity.isEmpty()) {
            return Optional.empty();
        }
        final Optional<CommentEntity> commentEntity = commentMapping.toEntity(
                comment, advertisementEntity.get(), userEntity, ZonedDateTime.now(ZoneId.of("Europe/Moscow")));
        if (commentEntity.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(commentMapping.fromEntity(commentRepository.save(commentEntity.get())));
        /*return advertisementRepository
                .findById(adId)
                .flatMap(adEntity -> commentMapping
                        .toEntity(comment, adEntity, userEntity, ZonedDateTime.now(ZoneId.of("Europe/Moscow")))
                        .map(e -> commentMapping.fromEntity(commentRepository.save(e))));*/

    }

    @Override
    public Optional<Comment> addComment(Long adId, CreateOrUpdateComment comment) {
        return addCommentUser(adId,
                comment,
                userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @Override
    public Optional<Comment> testMethod(Long adId,
                                        CreateOrUpdateComment createComment,
                                        UserEntity userEntity,
                                        ZonedDateTime dateTime) {
        final Optional<AdvertisementEntity> advertisementEntity = advertisementRepository.findById(adId);
        if (advertisementEntity.isEmpty()) {
            return Optional.empty();
        }
        final Optional<CommentEntity> commentEntity = commentMapping.toEntity(
                createComment,
                advertisementEntity.get(),
                userEntity,
                dateTime);
        if (commentEntity.isEmpty()) {
            return Optional.empty();
        }
        final Optional<CommentEntity> savedCommentEntity = Optional.of(commentRepository.save(commentEntity.get()));
        if (savedCommentEntity.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(commentMapping.fromEntity(savedCommentEntity.get()));
    }

    /**
     * Удаление комментария по его идентификатору.
     *
     * @param adId      идентификатор объявления (не используется в данной реализации)
     * @param commentId идентификатор комментария для удаления
     */
    @Override
    public void rmComment(Long adId, Long commentId) {
        //comments.removeIf(comment -> comment.getId() == commentId);
    }

    /**
     * Обновление существующего комментария.
     *
     * @param adId      идентификатор объявления (не используется в данной реализации)
     * @param commentId идентификатор комментария для обновления
     * @param comment   объект CreateOrUpdateComment с новыми данными комментария
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
