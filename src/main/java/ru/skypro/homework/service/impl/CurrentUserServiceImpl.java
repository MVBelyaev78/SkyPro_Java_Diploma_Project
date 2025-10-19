package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.config.UserEntityDetails;
import ru.skypro.homework.entity.UserEntity;


@Service
@Transactional
@RequiredArgsConstructor
public class CurrentUserServiceImpl {
    private final UserEntityDetailsServiceImpl userEntityDetailsService;

    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Пользователь не аутентифицирован");
        }

        String username = authentication.getName();
        UserEntityDetails userEntityDetails = (UserEntityDetails) userEntityDetailsService.loadUserByUsername(username);
        return userEntityDetails.getUserEntity();
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public boolean isCurrentUserAdmin() {
        UserEntity user = getCurrentUser();
        return user.getRole().equals("ADMIN");
    }

}
