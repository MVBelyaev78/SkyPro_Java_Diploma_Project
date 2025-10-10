package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Класс, представляющий объект регистрации пользователя.
 * Содержит информацию, необходимую для создания нового пользователя в системе.
 */
@Data
public class Register {

    @Schema(description = "Имя пользователя", example = "john_doe", required = true)
    private String username;

    @Schema(description = "Пароль пользователя", example = "securePassword123", required = true)
    private String password;

    @Schema(description = "Имя пользователя", example = "John", required = true)
    private String firstName;

    @Schema(description = "Фамилия пользователя", example = "Doe", required = true)
    private String lastName;

    @Schema(description = "Телефонный номер пользователя", example = "+1234567890", required = false)
    private String phone;

    @Schema(description = "Роль пользователя", example = "USER", required = true)
    private Role role;
}
