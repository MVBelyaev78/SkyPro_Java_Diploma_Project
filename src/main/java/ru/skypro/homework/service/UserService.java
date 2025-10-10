package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;

import java.io.IOException;

public interface UserService {
    boolean changePassword(String userName, String currentPassword, String newPassword);

    public User getUserByUserName(String userName);

    public UpdateUser updateUser(String userName, UpdateUser updateUser);

    public String updateUserAvatar(String userName, MultipartFile image) throws IOException;
}
