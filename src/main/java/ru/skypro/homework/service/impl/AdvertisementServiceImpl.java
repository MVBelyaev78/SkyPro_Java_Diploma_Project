package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.service.AdvertisementService;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {
    @Override
    public ExtendedAd getAdvertisementInfo(Long id) {
        return new ExtendedAd(0L, "string", "string", "string", "string", "string", "string", 0, "string");
    }

    @Override
    public Ads getAllAdvertisements() {
        return new Ads(1, new Ad(0L, "string", "string", 0, "string"));
    }

    @Override
    public Ads getAdvertisementsOfAuthorizedUser() {
        return new Ads(1, new Ad(0L, "string", "string", 0, "string"));
    }
}
