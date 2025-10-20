package ru.skypro.homework.service.impl;

import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentService;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация сервиса комментариев.
 */
public class CommentServiceImpl implements CommentService {
    private final List<Comment> comments = new ArrayList<>(); // Хранилище для комментариев

    /**
     * Получение комментариев для объявления по его идентификатору.
     *
     * @param id идентификатор объявления
     * @return объект Comments, содержащий количество и список комментариев
     */
    @Override
    public Comments getComments(int id) {

        return new Comments(comments.size(), comments);
    }

    /**
     * Добавление нового комментария к объявлению.
     *
     * @param id идентификатор объявления
     * @param comment объект CreateOrUpdateComment с данными нового комментария
     * @return созданный комментарий
     */
    @Override
    public Comment addComment(int id, CreateOrUpdateComment comment) {

        Comment newComment = new Comment();
        newComment.setId(comments.size() + 1);
        newComment.setText(comment.getText());
        comments.add(newComment);
        return newComment;
    }

    /**
     * Удаление комментария по его идентификатору.
     *
     * @param adId идентификатор объявления (не используется в данной реализации)
     * @param commentId идентификатор комментария для удаления
     */
    @Override
    public void rmComment(int adId, int commentId) {

        comments.removeIf(comment -> comment.getId() == commentId);
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
    public Comment updateComment(int adId, int commentId, CreateOrUpdateComment comment) {

        for (Comment existingComment : comments) {
            if (existingComment.getId() == commentId) {
                existingComment.setText(comment.getText());
                return existingComment;
            }
        }
        return null;
    }
}
