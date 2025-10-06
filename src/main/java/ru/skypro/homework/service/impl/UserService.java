package ru.skypro.homework.service.impl;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Login;

public interface UserService {
    void changePassword(String userName, String newPassword);

    void updateUserInfo(String currentUsername, Login updatedLogin);

    void updateUserAvatar(String username, MultipartFile image);
}
