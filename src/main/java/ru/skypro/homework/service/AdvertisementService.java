package ru.skypro.homework.service;

import ru.skypro.homework.dto.ExtendedAd;

public interface AdvertisementService {
    ExtendedAd getAdvertisementInfo(Long id);
}
