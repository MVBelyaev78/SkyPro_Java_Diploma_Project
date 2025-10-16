package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.AdvertisementEntity;
import ru.skypro.homework.mapping.AdvertisementMapping;
import ru.skypro.homework.repository.AdvertisementRepository;
import ru.skypro.homework.service.AdvertisementService;
import ru.skypro.homework.service.ImageService;

import java.util.List;
import java.util.Optional;

//@RequiredArgsConstructor
@Service
public class AdvertisementServiceImpl implements AdvertisementService {
    @Autowired
    AdvertisementRepository repository;

    @Autowired
    AdvertisementMapping mapping;

    @Autowired
    ImageService imageService;

    @Override
    public Optional<ExtendedAd> getAdvertisementInfo(Long id) {
        final Optional<AdvertisementEntity> entity = repository.findById(id);
        return mapping.getExtendedAdFromEntity(entity);
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

    @Override
    public CreateOrUpdateComment updateAdvertisementImage(Long id, MultipartFile image) throws Exception {
        imageService.saveImage(image);
        return new CreateOrUpdateComment("string");
    }

    @Override
    public Ad createAdvertisement(CreateOrUpdateAd createOrUpdateAd, MultipartFile image) throws Exception {
        imageService.saveImage(image);
        return new Ad(0L, "string", "string", createOrUpdateAd.getPrice(), createOrUpdateAd.getTitle());
    }
}
