package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.AdvertisementEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapping.AdvertisementMapping;
import ru.skypro.homework.repository.AdvertisementRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdvertisementService;
import ru.skypro.homework.service.ImageService;

import java.util.List;
import java.util.Optional;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {
    @Autowired
    AdvertisementRepository repository;

    @Autowired
    AdvertisementMapping mapping;

    @Autowired
    ImageService imageService;

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<ExtendedAd> getAdvertisementInfo(Long id) {
        return mapping.getExtendedAdFromEntity(repository.findById(id));
    }

    @Override
    public Ads getAllAdvertisements() {
        return new Ads(1, List.of(new Ad(0L, "string", "string", 0, "string")));
    }

    @Override
    public Ads getAdvertisementsOfAuthorizedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntity author = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Пользователь " + username + " не найден"));

        List<AdvertisementEntity> ads = repository.findByUserId(author.getId());

        return mapping.getAdsFromEntities(ads);
    }

    @Override
    public Boolean deleteAdvertisement(Long id) {
        return true;
    }

    @Override
    public Optional<Ad> updateAdvertisementInfo(Long id, CreateOrUpdateAd createOrUpdateAd) {
        final Optional<AdvertisementEntity> entity = repository.findById(id);
        entity.ifPresent(e -> {
            e.setTitle(createOrUpdateAd.getTitle());
            e.setPrice(createOrUpdateAd.getPrice());
            e.setDescription(createOrUpdateAd.getDescription());
        });
        return entity.map(e -> mapping.getAdFromEntity(repository.save(e)));
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
