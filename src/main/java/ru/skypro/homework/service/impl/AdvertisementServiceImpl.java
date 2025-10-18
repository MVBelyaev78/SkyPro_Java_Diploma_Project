package ru.skypro.homework.service.impl;

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
import java.util.stream.Collectors;

/**
 * Реализация сервиса для работы с объявлениями.
 */
@Service
public class AdvertisementServiceImpl implements AdvertisementService {
    @Autowired
    AdvertisementRepository repository;

    @Autowired
    AdvertisementMapping mapping;

    @Autowired
    ImageService imageService;

    /**
     * Получает информацию о конкретном объявлении по его идентификатору.
     *
     * @param id идентификатор объявления
     * @return объект ExtendedAd, содержащий информацию о объявлении, если оно найдено
     */
    @Override
    public Optional<ExtendedAd> getAdvertisementInfo(Long id) {
        return mapping.getExtendedAdFromEntity(repository.findById(id));
    }

    /**
     * Получает все объявления.
     *
     * @return объект Ads, содержащий список всех объявлений
     */
    @Override
    public Ads getAllAdvertisements() {
        List<Ad> ads = repository.findAll().stream()
                .map(mapping::getAdFromEntity)
                .collect(Collectors.toList());
        return new Ads(ads.size(), ads);
    }

    /**
     * Получает объявления авторизованного пользователя.
     *
     * @return объект Ads, содержащий список объявлений пользователя
     */
    @Override
    public Ads getAdvertisementsOfAuthorizedUser() {
        return new Ads(1, List.of(new Ad(0L, "string", "string", 0, "string")));
    }

    /**
     * Удаляет объявление по его идентификатору.
     *
     * @param id идентификатор объявления
     * @return true, если удаление прошло успешно
     */
    @Override
    public Boolean deleteAdvertisement(Long id) {
        return true;
    }

    /**
     * Обновляет информацию об объявлении.
     *
     * @param id идентификатор объявления
     * @param createOrUpdateAd объект с новыми данными объявления
     * @return обновленный объект Ad, если обновление прошло успешно
     */
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

    /**
     * Обновляет изображение объявления.
     *
     * @param id идентификатор объявления
     * @param image файл изображения
     * @return объект CreateOrUpdateComment с результатом операции
     * @throws Exception если произошла ошибка при сохранении изображения
     */
    @Override
    public CreateOrUpdateComment updateAdvertisementImage(Long id, MultipartFile image) throws Exception {
        imageService.saveImage(image);
        return new CreateOrUpdateComment("string");
    }

    /**
     * Создает новое объявление.
     *
     * @param createOrUpdateAd объект с данными нового объявления
     * @param image файл изображения для объявления
     * @return созданный объект Ad
     * @throws Exception если произошла ошибка при сохранении изображения
     */
    @Override
    public Ad createAdvertisement(CreateOrUpdateAd createOrUpdateAd, MultipartFile image) throws Exception {
        imageService.saveImage(image);
        return new Ad(0L, "string", "string", createOrUpdateAd.getPrice(), createOrUpdateAd.getTitle());
    }
}

