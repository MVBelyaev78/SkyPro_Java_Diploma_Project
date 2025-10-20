package ru.skypro.homework.service.impl;

import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentService;

import java.util.ArrayList;
import java.util.List;

public class CommentServiceImpl implements CommentService {
    private final List<Comment> comments = new ArrayList<>(); // Хранилище для комментариев

    @Override
    public Comments getComments(int id) {
        // Реализация получения комментариев для объявления
        // Здесь нужно вернуть все комментарии для указанного объявления
        return new Comments(comments.size(), comments);
    }

    @Override
    public Comment addComment(int id, CreateOrUpdateComment comment) {
        // Реализация добавления комментария
        Comment newComment = new Comment(); // Создание нового комментария
        newComment.setId(comments.size() + 1); // Установка уникального ID
        newComment.setText(comment.getText()); // Установка текста комментария
        comments.add(newComment); // Добавление комментария в хранилище
        return newComment; // Возврат созданного комментария
    }

    @Override
    public void rmComment(int adId, int commentId) {
        // Реализация удаления комментария
        comments.removeIf(comment -> comment.getId() == commentId); // Удаление комментария по ID
    }

    @Override
    public Comment updateComment(int adId, int commentId, CreateOrUpdateComment comment) {
        // Реализация обновления комментария
        for (Comment existingComment : comments) {
            if (existingComment.getId() == commentId) {
                existingComment.setText(comment.getText()); // Обновление текста комментария
                return existingComment; // Возврат обновленного комментария
            }
        }
        return null; // Возврат null, если комментарий не найден
    }
}
