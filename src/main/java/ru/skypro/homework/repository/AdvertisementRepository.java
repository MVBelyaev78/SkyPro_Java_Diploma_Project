package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.AdvertisementEntity;

public interface AdvertisementRepository extends JpaRepository<AdvertisementEntity, Long> {
}
