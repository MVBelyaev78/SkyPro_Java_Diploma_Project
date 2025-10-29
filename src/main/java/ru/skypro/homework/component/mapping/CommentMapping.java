package ru.skypro.homework.component.mapping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.AdvertisementEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.AdvertisementRepository;
import ru.skypro.homework.repository.UserRepository;

import java.time.ZonedDateTime;
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
            return new Comments(0, List.of());
        }
        final List<Comment> result = entities
                .stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());

        return new Comments(result.size(), result);
    }

    public Optional<CommentEntity> toEntity(CreateOrUpdateComment comment,
                                            AdvertisementEntity advertisementEntity,
                                            UserEntity userEntity,
                                            ZonedDateTime dateTime) {
        if (comment == null || advertisementEntity == null || userEntity == null || dateTime == null) {
            return Optional.empty();
        }
        CommentEntity entity = new CommentEntity();
        entity.setNmText(comment.getText());
        entity.setIdAuthor(userEntity);
        entity.setIdAdvertisement(advertisementEntity);
        entity.setDtCreate(dateTime);

        return Optional.of(entity);
    }

    public Optional<CreateOrUpdateComment> fromComment(Comment comment) {
        if (comment == null) {
            return Optional.empty();
        }
        return Optional.of(new CreateOrUpdateComment(comment.getText()));
    }
}
