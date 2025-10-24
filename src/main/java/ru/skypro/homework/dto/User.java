package ru.skypro.homework.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO объект представляющий пользователя системы.
 * Содержит полную информацию о пользователе.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Полная информация о пользователе")
public class User {
    @Schema(description = "ID пользователя", example = "0")
    private Long id;

    @Schema(description = "Email пользователя", example = "string")
    private String email;

    @Schema(description = "Имя пользователя", example = "string")
    private String firstName;

    @Schema(description = "Фамилия пользователя", example = "string")
    private String lastName;

    @Schema(description = "Телефон пользователя", example = "string")
    private String phone;

    @Schema(description = "string")
    private Role role;

    @Schema(description = "Аватар пользователя", example = "string")
    private String image;
}
