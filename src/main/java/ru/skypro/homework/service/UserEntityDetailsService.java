package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserEntityDetailsService {
    UserDetails loadUserByUsername(String username);

    UserDetails loadUserById(Long id);
}
