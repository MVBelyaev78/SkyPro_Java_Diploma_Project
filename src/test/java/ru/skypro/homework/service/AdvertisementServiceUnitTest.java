package ru.skypro.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdvertisementEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapping.AdvertisementMapping;
import ru.skypro.homework.repository.AdvertisementRepository;
import ru.skypro.homework.service.impl.AdvertisementServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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
        verifyNoMoreInteractions(repository);
        verify(mapping, times(1)).getExtendedAdFromEntity(adEntity);
        verifyNoMoreInteractions(mapping);
    }

    @Test
    public void testGetAdvertisementInfo_withIrrelevantId_returnsEmpty() {
        // Given
        final Long idAd = 1L;
        // When
        when(repository.findById(idAd)).thenReturn(Optional.empty());
        // Then
        assertEquals(Optional.empty(), service.getAdvertisementInfo(idAd));
        verify(repository, times(1)).findById(idAd);
        verifyNoMoreInteractions(repository);
        verify(mapping, never()).getExtendedAdFromEntity(any());
    }

    @Test
    public void testGetAdvertisementInfo_withNullId_returnsEmpty() {
        // Given
        final Long idAd = null;
        // When
        when(repository.findById(idAd)).thenReturn(Optional.empty());
        // Then
        assertEquals(Optional.empty(), service.getAdvertisementInfo(idAd));
        verify(repository, times(1)).findById(idAd);
        verifyNoMoreInteractions(repository);
        verify(mapping, never()).getExtendedAdFromEntity(any());
    }

    @Test
    public void testGetAllAdvertisements_withoutArguments_returnsFullyCompletedAds() {
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
        final List<AdvertisementEntity> adEntityList = List.of(adEntity);
        final Ad ad = new Ad(
                idAd,
                "Сергей Петров",
                "",
                30,
                "Глобус"
        );
        final List<Ad> adList = List.of(ad);
        final Ads ads = new Ads(adEntityList.size(), adList);
        // When
        when(repository.findAll()).thenReturn(adEntityList);
        when(mapping.getAdsFromEntities(adEntityList)).thenReturn(ads);
        // Then
        assertEquals(ads, service.getAllAdvertisements());
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
        verify(mapping, times(1)).getAdsFromEntities(adEntityList);
        verifyNoMoreInteractions(mapping);
    }

    @Test
    public void testGetAllAdvertisements_withoutArguments_returnsBlankAds() {
        // Given
        final Ads ads = new Ads(0, List.of());
        // When
        when(repository.findAll()).thenReturn(List.of());
        when(mapping.getAdsFromEntities(List.of())).thenReturn(ads);
        // Then
        assertEquals(ads, service.getAllAdvertisements());
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
        verify(mapping, atMost(1)).getAdsFromEntities(List.of());
        verifyNoMoreInteractions(mapping);
    }

    @Test
    public void testDeleteAdvertisement_existentAdvertisement_returnsTrue() {
        // Given
        final Long idAd = 1L;
        // When
        when(repository.existsById(idAd)).thenReturn(true);
        // Then
        assertEquals(true, service.deleteAdvertisement(idAd));
        verify(repository, times(1)).existsById(idAd);
        verify(repository, times(1)).deleteById(idAd);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void testDeleteAdvertisement_nonExistentAdvertisement_returnsTrue() {
        // Given
        final Long idAd = 1L;
        // When
        when(repository.existsById(idAd)).thenReturn(false);
        // Then
        assertEquals(false, service.deleteAdvertisement(idAd));
        verify(repository, times(1)).existsById(idAd);
        verifyNoMoreInteractions(repository);
    }
}
