package ru.skypro.homework.service;

public interface AuthorizationService {
    boolean isCommentAuthor(int commentId);

    boolean isAdAuthor(Long adId);
}
