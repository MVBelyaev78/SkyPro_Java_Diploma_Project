package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;

import java.io.IOException;
import java.util.Optional;

public interface UserService {
    boolean changePassword(String userName, String currentPassword, String newPassword);

    Optional<User> getUserByUserName(String userName);

    Optional<UpdateUser> updateUser(String userName, UpdateUser updateUser);

    String updateUserAvatar(String userName, MultipartFile image) throws IOException;
}
