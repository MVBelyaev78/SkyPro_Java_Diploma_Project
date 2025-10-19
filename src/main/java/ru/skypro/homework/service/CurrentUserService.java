package ru.skypro.homework.service;

import ru.skypro.homework.entity.UserEntity;

public interface CurrentUserService {
    UserEntity getCurrentUser();
    Long getCurrentUserId();
    boolean isCurrentUserAdmin();
}
