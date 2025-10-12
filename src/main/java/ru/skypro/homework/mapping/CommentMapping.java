package ru.skypro.homework.mapping;

import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.entity.AdvertisementEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;

import java.time.Instant;

public class CommentMapping {

    public static Comment fromEntity(CommentEntity entity) {
        UserEntity author = entity.getIdAuthor();
        return new Comment(
                author.getId_user(),
                author.getImagePath(),
                author.getFirstName(),
                entity.getDtCreateAsMillis(),
                entity.getIdComment(),
                entity.getNmText()
        );
    }

    public CommentEntity toEntity(Comment comment, Integer adId) {
        UserEntity author = userRepository.findById(comment.getAuthor()).orElseThrow();
        AdvertisementEntity advertisement = advertisementRepository.findById(adId).orElseThrow();

        CommentEntity entity = new CommentEntity();
        entity.setNmText(comment.getText());
        entity.setIdAuthor(author);
        entity.setIdAdvertisement(advertisement);
        entity.setDtCreate(Instant.now());

        return entity;
    }
}
