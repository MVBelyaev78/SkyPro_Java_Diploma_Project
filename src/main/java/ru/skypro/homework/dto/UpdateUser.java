package ru.skypro.homework.dto;

import lombok.Data;

/**
 * DTO объект для обновления информации о пользователе.
 * Содержит только те поля, которые могут быть изменены пользователем.
 */
@Data
public class UpdateUser {
    private String firstName;
    private String lastName;
    private String phone;
}
