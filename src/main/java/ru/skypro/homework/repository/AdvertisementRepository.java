package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.AdvertisementEntity;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<AdvertisementEntity, Long> {
    List<AdvertisementEntity> findByUserId(Long userId);
}
