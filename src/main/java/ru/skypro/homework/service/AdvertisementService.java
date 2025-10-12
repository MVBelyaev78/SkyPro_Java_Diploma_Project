package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

public interface AdvertisementService {
    ExtendedAd getAdvertisementInfo(Long id);

    Ads getAllAdvertisements();

    Ads getAdvertisementsOfAuthorizedUser();

    Boolean deleteAdvertisement(Long id);

    Ad updateAdvertisementInfo(Long id, CreateOrUpdateAd createOrUpdateAd);

    CreateOrUpdateComment updateAdvertisementImage(Long id, MultipartFile image) throws Exception;

    Ad createAdvertisement(CreateOrUpdateAd createOrUpdateAd, MultipartFile image) throws Exception;
}
