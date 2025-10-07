package ru.skypro.homework.dto;

import lombok.Data;

/**
 * DTO объект для смены пароля пользователя.
 * Содержит текущий и новый пароль.
 */
@Data
public class NewPassword {
    private String currentPassword;
    private String newPassword;
}
