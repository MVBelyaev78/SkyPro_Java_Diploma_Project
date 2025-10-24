package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO объект для смены пароля пользователя.
 * Содержит текущий и новый пароль.
 */
@Data
@Schema(description = "Данные для смены пароля пользователя")
public class NewPassword {
    @Schema(description = "Текущий пароль пользователя", example = "stringst")
    private String currentPassword;

    @Schema(description = "Новый пароль пользователя", example = "stringst")
    private String newPassword;
}
