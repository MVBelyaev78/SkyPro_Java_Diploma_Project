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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdvertisementServiceUnitTest {
    @Mock
    private AdvertisementRepository repository;

    @Mock
    private AdvertisementMapping mapping;

    @InjectMocks
    AdvertisementServiceImpl service;

    @Test
    public void testGetAdvertisementInfo_withRelevantId_returnsRelevantDto() {
        // Given
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("wertin@bk.ru");
        userEntity.setFirstName("Сергей");
        userEntity.setLastName("Петров");
        userEntity.setPhone("+79356661300");
        userEntity.setRole("USER");
        userEntity.setPassword("qw123456");
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
        when(mapping.getExtendedAdFromEntity(adEntity)).thenReturn(Optional.of(extendedAd));
        // Then
        assertEquals(Optional.of(extendedAd), service.getAdvertisementInfo(idAd));
        verify(repository, times(1)).findById(idAd);
        verify(mapping, times(1)).getExtendedAdFromEntity(adEntity);
    }
}
