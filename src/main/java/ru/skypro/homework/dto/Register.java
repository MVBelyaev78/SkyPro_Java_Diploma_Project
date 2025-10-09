package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Класс, представляющий объект регистрации пользователя.
 */
@Data
public class Register {

    @Schema(description = "Имя пользователя", example = "john_doe")
    private String username;

    @Schema(description = "Пароль пользователя", example = "securePassword123")
    private String password;

    @Schema(description = "Имя пользователя", example = "John")
    private String firstName;

    @Schema(description = "Фамилия пользователя", example = "Doe")
    private String lastName;

    @Schema(description = "Телефонный номер пользователя", example = "+1234567890")
    private String phone;

    @Schema(description = "Роль пользователя", example = "USER")
    private Role role;
}
