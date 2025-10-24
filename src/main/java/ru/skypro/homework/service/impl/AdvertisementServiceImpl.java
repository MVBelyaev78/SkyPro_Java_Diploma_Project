package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.AdvertisementEntity;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapping.AdvertisementMapping;
import ru.skypro.homework.repository.AdvertisementRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdvertisementService;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для работы с объявлениями.
 */
@Slf4j
@Service
@Transactional
public class AdvertisementServiceImpl implements AdvertisementService {
    @Autowired
    private AdvertisementRepository repository;

    @Autowired
    private AdvertisementMapping mapping;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Получает информацию о конкретном объявлении по его идентификатору.
     *
     * @param id идентификатор объявления
     * @return объект ExtendedAd, содержащий информацию о объявлении, если оно найдено
     */
    @Override
    public Optional<ExtendedAd> getAdvertisementInfo(Long id) {
        final Optional<AdvertisementEntity> entity = repository.findById(id);
        if (entity.isEmpty()) {
            return Optional.empty();
        }
        return mapping.getExtendedAdFromEntity(entity.get());
    }

    /**
     * Получает все объявления.
     *
     * @return объект Ads, содержащий список всех объявлений
     */
    @Override
    public Ads getAllAdvertisements() {
        final List<Ad> ads = repository.findAll().stream()
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
        final UserEntity authorEntity = userRepository.findByEmail(getAuthentication().getName());
        return mapping.getAdsFromEntities(repository.findByUserId(authorEntity.getId()));
    }

    /**
     * Удаляет объявление по его идентификатору.
     *
     * @param id идентификатор объявления
     * @return true, если удаление прошло успешно, иначе false
     */
    @Override
    public Boolean deleteAdvertisement(Long id) {
        // Проверяем, существует ли объявление с данным идентификатором
        if (repository.existsById(id)) {
            repository.deleteById(id); // Удаляем объявление из базы данных
            return true; // Возвращаем true, если удаление прошло успешно
        }
        return false; // Возвращаем false, если объявления с данным идентификатором не существует
    }

    /**
     * Обновляет информацию об объявлении.
     *
     * @param id               идентификатор объявления
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
     * @param id    идентификатор объявления
     * @param image файл изображения
     * @return объект CreateOrUpdateComment с результатом операции
     * @throws Exception если произошла ошибка при сохранении изображения
     */
    @Override
    public String updateAdvertisementImage(Long id, MultipartFile image) throws Exception {
        AdvertisementEntity adEntity = repository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        ImageEntity imageEntity = imageService.saveImage(image);
        adEntity.setImage(imageEntity);
        repository.save(adEntity);

        return imageEntity.getFilePath();
    }

    /**
     * Создает новое объявление.
     *
     * @param createOrUpdateAd объект с данными нового объявления
     * @param image            файл изображения для объявления
     * @return созданный объект Ad
     * @throws IOException если произошла ошибка при сохранении изображения
     */
    @Override
    public Ad createAdvertisement(CreateOrUpdateAd createOrUpdateAd, MultipartFile image) throws IOException {
        final UserEntity authorEntity = userRepository.findByEmail(getAuthentication().getName());
        final AdvertisementEntity entity = mapping
                .getEntityFromAd(createOrUpdateAd, authorEntity, imageService.saveImage(image));

        return mapping.getAdFromEntity(repository.save(entity));
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
