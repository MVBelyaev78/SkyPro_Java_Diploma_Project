package ru.skypro.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.ResourceNotFoundException;
import ru.skypro.homework.mapping.UserMapping;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapping userMapping;

    @InjectMocks
    private UserServiceImpl userService;

    private final String TEST_EMAIL = "test@example.com";
    private final String TEST_PASSWORD = "password123";
    private final String ENCODED_PASSWORD = "encodedPassword123";

    private UserEntity createTestUserEntity() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail(TEST_EMAIL);
        user.setFirstName("Иван");
        user.setLastName("Иванов");
        user.setPhone("+79878765432");
        user.setRole("USER");
        user.setPassword(ENCODED_PASSWORD);
        return user;
    }

    private User createTestUserDto() {
        return new User(1L, TEST_EMAIL, "Иван", "Иванов", "+79878765432", Role.USER, "/image/avatar.jpg");
    }

    private UpdateUser createTestUpdateUser() {
        return new UpdateUser("Петр", "Петров", "+79991234567");
    }

    @Test
    void changePassword_WhenUserExistsAndCurrentPasswordCorrect_ShouldReturnTrue() {
        final UserEntity user = createTestUserEntity();
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(user);
        when(passwordEncoder.matches(TEST_PASSWORD, ENCODED_PASSWORD)).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("newEncodePassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        final boolean result = userService.changePassword(TEST_EMAIL, TEST_PASSWORD, "newPassword");

        assertTrue(result);
        verify(userRepository).findByEmail(TEST_EMAIL);
        verify(passwordEncoder).matches(TEST_PASSWORD, ENCODED_PASSWORD);
        verify(passwordEncoder).encode("newPassword");
        verify(userRepository).save(user);
        assertEquals("newEncodePassword", user.getPassword());
    }

    @Test
    void changePassword_WhenUserExistsButCurrentPasswordIncorrect_ShouldReturnFalse() {
        final UserEntity user = createTestUserEntity();
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(user);
        when(passwordEncoder.matches(TEST_PASSWORD, ENCODED_PASSWORD)).thenReturn(false);

        final boolean result = userService.changePassword(TEST_EMAIL, TEST_PASSWORD, "newPassword");

        assertFalse(result);
        verify(userRepository).findByEmail(TEST_EMAIL);
        verify(passwordEncoder).matches(TEST_PASSWORD, ENCODED_PASSWORD);
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void changePassword_WhenUserNotFound_ShouldThrowException() {
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.changePassword(TEST_EMAIL, TEST_PASSWORD, "newPassword");
        });

        assertEquals("Пользователь не найден", exception.getMessage());
        verify(userRepository).findByEmail(TEST_EMAIL);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void changePassword_WhenNewPasswordIsEmpty_ShouldEncodeEmptyPassword() {
        final UserEntity user = createTestUserEntity();
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(user);
        when(passwordEncoder.matches(TEST_PASSWORD, ENCODED_PASSWORD)).thenReturn(true);
        when(passwordEncoder.encode("")).thenReturn("encodeEmptyPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        final boolean result = userService.changePassword(TEST_EMAIL, TEST_PASSWORD, "");

        assertTrue(result);
        verify(passwordEncoder).encode("");
    }

    @Test
    void getUserByUserName_WhenExists_ShouldReturnUserDto() {
        final UserEntity userEntity = createTestUserEntity();
        final User expectedUser = createTestUserDto();

        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(userEntity);
        when(userMapping.toDto(userEntity)).thenReturn(Optional.of(expectedUser));

        assertEquals(Optional.of(expectedUser), userService.getUserByUserName(TEST_EMAIL));
        verify(userRepository).findByEmail(TEST_EMAIL);
        verify(userMapping).toDto(userEntity);
    }

    @Test
    void getUserByUserName_WhenUserNotFound_ShouldThrowException() {
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(null);

        assertEquals(userService.getUserByUserName(TEST_EMAIL), Optional.empty());
        verify(userRepository).findByEmail(TEST_EMAIL);
        verify(userMapping, never()).toDto(any(UserEntity.class));
    }

    @Test
    void getUserByUserName_WithDifferentEmailCases_ShouldWorkCorrectly() {
        final String emailWithUppercase = "TEST@EXAMPLE.COM";
        final UserEntity userEntity = createTestUserEntity();
        final User expectedUser = createTestUserDto();

        when(userRepository.findByEmail(emailWithUppercase)).thenReturn(userEntity);
        when(userMapping.toDto(userEntity)).thenReturn(Optional.of(expectedUser));

        Optional<User> result = userService.getUserByUserName(emailWithUppercase);

        assertEquals(Optional.of(expectedUser), result);
        verify(userRepository).findByEmail(emailWithUppercase);
    }

    @Test
    void updateUser_WhenUserExists_ShouldReturnUpdateUser() {
        final UserEntity existingUserEntity = new UserEntity();
        existingUserEntity.setId(1L);
        existingUserEntity.setEmail(TEST_EMAIL);
        existingUserEntity.setFirstName("Иван");
        existingUserEntity.setLastName("Иванов");
        existingUserEntity.setPhone("+79878765432");
        existingUserEntity.setRole("USER");
        existingUserEntity.setPassword(ENCODED_PASSWORD);

        final UpdateUser updateUser = new UpdateUser("Петр", "Петров", "+79991234567");

        final UserEntity resultUserEntity = new UserEntity();
        resultUserEntity.setId(1L);
        resultUserEntity.setEmail(TEST_EMAIL);
        resultUserEntity.setFirstName("Петр");
        resultUserEntity.setLastName("Петров");
        resultUserEntity.setPhone("+79991234567");
        resultUserEntity.setRole("USER");
        resultUserEntity.setPassword(ENCODED_PASSWORD);

        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(existingUserEntity);
        when(userMapping.updateUserEntity(existingUserEntity, updateUser))
                .thenReturn(resultUserEntity);
        when(userRepository.save(resultUserEntity)).thenReturn(resultUserEntity);
        when(userMapping.toUpdateUser(resultUserEntity)).thenReturn(Optional.of(updateUser));

        Optional<UpdateUser> result = userService.updateUser(TEST_EMAIL, updateUser);

        assertEquals(Optional.of(updateUser), result);

        verify(userRepository, times(1)).findByEmail(TEST_EMAIL);
        verify(userMapping, times(1))
                .updateUserEntity(existingUserEntity, updateUser);
        verify(userRepository, times(1)).save(resultUserEntity);
        verify(userMapping, times(1)).toUpdateUser(resultUserEntity);
    }

    @Test
    void updateUser_WhenUserNotFound_ShouldThrowException() {
        UpdateUser updateUser = createTestUpdateUser();
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.updateUser(TEST_EMAIL, updateUser);
        });

        assertEquals("Пользователь не найден", exception.getMessage());
        verify(userRepository).findByEmail(TEST_EMAIL);
        verify(userRepository, never()).save(any(UserEntity.class));
        verify(userMapping, never()).toUpdateUser(any(UserEntity.class));
    }
}
