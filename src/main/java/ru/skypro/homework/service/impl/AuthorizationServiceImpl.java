package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.AdvertisementEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.AdvertisementRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AuthorizationService;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private final CommentRepository commentRepository;
    private final AdvertisementRepository advertisementRepository;
    private final CurrentUserServiceImpl currentUserService;

    public boolean isCommentAuthor(int commentId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Комментарий не найден"));

        UserEntity currentUser = currentUserService.getCurrentUser();
        return comment.getIdAuthor().getId().equals(currentUser.getId());
    }

    public boolean isAdAuthor(Long adId) {
        AdvertisementEntity ad = advertisementRepository.findById(adId)
                .orElseThrow(() -> new RuntimeException("Объявление не найдено"));

        UserEntity currentUser = currentUserService.getCurrentUser();
        return ad.getUser().getId().equals(currentUser.getId());
    }

    public boolean isCommentAuthorOrAdmin(int commentId) {
        if (currentUserService.isCurrentUserAdmin()) {
            return true;
        }

        return isCommentAuthor(commentId);
    }

    public boolean isAdAuthorOrAdmin(Long adId) {
        if (currentUserService.isCurrentUserAdmin()) {
            return true;
        }

        return isAdAuthor(adId);
    }
}
