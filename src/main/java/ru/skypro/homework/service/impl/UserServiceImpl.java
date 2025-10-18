package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean changePassword(String userName, String currentPassword, String newPassword) {
        UserEntity user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    @Override
    public User getUserByUserName(String userName) {
        return new User(1L, "user@example.com", "Иван", "Иванов", "+79991234567", Role.USER, "/images/avatar.jpg");
    }

    @Override
    public UpdateUser updateUser(String userName, UpdateUser updateUser) {
        return new UpdateUser("Иван", "Иванов", "+79991234567");
    }

    @Override
    public String updateUserAvatar(String userName, MultipartFile image) throws IOException {
        return "User Avatar";
    }
}
