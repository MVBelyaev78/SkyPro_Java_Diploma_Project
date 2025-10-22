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
    public Optional<User> toDto(Optional<UserEntity> entity) {
        return entity.map(e -> {
            User user = new User();
            user.setId(e.getId());
            user.setEmail(e.getEmail());
            user.setFirstName(e.getFirstName());
            user.setLastName(e.getLastName());
            user.setPhone(e.getPhone());
            user.setRole(Role.valueOf(e.getRole()));
            user.setImage(String.valueOf(Optional.ofNullable(e.getImage())));
            return user;
        });
    }

    /**
     * Преобразуем User DTO в UserEntity
     *
     * @param dto DTO пользователя
     * @return сущность пользователя
     */
    public Optional<UserEntity> toEntity(Optional<User> dto) {
        return dto.map(d -> {
            UserEntity entity = new UserEntity();
            entity.setId(d.getId());
            entity.setEmail(d.getEmail());
            entity.setFirstName(d.getFirstName());
            entity.setLastName(d.getLastName());
            entity.setPhone(d.getPhone());
            entity.setRole(convertToString(Optional.ofNullable(d.getRole())));
            return entity;
        });
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
     * Обновляем UserEntity
     *
     * @param entity сущность пользователя
     * @return обновленная сущность пользователя
     */
    public Optional<UserEntity> updateUserEntity(Optional<UserEntity> entity, Optional<UpdateUser> updateUser) {
        updateUser.ifPresent(u -> entity.ifPresent(e -> {
            e.setFirstName(u.getFirstName());
            e.setLastName(u.getLastName());
            e.setPhone(u.getPhone());
        }));
        return entity;
    }

    /**
     * Создаем новый UserEntity на основе UpdateUser DTO
     *
     * @param updateDto DTO с данными пользователя
     * @return новая сущность пользователя
     */
    public Optional<UserEntity> toEntityFromUpdateDto(Optional<UpdateUser> updateDto) {
        return updateDto.map(u -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setFirstName(u.getFirstName());
            userEntity.setLastName(u.getLastName());
            userEntity.setPhone(u.getPhone());
            return userEntity;
        });
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
