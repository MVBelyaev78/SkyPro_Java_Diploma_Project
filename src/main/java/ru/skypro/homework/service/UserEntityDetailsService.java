package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.config.UserEntityDetails;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserEntityDetailsService implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findByEmail(username)
                .orElseThrow(() -> {
                    log.error("User not found: {}", username);
                    return new UsernameNotFoundException("Пользователь не найден: " + username);
                });
        log.info("User found: {} {}", user.getFirstName(), user.getLastName());
        return new UserEntityDetails(user);
    }

    public UserDetails loadUserById(Long id) {
        UserEntity user = repository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден с id: " + id));

        return new UserEntityDetails(user);
    }
}
