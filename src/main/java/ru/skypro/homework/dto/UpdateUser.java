package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO объект для обновления информации о пользователе.
 * Содержит только те поля, которые могут быть изменены пользователем.
 */
@Data
@AllArgsConstructor
@Schema(description = "Данные для обновления информации о пользователе")
public class UpdateUser {
    @Schema(description = "Имя пользователя", example = "string")
    private String firstName;

    @Schema(description = "Фамилия пользователя", example = "string")
    private String lastName;

    @Schema(description = "Телефон пользователя", example = "+7 (323) 419-03-91")
    private String phone;
}
