package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

import java.io.IOException;
import java.util.Optional;

public interface AdvertisementService {
    Optional<ExtendedAd> getAdvertisementInfo(Long id);

    Ads getAllAdvertisements();

    Ads getAdvertisementsOfAuthorizedUser();

    Boolean deleteAdvertisement(Long id);

    Optional<Ad> updateAdvertisementInfo(Long id, CreateOrUpdateAd createOrUpdateAd);

    String  updateAdvertisementImage(Long id, MultipartFile image) throws Exception;

    Ad createAdvertisement(String createOrUpdateAd, MultipartFile image) throws IOException;
}
