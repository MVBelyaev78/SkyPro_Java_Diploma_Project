package ru.skypro.homework.component.mapping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.entity.AdvertisementEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.AdvertisementRepository;
import ru.skypro.homework.repository.UserRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentMapping {
    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;

    public Comment fromEntity(CommentEntity entity) {
        UserEntity author = entity.getIdAuthor();
        return new Comment(
                author.getId(),
                author.getPhone(),
                author.getFirstName(),
                entity.getDtCreateAsMillis(),
                entity.getIdComment(),
                entity.getNmText()
        );
    }

    public Comments fromEntities(List<CommentEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return new Comments(0, new ArrayList<>());
        }
        final List<Comment> result = entities
                .stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());

        return new Comments(result.size(), result);
    }

    public Optional<CommentEntity> toEntity(Comment comment, Long adId) {
        final Optional<UserEntity> author = userRepository.findById(comment.getAuthor());
        final Optional<AdvertisementEntity> advertisement = advertisementRepository.findById(adId);

        if (author.isEmpty() || advertisement.isEmpty()) {
            return Optional.empty();
        }

        CommentEntity entity = new CommentEntity();
        entity.setNmText(comment.getText());
        entity.setIdAuthor(author.get());
        entity.setIdAdvertisement(advertisement.get());
        entity.setDtCreate(ZonedDateTime.now(ZoneId.of("Europe/Moscow")));

        return Optional.of(entity);
    }
}
