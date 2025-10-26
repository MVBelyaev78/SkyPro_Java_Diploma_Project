package ru.skypro.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
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
        final Long adEntityId = 1L;
        final AdvertisementEntity adEntity = new AdvertisementEntity();
        adEntity.setId(adEntityId);
        adEntity.setTitle("Глобус");
        adEntity.setDescription("Школьный глобус с политической картой");
        adEntity.setPrice(30);
        adEntity.setUser(userEntity);
        final ExtendedAd extendedAd = new ExtendedAd(
                1L,
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
        when(repository.findById(adEntityId)).thenReturn(Optional.of(adEntity));
        when(mapping.getExtendedAdFromEntity(adEntity)).thenReturn(Optional.of(extendedAd));
        // Then
        assertEquals(Optional.of(extendedAd), service.getAdvertisementInfo(adEntityId));
        verify(repository, times(1)).findById(adEntityId);
        verifyNoMoreInteractions(repository);
        verify(mapping, times(1)).getExtendedAdFromEntity(adEntity);
        verifyNoMoreInteractions(mapping);
    }

    @Test
    public void testGetAdvertisementInfo_withIrrelevantId_returnsEmpty() {
        // Given
        final Long adEntityId = 1L;
        // When
        when(repository.findById(adEntityId)).thenReturn(Optional.empty());
        // Then
        assertEquals(Optional.empty(), service.getAdvertisementInfo(adEntityId));
        verify(repository, times(1)).findById(adEntityId);
        verifyNoMoreInteractions(repository);
        verify(mapping, never()).getExtendedAdFromEntity(any(AdvertisementEntity.class));
        verifyNoMoreInteractions(mapping);
    }

    @Test
    public void testGetAdvertisementInfo_withNullId_returnsEmpty() {
        // Given
        // When
        when(repository.findById(null)).thenReturn(Optional.empty());
        // Then
        assertEquals(Optional.empty(), service.getAdvertisementInfo(null));
        verify(repository, times(1)).findById(null);
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

        final AdvertisementEntity adEntity = new AdvertisementEntity();
        adEntity.setId(1L);
        adEntity.setTitle("Глобус");
        adEntity.setDescription("Школьный глобус с политической картой");
        adEntity.setPrice(30);
        adEntity.setUser(userEntity);
        final List<AdvertisementEntity> adEntityList = List.of(adEntity);

        final Ad ad = new Ad(
                1L,
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
        final Long adEntityId = 1L;
        // When
        when(repository.existsById(adEntityId)).thenReturn(true);
        // Then
        assertEquals(true, service.deleteAdvertisement(adEntityId));
        verify(repository, times(1)).existsById(adEntityId);
        verify(repository, times(1)).deleteById(adEntityId);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void testDeleteAdvertisement_nonExistentAdvertisement_returnsTrue() {
        // Given
        final Long adEntityId = 1L;
        // When
        when(repository.existsById(adEntityId)).thenReturn(false);
        // Then
        assertEquals(false, service.deleteAdvertisement(adEntityId));
        verify(repository, times(1)).existsById(adEntityId);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void testUpdateAdvertisementInfo_withRelevantId_returnsRelevantDto() {
        // Given
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("wertin@bk.ru");
        userEntity.setFirstName("Сергей");
        userEntity.setLastName("Петров");
        userEntity.setPhone("+79356661300");
        userEntity.setRole("USER");
        userEntity.setPassword("qw123456");

        final Long adEntityId = 1L;
        final AdvertisementEntity adInitEntity = new AdvertisementEntity();
        adInitEntity.setId(adEntityId);
        adInitEntity.setTitle("Глобус");
        adInitEntity.setDescription("Школьный глобус с политической картой");
        adInitEntity.setPrice(30);
        adInitEntity.setUser(userEntity);

        final CreateOrUpdateAd createOrUpdateAd = new CreateOrUpdateAd();
        createOrUpdateAd.setTitle("Глобус");
        createOrUpdateAd.setPrice(35);
        createOrUpdateAd.setDescription("Большой школьный глобус с политической картой");

        final AdvertisementEntity adResultEntity = new AdvertisementEntity();
        adResultEntity.setId(adEntityId);
        adResultEntity.setTitle("Глобус");
        adResultEntity.setDescription("Большой школьный глобус с политической картой");
        adResultEntity.setPrice(35);
        adResultEntity.setUser(userEntity);

        final Ad resultAd = new Ad(
                1L,
                "Петров",
                "",
                35,
                "Глобус"
        );
        // When
        when(repository.findById(adEntityId)).thenReturn(Optional.of(adInitEntity));
        when(mapping.getReadyForUpdateEntity(adInitEntity, createOrUpdateAd)).thenReturn(Optional.of(adResultEntity));
        when(repository.save(adResultEntity)).thenReturn(adResultEntity);
        when(mapping.getAdFromEntity(adResultEntity)).thenReturn(resultAd);
        // Then
        assertEquals(Optional.of(resultAd), service.updateAdvertisementInfo(adEntityId, createOrUpdateAd));
        verify(repository, times(1)).findById(adEntityId);
        verify(repository, times(1)).save(adResultEntity);
        verifyNoMoreInteractions(repository);
        verify(mapping, times(1)).getReadyForUpdateEntity(adInitEntity, createOrUpdateAd);
        verify(mapping).getAdFromEntity(adResultEntity);
        verifyNoMoreInteractions(mapping);
    }

    @Test
    public void testUpdateAdvertisementInfo_withIrrelevantId_returnsRelevantDto() {
        // Given
        final Long adEntityId = 1L;
        // When
        when(repository.findById(adEntityId)).thenReturn(Optional.empty());
        // Then
        assertEquals(Optional.empty(),
                service.updateAdvertisementInfo(adEntityId, any(CreateOrUpdateAd.class)));
        verify(repository, times(1)).findById(adEntityId);
        verifyNoMoreInteractions(repository);
        verify(mapping, never()).getReadyForUpdateEntity(any(AdvertisementEntity.class), any(CreateOrUpdateAd.class));
        verify(mapping, never()).getAdFromEntity(any(AdvertisementEntity.class));
        verifyNoMoreInteractions(mapping);
    }
}
