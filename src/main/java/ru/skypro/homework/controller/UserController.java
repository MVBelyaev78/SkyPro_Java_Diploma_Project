package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Login;
import ru.skypro.homework.service.impl.UserService;

/**
 * Контроллер для управления пользователями.
 * Предоставляет API для обновления пароля, получения информации об авторизованном пользователе,
 * обновления информации об авторизованном пользователе и обновления аватара авторизованного пользователя.
 */

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping("/setPassword")
    public ResponseEntity<String> updatePassword(@RequestBody Login newLogin) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        userService.changePassword(currentUsername, newLogin.getPassword());

        return ResponseEntity.ok("Пароль успешно обновлен");
    }

    @GetMapping("/me")
    public ResponseEntity<Login> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Login userLogin = new Login();
        userLogin.setUsername(username);
        userLogin.setPassword("***");

        return ResponseEntity.ok(userLogin);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody Login updatedLogin) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        userService.updateUserInfo(currentUsername, updatedLogin);

        return ResponseEntity.ok("Информация пользователя обновлена");
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUserAvatar(@RequestParam("image") MultipartFile image) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        userService.updateUserAvatar(username, image);

        return ResponseEntity.ok("Аватар успешно обновлен");
    }
}
