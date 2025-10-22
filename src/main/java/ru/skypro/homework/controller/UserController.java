package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Пользователи", description = "API для управления информацией о пользователях")
public class UserController {

    private final UserService userService;

    /**
     * Обновление пароля
     *
     * @param newPassword    DTO объект содержащий текущий и новый пароли
     * @param authentication объект аутентификации Spring Security
     * @return статус обновления
     */
    @Operation(summary = "Обновление пароля")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
    })
    @PostMapping("/setPassword")
    public ResponseEntity<?> setPassword(@RequestBody NewPassword newPassword,
                                         Authentication authentication) {
        if (userService.changePassword(authentication.getName(),
                newPassword.getCurrentPassword(),
                newPassword.getNewPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Получает информацию о текущем авторизованном пользователе.
     *
     * @param authentication объект аутентификации Spring Security
     * @return ResponseEntity с данными пользователя или статусом NOT_FOUND/INTERNAL_SERVER_ERROR
     */
    @Operation(summary = "Получение информации об авторизованном пользователе")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о пользователе получена",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
            ),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера"),
    })
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
     * @param updateUser     DTO объект с обновляемыми полями пользователя
     * @param authentication объект аутентификации Spring Security
     * @return ResponseEntity с обновленными данными пользователя или статусом NOT_FOUND/INTERNAL_SERVER_ERROR
     */
    @Operation(
            summary = "Обновление информации о пользователе",
            description = "Позволяет обновить информацию о текущем авторизованном пользователе"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о пользователе обновлена",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateUser.class))
            ),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
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
     * @param image          файл изображения для установки в качестве аватара
     * @param authentication объект аутентификации Spring Security
     * @return ResponseEntity с путем к сохраненному изображению или статусом ошибки
     */
    @Operation(
            summary = "Обновление аватара пользователя",
            description = "Позволяет обновить аватар текущего авторизованного пользователя"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Аватар успешно обновлен",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "400", description = "Неверный формат файла или файл не предоставлен"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
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
