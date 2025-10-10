package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.service.AdvertisementService;

import java.util.List;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {
    @Override
    public ExtendedAd getAdvertisementInfo(Long id) {
        return new ExtendedAd(0L, "string", "string", "string", "string", "string", "string", 0, "string");
    }

    @Override
    public Ads getAllAdvertisements() {
        return new Ads(1, List.of(new Ad(0L, "string", "string", 0, "string")));
    }

    @Override
    public Ads getAdvertisementsOfAuthorizedUser() {
        return new Ads(1, List.of(new Ad(0L, "string", "string", 0, "string")));
    }

    @Override
    public Boolean deleteAdvertisement(Long id) {
        return true;
    }

    @Override
    public Ad updateAdvertisementInfo(Long id, CreateOrUpdateAd createOrUpdateAd) {
        return new Ad(0L, "string", "string", createOrUpdateAd.getPrice(), createOrUpdateAd.getTitle());
    }
}
