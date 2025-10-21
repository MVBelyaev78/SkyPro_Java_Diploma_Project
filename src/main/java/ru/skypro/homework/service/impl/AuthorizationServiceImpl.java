package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.AdvertisementEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.AdvertisementRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AuthorizationService;

/**
 * Реализация сервиса авторизации, который проверяет права доступа пользователей
 * к комментариям и объявлениям.
 */
@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private final CommentRepository commentRepository;
    private final AdvertisementRepository advertisementRepository;
    private final CurrentUserServiceImpl currentUserService;

    /**
     * Проверяет, является ли текущий пользователь автором указанного комментария.
     *
     * @param commentId идентификатор комментария
     * @return true, если текущий пользователь является автором комментария, иначе false
     * @throws RuntimeException если комментарий не найден
     */
    public boolean isCommentAuthor(int commentId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Комментарий не найден"));

        UserEntity currentUser = currentUserService.getCurrentUser();
        return comment.getIdAuthor().getId().equals(currentUser.getId());
    }

    /**
     * Проверяет, является ли текущий пользователь автором указанного объявления.
     *
     * @param adId идентификатор объявления
     * @return true, если текущий пользователь является автором объявления, иначе false
     * @throws RuntimeException если объявление не найдено
     */
    public boolean isAdAuthor(Long adId) {
        AdvertisementEntity ad = advertisementRepository.findById(adId)
                .orElseThrow(() -> new RuntimeException("Объявление не найдено"));

        UserEntity currentUser = currentUserService.getCurrentUser();
        return ad.getUser().getId().equals(currentUser.getId());
    }

    /**
     * Проверяет, является ли текущий пользователь автором указанного комментария
     * или администратором.
     *
     * @param commentId идентификатор комментария
     * @return true, если текущий пользователь является автором комментария или администратором,
     *         иначе false
     */
    public boolean isCommentAuthorOrAdmin(int commentId) {
        if (currentUserService.isCurrentUserAdmin()) {
            return true;
        }

        return isCommentAuthor(commentId);
    }

    /**
     * Проверяет, является ли текущий пользователь автором указанного объявления
     * или администратором.
     *
     * @param adId идентификатор объявления
     * @return true, если текущий пользователь является автором объявления или администратором,
     *         иначе false
     */
    public boolean isAdAuthorOrAdmin(Long adId) {
        if (currentUserService.isCurrentUserAdmin()) {
            return true;
        }

        return isAdAuthor(adId);
    }
}
