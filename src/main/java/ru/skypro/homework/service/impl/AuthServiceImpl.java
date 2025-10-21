package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

/**
 * Реализация сервиса аутентификации, обеспечивающая функции входа и регистрации пользователей.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Выполняет аутентификацию пользователя с указанными именем пользователя и паролем.
     *
     * @param userName имя пользователя
     * @param password пароль пользователя
     * @return true, если аутентификация прошла успешно, иначе false
     */
    @Override
    public boolean login(String userName, String password) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userName, password);

            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } catch (AuthenticationException e) {
            return false;
        }
    }

    /**
     * Регистрирует нового пользователя с указанной информацией.
     *
     * @param register объект, содержащий данные для регистрации пользователя
     * @return true, если регистрация прошла успешно, иначе false (например, если пользователь с таким email уже существует)
     */
    @Override
    public boolean register(Register register) {
        if (repository.existsByEmail(register.getUsername())) {
            return false;
        }

        UserEntity user = new UserEntity();
        user.setEmail(register.getUsername());
        user.setPassword(encoder.encode(register.getPassword()));
        user.setFirstName(register.getFirstName());
        user.setLastName(register.getLastName());
        user.setPhone(register.getPhone());
        user.setRole(register.getRole().toString());

        repository.save(user);
        return true;
    }
}
