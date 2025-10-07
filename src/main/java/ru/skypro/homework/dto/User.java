package ru.skypro.homework.dto;

import lombok.Data;

/**
 * DTO объект представляющий пользователя системы.
 * Содержит полную информацию о пользователе.
 */
@Data
public class User {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String image;
}
