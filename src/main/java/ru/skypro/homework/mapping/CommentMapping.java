package ru.skypro.homework.mapping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.entity.AdvertisementEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.AdvertisementRepository;
import ru.skypro.homework.repository.UserRepository;

import java.time.Instant;
import java.util.Optional;

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
        entity.setDtCreate(Instant.now());

        return Optional.of(entity);
    }
}
