package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.AdvertisementEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<AdvertisementEntity, Long> {
    /**
     * Поиск объяввления по ID объявления
     *
     * @param id ID объявления
     * @return объявление
     */
    Optional<AdvertisementEntity> findById(Long id);

    /**
     * Поиск объяввлений по ID пользователя
     *
     * @param userId ID пользователя
     * @return список объяввлений
     */
    List<AdvertisementEntity> findByUserId(Long userId);

    /**
     * Поиск всех объяввлений
     *
     * @return список объяввлений
     */
    List<AdvertisementEntity> findAll();

    /**
     * Проверка на наличие объявления по ID объявления
     *
     * @param id ID объявления
     * @return признак наличия объявления
     */
    boolean existsById(Long id);
}
