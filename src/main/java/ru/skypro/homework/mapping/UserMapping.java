package ru.skypro.homework.mapping;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.ImageEntity;
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
    public Optional<User> toDto(UserEntity entity) {
        if (entity == null) {
            return Optional.empty();
        }
        User user = new User();
        user.setId(entity.getId());
        user.setEmail(entity.getEmail());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setPhone(entity.getPhone());
        user.setRole(Role.valueOf(entity.getRole()));
        user.setImage("");
        entity.getImage().ifPresent(ue -> {
            user.setImage(ue.getName());
        });
        return Optional.of(user);
    }

    /**
     * Преобразуем User DTO в UserEntity
     *
     * @param dto DTO пользователя
     * @return сущность пользователя
     */
    public Optional<UserEntity> toEntity(User dto) {
        if (dto == null) {
            return Optional.empty();
        }
        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhone(dto.getPhone());
        entity.setRole(String.valueOf(entity.getRole()));
        return Optional.of(entity);
    }

    /**
     * Преобразукм UserEntity в UpdateUser DTO
     *
     * @param entity сущность пользователя
     * @return DTO для обновления пользователя
     */
    public Optional<UpdateUser> toUpdateUser(UserEntity entity) {
        if (entity == null) {
            return Optional.empty();
        }
        UpdateUser updateUser = new UpdateUser();
        updateUser.setFirstName(entity.getFirstName());
        updateUser.setLastName(entity.getLastName());
        updateUser.setPhone(entity.getPhone());
        return Optional.of(updateUser);
    }

    /**
     * Обновляем UserEntity
     *
     * @param entity сущность пользователя
     * @return обновленная сущность пользователя
     */
    public UserEntity updateUserEntity(UserEntity entity, UpdateUser updateUser) {
        if (entity == null || updateUser ==  null) {
            return null;
        }
        entity.setFirstName(updateUser.getFirstName());
        entity.setLastName(updateUser.getLastName());
        entity.setPhone(updateUser.getPhone());
        return entity;
    }

    /**
     * Создаем новый UserEntity на основе UpdateUser DTO
     *
     * @param updateDto DTO с данными пользователя
     * @return новая сущность пользователя
     */
    public Optional<UserEntity> toEntityFromUpdateDto(UpdateUser updateDto) {
        if (updateDto == null) {
            return Optional.empty();
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(updateDto.getFirstName());
        userEntity.setLastName(updateDto.getLastName());
        userEntity.setPhone(updateDto.getPhone());
        return Optional.of(userEntity);
    }

    /**
     * Создаем упрощенный User DTO только с основной информацией
     *
     * @param entity сущность пользователя
     * @return упрощенный DTO пользователя
     */
    public Optional<User> toSimpleDto(Optional<UserEntity> entity) {
        return entity.map(ue -> {
            User user = new User();
            user.setId(ue.getId());
            user.setEmail(ue.getEmail());
            user.setFirstName(ue.getFirstName());
            user.setLastName(ue.getLastName());
            user.setPhone(ue.getPhone());
            user.setRole(Role.valueOf(ue.getRole()));
            user.setImage(String.valueOf(ue.getImage().map(ImageEntity::getFilePath)));
            return user;
        });
    }

    private String convertToString(Optional<Role> role) {
        if (role.isEmpty()) {
            return Role.USER.toString();
        }
        return role.toString();
    }
}
