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
import ru.skypro.homework.mapping.UserMapping;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapping userMapping;

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
        UserEntity userEntity = userRepository.findByEmail(userName)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + userName));
        return userMapping.toDto(userEntity);
    }

    @Override
    public UpdateUser updateUser(String userName, UpdateUser updateUser) {
        UserEntity userEntity = userRepository.findByEmail(userName)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + userName));

        userMapping.updateEntityFromUpdateDTO(userEntity, updateUser);
        UserEntity savedUser = userRepository.save(userEntity);

        return userMapping.toUpdateUser(savedUser);
    }

    @Override
    public String updateUserAvatar(String userName, MultipartFile image) throws IOException {
        return "User Avatar";
    }
}
