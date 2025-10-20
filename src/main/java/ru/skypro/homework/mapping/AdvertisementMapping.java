package ru.skypro.homework.mapping;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.AdvertisementEntity;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AdvertisementMapping {
    public Ad getAdFromEntity(AdvertisementEntity entity) {
        return new Ad(entity.getId(),
                entity.getUser().getLastName(),
                entity.getImage().isPresent() ? entity.getImage().get().getName() : "",
                entity.getPrice(),
                entity.getTitle());
    }

    public Ads getAdsFromEntities(List<AdvertisementEntity> advertisementEntityList) {
        List<Ad> adList = advertisementEntityList
                .stream()
                .map(this::getAdFromEntity)
                .collect(Collectors.toList());
        return new Ads(adList.size(), adList);
    }

    public Optional<ExtendedAd> getExtendedAdFromEntity(Optional<AdvertisementEntity> entity) {
        return entity.map(e -> new ExtendedAd(
                e.getId(),
                e.getUser().getFirstName(),
                e.getUser().getLastName(),
                e.getDescription(),
                e.getUser().getEmail(),
                e.getImage().isPresent() ? e.getImage().get().getName() : "",
                e.getUser().getPhone(),
                e.getPrice(),
                e.getTitle()));
    }

    public AdvertisementEntity getEntityFromAd(Optional<CreateOrUpdateAd> createOrUpdateAd, Optional<UserEntity> userEntity, Optional<ImageEntity> imageEntity) {
        AdvertisementEntity entity = new AdvertisementEntity();
        createOrUpdateAd.ifPresent(ad -> {
            entity.setTitle(ad.getTitle());
            entity.setDescription(ad.getDescription());
            entity.setPrice(ad.getPrice());
        });
        userEntity.ifPresent(entity::setUser);
        imageEntity.ifPresent(entity::setImage);
        return entity;
    }
}
