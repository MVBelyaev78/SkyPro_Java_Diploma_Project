package ru.skypro.homework.mapping;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;

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
    public User toDto(UserEntity entity) {
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
    public UpdateUser toUpdateUser(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return new UpdateUser(
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPhone()
        );
    }

    /**
     * Обновляем UserEntity на основе данных из UpdateUser DTO
     *
     * @param entity существующая сущность пользователя
     * @param updateDto DTO с обновленными данными
     * @return обновленная сущность пользователя
     */
    public UserEntity updateEntityFromUpdateDTO(UserEntity entity, UpdateUser updateDto) {
        if (entity == null || updateDto == null) {
            return entity;
        }

        if (updateDto.getFirstName() != null) {
            entity.setFirstName(updateDto.getFirstName());
        }
        if (updateDto.getLastName() != null) {
            entity.setLastName(updateDto.getLastName());
        }
        if (updateDto.getPhone() != null) {
            entity.setPhone(updateDto.getPhone());
        }

        return entity;
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
