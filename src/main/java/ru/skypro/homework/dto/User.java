package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO объект представляющий пользователя системы.
 * Содержит полную информацию о пользователе.
 */
@Data
@AllArgsConstructor
@Schema(description = "Полная информация о пользователе")
public class User {
    @Schema(description = "ID пользователя", example = "1")
    private Long id;

    @Schema(description = "Email пользователя", example = "user@example.com")
    private String email;

    @Schema(description = "Имя пользователя", example = "Иван")
    private String firstName;

    @Schema(description = "Фамилия пользователя", example = "Иванов")
    private String lastName;

    @Schema(description = "Телефон пользователя", example = "+79991234567")
    private String phone;

    @Schema(description = "Роль пользователя")
    private Role role;

    @Schema(description = "Ссылка на аватар пользователя", example = "/images/avatar.jpg")
    private String image;
}
