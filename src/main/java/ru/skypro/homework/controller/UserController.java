package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

/**
 * Контроллер для управления информацией о пользователях.
 * Предоставляет API для обновления пароля, получения информации об авторизованном пользователе,
 * обновления информации об авторизованном пользователе и обновления аватара авторизованного пользователя.
 */

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ImageService imageService;

    /**
     * Обновляет пароль текущего авторизованного пользователя.
     *
     * @param newPassword DTO объект содержащий текущий и новый пароли
     * @param authentication объект аутентификации Spring Security
     * @return ResponseEntity со статусом Ok при успешном обновлении,
     *          FORBIDDEN при неверном текущем пароле или INTERNAL_SERVER_ERROR при ошибке
     */
    @PostMapping("/setPassword")
    public ResponseEntity<?> setPassword(@RequestBody NewPassword newPassword,
                                         Authentication authentication) {
        try {
            String userName = authentication.getName();
            boolean success = userService.changePassword(
                    userName,
                    newPassword.getCurrentPassword(),
                    newPassword.getNewPassword()
            );

            if (success) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (Exception e) {
            log.error("Ошибка смены пароля", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Получает информацию о текущем авторизованном пользователе.
     *
     * @param authentication объект аутентификации Spring Security
     * @return ResponseEntity с данными пользователя или статусом NOT_FOUND/INTERNAL_SERVER_ERROR
     */
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        try {
            String userName = authentication.getName();
            User user = userService.getUserByUserName(userName);

            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Ошибка вывода авторизованного пользователя", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Обновляет информацию о текущем авторизованном пользователе.
     *
     * @param updateUser DTO объект с обновляемыми полями пользователя
     * @param authentication объект аутентификации Spring Security
     * @return ResponseEntity с обновленными данными пользователя или статусом NOT_FOUND/INTERNAL_SERVER_ERROR
     */
    @PatchMapping("/me")
    public ResponseEntity<UpdateUser> updateCurrentUser(@RequestBody UpdateUser updateUser,
                                                        Authentication authentication) {
        try {
            String userName = authentication.getName();
            UpdateUser updatedUser = userService.updateUser(userName, updateUser);

            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Ошибка обновления информации о пользователе", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Обновляет аватар текущего авторизованного пользователя.
     *
     * @param image файл изображения для установки в качестве аватара
     * @param authentication объект аутентификации Spring Security
     * @return ResponseEntity с путем к сохраненному изображению или статусом ошибки
     */
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUserAvatar(@RequestParam("image") MultipartFile image,
                                                   Authentication authentication) {
        try {
            String userName = authentication.getName();

            if (image.isEmpty()) {
                return ResponseEntity.badRequest().body("Изображение не предоставлено");
            }

            if (!image.getContentType().startsWith("image/")) {
                return ResponseEntity.badRequest().body("Файл должен быть изображением");
            }

            String imagePath = userService.updateUserAvatar(userName, image);
            return ResponseEntity.ok(imagePath);

        } catch (IOException e) {
            log.error("Ошибка загрузки аватара пользователя", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка загрузки изображения");
        } catch (Exception e) {
            log.error("Ошибка изменения аватара пользователя", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
