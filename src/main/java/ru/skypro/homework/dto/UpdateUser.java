package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO объект для обновления информации о пользователе.
 * Содержит только те поля, которые могут быть изменены пользователем.
 */
@Data
@Schema(description = "Данные для обновления информации о пользователе")
public class UpdateUser {
    @Schema(description = "Имя пользователя", example = "Иван")
    private String firstName;

    @Schema(description = "Фамилия пользователя", example = "Иванов")
    private String lastName;

    @Schema(description = "Телефон пользователя", example = "+79991234567")
    private String phone;
}
