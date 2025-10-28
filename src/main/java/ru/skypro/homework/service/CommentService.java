package ru.skypro.homework.service;

import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.AdvertisementEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Реализация сервиса для работы с комментариями к объявлениям
 *
 * @author Maxim
 * @version 1.0
 */
public interface CommentService {
    /**
     * Получить все комментарии для указанного объявления
     *
     * @param adId идентификатор объявления
     * @return {@link Comments} содержащий кол-во комментариев и коллекцию {@link Comment}
     */
    Comments getComments(Long adId);

    /**
     * Добавляет комментарий к объявлению
     *
     * @param adId       идентификатор объявления
     * @param comment    текст комментария
     * @param userEmail  E-Mail автора комментария
     * @param dateTime дата/время размещения комментария
     * @return {@link Comment} созданный комментарий
     */
    Optional<Comment> addCommentUserDateTime(Long adId,
                                             CreateOrUpdateComment comment,
                                             String userEmail,
                                             ZonedDateTime dateTime);

    Optional<Comment> addComment(Long adId, CreateOrUpdateComment comment);

    /**
     * Удаляет комментарий
     *
     * @param adId      идентификатор объявления
     * @param commentId идентификатор комментария
     */
    void rmComment(Long adId, Long commentId);

    /**
     * Обновляет комментарий
     *
     * @param adId      идентификатор объявления
     * @param commentId идентификатор комментария
     * @param comment   новый текст комментария
     * @return {@link Comment} обновленный комментарий
     */
    Comment updateComment(Long adId, Long commentId, CreateOrUpdateComment comment);
}
