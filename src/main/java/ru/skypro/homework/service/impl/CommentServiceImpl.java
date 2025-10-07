package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {

    @Override
    public Comments getComments(int id) {
        List<Comment> response = List.of(
                        new CommentEntity(1, "img-url", "firstname", 999, 1, "text")
                ).stream()
                .map(Comment::fromEntity)
                .collect(Collectors.toList());

        return new Comments(response.size(), response);
    }

    @Override
    public Comment addComment(int id, CreateOrUpdateComment comment) {
        Comment response = Comment.fromEntity(
                new CommentEntity(1, "img-url", "firstname", 999, 1, comment.getText())
        );

        return response;
    }

    @Override
    public void rmComment(int adId, int commentId) {
    }

    @Override
    public Comment updateComment(int adId, int commentId, CreateOrUpdateComment comment) {
        Comment response = Comment.fromEntity(
                new CommentEntity(1, "img-url", "firstname", 999, commentId, comment.getText())
        );

        return response;
    }
}
