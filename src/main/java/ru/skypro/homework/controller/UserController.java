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
import ru.skypro.homework.exception.ResourceNotFoundException;
import ru.skypro.homework.service.UserService;

import java.io.IOException;
import java.util.Objects;

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
     * Получение информации об авторизованном пользователе
     *
     * @param authentication объект аутентификации Spring Security
     * @return статус получения
     */
    @Operation(summary = "Получение информации об авторизованном пользователе")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "")),
    })
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        try {
            return ResponseEntity.ok(userService.getUserByUserName(authentication.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("")));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Обновление информации о пользователе
     *
     * @param updateUser     DTO объект с обновляемыми полями пользователя
     * @param authentication объект аутентификации Spring Security
     * @return статус обновления информации
     */
    @Operation(summary = "Обновление информации о авторизованном пользователе")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateUser.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = ""))
    })
    @PatchMapping("/me")
    public ResponseEntity<UpdateUser> updateCurrentUser(@RequestBody UpdateUser updateUser,
                                                        Authentication authentication) {
        try {
            return ResponseEntity.ok(userService.updateUser(authentication.getName(), updateUser)
                    .orElseThrow(() -> new ResourceNotFoundException("")));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Обновление аватара авторизованного пользователя
     *
     * @param image          файл изображения для установки в качестве аватара
     * @param authentication объект аутентификации Spring Security
     * @return статус обновления
     */
    @Operation(summary = "Обновление аватара авторизованного пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = ""))
    })
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUserAvatar(@RequestParam("image") MultipartFile image,
                                                   Authentication authentication) {
        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body("Изображение не предоставлено");
        }
        if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
            return ResponseEntity.badRequest().body("Файл должен быть изображением");
        }
        try {
            return ResponseEntity.ok(userService.updateUserAvatar(authentication.getName(), image));
        } catch (IOException e) {
            log.error("Ошибка загрузки аватара пользователя", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
