package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO для аутентификации пользователя.
 */
@Data
@Schema(description = "Объект для аутентификации пользователя")
public class Login {

    @Schema(description = "Имя пользователя", example = "user123", required = true)
    private String username;

    @Schema(description = "Пароль пользователя", example = "password123", required = true)
    private String password;
}
