package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.config.UserEntityDetails;
import ru.skypro.homework.entity.UserEntity;

/**
 * Сервис для получения информации о текущем аутентифицированном пользователе.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CurrentUserServiceImpl {
    private final UserEntityDetailsServiceImpl userEntityDetailsService;

    /**
     * Возвращает текущего аутентифицированного пользователя.
     *
     * @return объект UserEntity, представляющий текущего пользователя
     * @throws AccessDeniedException если пользователь не аутентифицирован
     */
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Пользователь не аутентифицирован");
        }

        String username = authentication.getName();
        UserEntityDetails userEntityDetails = (UserEntityDetails) userEntityDetailsService.loadUserByUsername(username);
        return userEntityDetails.getUserEntity();
    }

    /**
     * Возвращает идентификатор текущего аутентифицированного пользователя.
     *
     * @return идентификатор пользователя (Long)
     */
    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    /**
     * Проверяет, является ли текущий пользователь администратором.
     *
     * @return true, если текущий пользователь является администратором, иначе false
     */
    public boolean isCurrentUserAdmin() {
        UserEntity user = getCurrentUser();
        return user.getRole().equals("ADMIN");
    }
}
