package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public boolean changePassword(String userName, String currentPassword, String newPassword) {
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
