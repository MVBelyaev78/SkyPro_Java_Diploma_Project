package ru.skypro.homework.mapping;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdvertisementEntity;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AdvertisementMapping {
    public Ad getAdFromEntity(AdvertisementEntity entity) {
        return new Ad(entity.getId(),
                entity.getUser().getLastName(),
                entity.getImage().isPresent() ? entity.getImage().get().getFilePath() : "",
                entity.getPrice(),
                entity.getTitle());
    }

    public Ads getAdsFromEntities(List<AdvertisementEntity> advertisementEntityList) {
        if (advertisementEntityList == null || advertisementEntityList.isEmpty()) {
            return null;
        }
        List<Ad> adList = advertisementEntityList
                .stream()
                .map(this::getAdFromEntity)
                .collect(Collectors.toList());

        return new Ads(adList.size(), adList);
    }

    public Optional<ExtendedAd> getExtendedAdFromEntity(AdvertisementEntity entity) {
        if (entity == null) {
            return Optional.empty();
        }
        final ExtendedAd extendedAd = new ExtendedAd();
        extendedAd.setPk(entity.getId());
        extendedAd.setAuthorFirstName(entity.getUser().getFirstName());
        extendedAd.setAuthorLastName(entity.getUser().getLastName());
        extendedAd.setDescription(entity.getDescription());
        extendedAd.setEmail(entity.getUser().getEmail());
        extendedAd.setImage(entity.getImage().isPresent() ? entity.getImage().get().getFilePath() : "");
        extendedAd.setPhone(entity.getUser().getPhone());
        extendedAd.setPrice(entity.getPrice());
        extendedAd.setTitle(entity.getTitle());

        return Optional.of(extendedAd);
    }

    public AdvertisementEntity getEntityFromAd(CreateOrUpdateAd createOrUpdateAd, UserEntity userEntity, ImageEntity imageEntity) {
        if (createOrUpdateAd == null || userEntity == null) {
            return null;
        }
        AdvertisementEntity entity = new AdvertisementEntity();
        entity.setUser(userEntity);
        entity.setTitle(createOrUpdateAd.getTitle());
        entity.setDescription(createOrUpdateAd.getDescription());
        entity.setPrice(createOrUpdateAd.getPrice());
        entity.setImage(imageEntity);

        return entity;
    }

    public Optional<AdvertisementEntity> getReadyForUpdateEntity(AdvertisementEntity entity,
                                                                 CreateOrUpdateAd createOrUpdateAd) {
        if (entity == null) {
            return Optional.empty();
        }
        if (createOrUpdateAd == null) {
            return Optional.of(entity);
        }
        AdvertisementEntity entityResult = new AdvertisementEntity();
        entityResult.setId(entity.getId());
        entityResult.setTitle(createOrUpdateAd.getTitle());
        entityResult.setDescription(createOrUpdateAd.getDescription());
        entityResult.setPrice(createOrUpdateAd.getPrice());
        entityResult.setUser(entity.getUser());
        return Optional.of(entityResult);
    }
}
