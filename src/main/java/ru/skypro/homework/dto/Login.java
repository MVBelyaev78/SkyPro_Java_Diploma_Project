package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO для аутентификации пользователя.
 * Содержит необходимые данные для входа пользователя в систему.
 */
@Data
@Schema(description = "Объект для аутентификации пользователя")
public class Login {

    @Schema(description = "Имя пользователя", example = "user123", required = true)
    private String username;

    @Schema(description = "Пароль пользователя", example = "password123", required = true)
    private String password;
}
