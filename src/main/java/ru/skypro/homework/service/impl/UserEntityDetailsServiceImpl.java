package ru.skypro.homework.service.impl;

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

/**
 * Сервис для загрузки данных о пользователе по имени пользователя.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserEntityDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    /**
     * Загружает пользователя по имени пользователя (в данном случае - по email).
     *
     * @param username имя пользователя (email)
     * @return объект UserDetails, представляющий найденного пользователя
     * @throws UsernameNotFoundException если пользователь не найден
     */
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

    /**
     * Загружает пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return объект UserDetails, представляющий найденного пользователя
     * @throws UsernameNotFoundException если пользователь не найден с указанным идентификатором
     */
    public UserDetails loadUserById(Long id) {
        UserEntity user = repository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден с id: " + id));

        return new UserEntityDetails(user);
    }
}
