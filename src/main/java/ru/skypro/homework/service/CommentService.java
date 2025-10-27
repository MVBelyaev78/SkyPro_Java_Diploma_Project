package ru.skypro.homework.service;

import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

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
     * @param adId      идентификатор объявления
     * @param comment текст комментария
     * @return {@link Comment} созданный комментарий
     */
    Comment addComment(Long adId, CreateOrUpdateComment comment);

    /**
     * Удаляет комментарий
     *
     * @param adId      идентификатор объявления
     * @param commentId идентификатор комментария
     */
    void rmComment(int adId, int commentId);

    /**
     * Обновляет комментарий
     *
     * @param adId      идентификатор объявления
     * @param commentId идентификатор комментария
     * @param comment   новый текст комментария
     * @return {@link Comment} обновленный комментарий
     */
    Comment updateComment(int adId, int commentId, CreateOrUpdateComment comment);
}
