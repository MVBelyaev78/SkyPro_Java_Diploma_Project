package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.component.ImageComponent;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.ResourceNotFoundException;
import ru.skypro.homework.component.mapping.UserMapping;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapping mapping;
    private final ImageComponent imageComponent;

    @Override
    public boolean changePassword(String userName, String currentPassword, String newPassword) {
        UserEntity user = userRepository.findByEmail(userName);
        if (user == null) {
            throw new ResourceNotFoundException("Пользователь не найден");
        }
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return true;
    }

    @Override
    public Optional<User> getUserByUserName(String userName) {
        return mapping.toDto(userRepository.findByEmail(userName));
    }

    @Override
    public Optional<UpdateUser> updateUser(String userName, UpdateUser updateUser) {
        final UserEntity userEntity = userRepository.findByEmail(userName);
        if (userEntity == null) {
            throw new ResourceNotFoundException("Пользователь не найден");
        }
        final UserEntity userEntityUpdated = mapping.updateUserEntity(userEntity, updateUser);
        if (userEntityUpdated == null) {
            throw new ResourceNotFoundException("Пользователь не найден");
        }
        return mapping.toUpdateUser(userRepository.save(userEntityUpdated));
    }

    @Override
    public String updateUserAvatar(String userName, MultipartFile image) throws IOException {
        String result = "";
        UserEntity author = userRepository.findByEmail(userName);
        if (author == null) {
            throw new ResourceNotFoundException("Пользователь не найден");
        }
        if (image != null) {
            ImageEntity imageEntity = imageComponent.saveImage(image);
            author.setImage(imageEntity);
            result = imageEntity.getFilePath();
        }
        userRepository.save(author);

        return result;
    }
}
