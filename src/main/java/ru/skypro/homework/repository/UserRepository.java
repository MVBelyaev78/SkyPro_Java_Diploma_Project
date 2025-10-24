package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.UserEntity;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью UserEntity (Пользователь)
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Находит пользователя по email (используется как userName в аутентификации)
     *
     * @param email email пользователя
     * @return Optional с пользователем, если найден
     */
    UserEntity findByEmail(String email);

    /**
     * Проверяет существование пользователя по email
     *
     * @param email email пользователя
     * @return true если пользователь существует
     */
    boolean existsByEmail(String email);

    /**
     * Находит пользователей по имени и фамилии
     * (соответствует constraint user_uk$1 в БД)
     *
     * @param firstName имя пользователя
     * @param lastName фамилия пользователя
     * @return список пользователей
     */
    List<UserEntity> findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Находит пользователей по роли
     *
     * @param role роль пользователя (USER, ADMIN)
     * @return список пользователей с указанной ролью
     */
    List<UserEntity> findByRole(String role);

    /**
     * Находит пользователя по номеру телефона
     *
     * @param phone номер телефона
     * @return Optional с пользователем, если найден
     */
    UserEntity findByPhone(String phone);

    /**
     * Проверяет существование пользователя с таким же именем и фамилией (исключая текущего пользователя)
     * Используется для валидации уникальности при обновлении
     *
     * @param firstName имя
     * @param lastName фамилия
     * @param excludeId ID пользователя для исключения
     * @return true если существует другой пользователь с таким же именем и фамилией
     */
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM UserEntity u WHERE u.firstName = :firstName AND u.lastName = :lastName AND u.id != :excludeId")
    boolean existsByFirstNameAndLastNameExcludingId(@Param("firstName") String firstName,
                                                    @Param("lastName") String lastName,
                                                    @Param("excludeId") Long excludeId);

    /**
     * Проверяет существование пользователя с таким email (исключая текущего пользователя)
     * Используется для валидации уникальности email при обновлении
     *
     * @param email email
     * @param excludeId ID пользователя для исключения
     * @return true если существует другой пользователь с таким email
     */
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM UserEntity u WHERE u.email = :email AND u.id != :excludeId")
    boolean existsByEmailExcludingId(@Param("email") String email,
                                     @Param("excludeId") Long excludeId);

    /**
     * Находит пользователей по части имени (поиск)
     *
     * @param firstNamePart часть имени для поиска
     * @return список пользователей
     */
    List<UserEntity> findByFirstNameContainingIgnoreCase(String firstNamePart);

    /**
     * Находит пользователей по части фамилии (поиск)
     *
     * @param lastNamePart часть фамилии для поиска
     * @return список пользователей
     */
    List<UserEntity> findByLastNameContainingIgnoreCase(String lastNamePart);

    /**
     * Находит пользователей по части email (поиск)
     *
     * @param emailPart часть email для поиска
     * @return список пользователей
     */
    List<UserEntity> findByEmailContainingIgnoreCase(String emailPart);

    /**
     * Находит всех пользователей с аватаром (image не null)
     *
     * @return список пользователей с аватаром
     */
    List<UserEntity> findByImageIsNotNull();

    /**
     * Находит всех пользователей без аватара (image is null)
     *
     * @return список пользователей без аватара
     */
    List<UserEntity> findByImageIsNull();
}
