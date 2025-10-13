package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentService;

import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {

    @Override
    public Comments getComments(int id) {
        List<Comment> response = List.of(
                new Comment(1L, "img-url", "firstname", 999L, 1, "text")
        );

        return new Comments(response.size(), response);
    }

    @Override
    public Comment addComment(int id, CreateOrUpdateComment comment) {
        Comment response = new Comment(1L, "img-url", "firstname", 999L, 1, comment.getText());

        return response;
    }

    @Override
    public void rmComment(int adId, int commentId) {
    }

    @Override
    public Comment updateComment(int adId, int commentId, CreateOrUpdateComment comment) {
        Comment response = new Comment(1L, "img-url", "firstname", 999L, commentId, comment.getText());

        return response;
    }
}
