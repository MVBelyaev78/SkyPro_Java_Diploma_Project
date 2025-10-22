package ru.skypro.homework.mapping;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;

import java.util.Optional;

/**
 * Маппер для преобразования между сущностью UserEntity и DTO объектами
 */
@Component
public class UserMapping {

    /**
     * Преобразуем UserEntity в DTO
     *
     * @param entity сущность пользователя
     * @return DTO пользоваетля
     */
    public Optional<User> toDto(Optional<UserEntity> entity) {
        return entity.map(e -> new User(e.getId(),
                e.getEmail(),
                e.getFirstName(),
                e.getLastName(),
                e.getPhone(),
                covertToRole(e.getRole()),
                e.getImage().isPresent() ? e.getImage().get().getFilePath() : ""));
    }

    private Role covertToRole(String roleString) {
        if (roleString == null) {
            return null;
        }
        try {
            return Role.valueOf(roleString);
        } catch (IllegalArgumentException e) {
            return Role.USER;
        }
    }

    /**
     * Преобразуем User DTO в UserEntity
     *
     * @param dto DTO пользователя
     * @return сущность пользователя
     */
    public UserEntity toEntity(User dto) {
        if (dto == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhone(dto.getPhone());
        entity.setRole(convertToString(dto.getRole()));

        return entity;
    }

    /**
     * Преобразукм UserEntity в UpdateUser DTO
     *
     * @param entity сущность пользователя
     * @return DTO для обновления пользователя
     */
    public Optional<UpdateUser> toUpdateUser(Optional<UserEntity> entity) {
        return entity.map(e -> new UpdateUser(e.getFirstName(), e.getLastName(), e.getPhone()));
    }

    /**
     * Создаем новый UserEntity на основе UpdateUser DTO
     *
     * @param updateDto DTO с данными пользователя
     * @return новая сущность пользователя
     */
    public UserEntity toEntityFromUpdateDto(UpdateUser updateDto) {
        if (updateDto == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setFirstName(updateDto.getFirstName());
        entity.setLastName(updateDto.getLastName());
        entity.setPhone(updateDto.getPhone());

        return entity;
    }

    /**
     * Преобразуем enum Role в строку
     *
     * @param role enum роль
     * @return строковое представление роли
     */
    private String convertToString(Role role) {
        if (role == null) {
            return "USER";
        }
        return role.name();
    }

    /**
     * Создаем упрощенный User DTO только с основной информацией
     *
     * @param entity сущность пользователя
     * @return упрощенный DTO пользователя
     */
    public User toSimpleDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        String imagePath = "";
        if (entity.getImage().isPresent()) {
            imagePath = entity.getImage().get().getFilePath();
        }

        return new User(
                entity.getId(),
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPhone(),
                covertToRole(entity.getRole()),
                imagePath);
    }
}
