package ru.skypro.homework.service;

import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;

public interface AdvertisementService {
    ExtendedAd getAdvertisementInfo(Long id);

    Ads getAllAdvertisements();

    Ads getAdvertisementsOfAuthorizedUser();

    Boolean deleteAdvertisement(Long id);

    Ad updateAdvertisementInfo(Long id, CreateOrUpdateAd createOrUpdateAd);
}
