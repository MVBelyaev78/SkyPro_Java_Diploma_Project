package ru.skypro.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdvertisementEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapping.AdvertisementMapping;
import ru.skypro.homework.repository.AdvertisementRepository;
import ru.skypro.homework.service.impl.AdvertisementServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdvertisementServiceUnitTest {
    @Mock
    private AdvertisementRepository repository;

    @Mock
    private AdvertisementMapping mapping;

    @InjectMocks
    AdvertisementServiceImpl service;

    @Test
    public void should_getAdvertisementInfo_succeed() {
        // Given
        final Long idUser = 1L;
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(idUser);
        userEntity.setEmail("wertin@bk.ru");
        userEntity.setFirstName("Сергей");
        userEntity.setLastName("Петров");
        userEntity.setPhone("+79356661300");
        userEntity.setRole("USER");
        userEntity.setPassword("123456");
        final Long idAd = 1L;
        final AdvertisementEntity adEntity = new AdvertisementEntity();
        adEntity.setId(idAd);
        adEntity.setTitle("Глобус");
        adEntity.setDescription("Школьный глобус с политической картой");
        adEntity.setPrice(30);
        adEntity.setUser(userEntity);
        final ExtendedAd extendedAd = new ExtendedAd(
                idAd,
                "Сергей",
                "Петров",
                "Школьный глобус с политической картой",
                "wertin@bk.ru",
                "",
                "+79356661300",
                30,
                "Глобус"
                );
        // When
        when(repository.findById(idAd)).thenReturn(Optional.of(adEntity));
        when(mapping.getExtendedAdFromEntity(Optional.of(adEntity))).thenReturn(Optional.of(extendedAd));
        // Then
        assertEquals(Optional.of(extendedAd), service.getAdvertisementInfo(idAd));
    }
}
