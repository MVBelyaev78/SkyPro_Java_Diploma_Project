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
import ru.skypro.homework.component.mapping.AdvertisementMapping;
import ru.skypro.homework.repository.AdvertisementRepository;
import ru.skypro.homework.service.impl.AdvertisementServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AdvertisementServiceTest {
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
        final Long entityId = 1L;
        final AdvertisementEntity entity = new AdvertisementEntity();
        entity.setId(entityId);
        entity.setTitle("Глобус");
        entity.setDescription("Школьный глобус с политической картой");
        entity.setPrice(30);
        entity.setUser(userEntity);
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
        when(repository.findById(entityId)).thenReturn(Optional.of(entity));
        when(mapping.getExtendedAdFromEntity(entity)).thenReturn(Optional.of(extendedAd));
        // Then
        assertEquals(Optional.of(extendedAd), service.getAdvertisementInfo(entityId));
        verify(repository, times(1)).findById(entityId);
        verifyNoMoreInteractions(repository);
        verify(mapping, times(1)).getExtendedAdFromEntity(entity);
        verifyNoMoreInteractions(mapping);
    }

    @Test
    public void testGetAdvertisementInfo_withIrrelevantId_returnsEmpty() {
        // Given
        final Long entityId = 1L;
        // When
        when(repository.findById(entityId)).thenReturn(Optional.empty());
        // Then
        assertEquals(Optional.empty(), service.getAdvertisementInfo(entityId));
        verify(repository, times(1)).findById(entityId);
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

        final AdvertisementEntity entity = new AdvertisementEntity();
        entity.setId(1L);
        entity.setTitle("Глобус");
        entity.setDescription("Школьный глобус с политической картой");
        entity.setPrice(30);
        entity.setUser(userEntity);
        final List<AdvertisementEntity> adEntityList = List.of(entity);

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
        final Long entityId = 1L;
        // When
        when(repository.existsById(entityId)).thenReturn(true);
        // Then
        assertEquals(true, service.deleteAdvertisement(entityId));
        verify(repository, times(1)).existsById(entityId);
        verify(repository, times(1)).deleteById(entityId);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void testDeleteAdvertisement_nonExistentAdvertisement_returnsTrue() {
        // Given
        final Long entityId = 1L;
        // When
        when(repository.existsById(entityId)).thenReturn(false);
        // Then
        assertEquals(false, service.deleteAdvertisement(entityId));
        verify(repository, times(1)).existsById(entityId);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void testUpdateAdvertisementInfo_withRelevantEntityId_withRelevantCreateOrUpdateAd_returnsRelevantDto() {
        // Given
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("wertin@bk.ru");
        userEntity.setFirstName("Сергей");
        userEntity.setLastName("Петров");
        userEntity.setPhone("+79356661300");
        userEntity.setRole("USER");
        userEntity.setPassword("qw123456");

        final Long entityId = 1L;
        final AdvertisementEntity initEntity = new AdvertisementEntity();
        initEntity.setId(entityId);
        initEntity.setTitle("Глобус");
        initEntity.setDescription("Школьный глобус с политической картой");
        initEntity.setPrice(30);
        initEntity.setUser(userEntity);

        final CreateOrUpdateAd createOrUpdateAd = new CreateOrUpdateAd();
        createOrUpdateAd.setTitle("Глобус");
        createOrUpdateAd.setPrice(35);
        createOrUpdateAd.setDescription("Большой школьный глобус с политической картой");

        final AdvertisementEntity resultEntity = new AdvertisementEntity();
        resultEntity.setId(entityId);
        resultEntity.setTitle("Глобус");
        resultEntity.setDescription("Большой школьный глобус с политической картой");
        resultEntity.setPrice(35);
        resultEntity.setUser(userEntity);

        final Ad resultAd = new Ad(
                1L,
                "Петров",
                "",
                35,
                "Глобус"
        );
        // When
        when(repository.findById(entityId)).thenReturn(Optional.of(initEntity));
        when(mapping.getReadyForUpdateEntity(initEntity, createOrUpdateAd)).thenReturn(Optional.of(resultEntity));
        when(repository.save(resultEntity)).thenReturn(resultEntity);
        when(mapping.getAdFromEntity(resultEntity)).thenReturn(resultAd);
        // Then
        assertEquals(Optional.of(resultAd), service.updateAdvertisementInfo(entityId, createOrUpdateAd));
        verify(repository, times(1)).findById(entityId);
        verify(repository, times(1)).save(resultEntity);
        verifyNoMoreInteractions(repository);
        verify(mapping, times(1)).getReadyForUpdateEntity(initEntity, createOrUpdateAd);
        verify(mapping).getAdFromEntity(resultEntity);
        verifyNoMoreInteractions(mapping);
    }

    @Test
    public void testUpdateAdvertisementInfo_withIrrelevantEntityId_returnsEmptyDto() {
        // Given
        final Long entityId = 1L;
        // When
        when(repository.findById(entityId)).thenReturn(Optional.empty());
        // Then
        assertEquals(Optional.empty(),
                service.updateAdvertisementInfo(entityId, any(CreateOrUpdateAd.class)));
        verify(repository, times(1)).findById(entityId);
        verifyNoMoreInteractions(repository);
        verify(mapping, never()).getReadyForUpdateEntity(any(AdvertisementEntity.class), any(CreateOrUpdateAd.class));
        verify(mapping, never()).getAdFromEntity(any(AdvertisementEntity.class));
        verifyNoMoreInteractions(mapping);
    }

    @Test
    public void testUpdateAdvertisementInfo_withRelevantEntityId_withEmptyCreateOrUpdateAd_returnsRelevantDto() {
        // Given
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("wertin@bk.ru");
        userEntity.setFirstName("Сергей");
        userEntity.setLastName("Петров");
        userEntity.setPhone("+79356661300");
        userEntity.setRole("USER");
        userEntity.setPassword("qw123456");

        final Long entityId = 1L;
        final AdvertisementEntity entity = new AdvertisementEntity();
        entity.setId(entityId);
        entity.setTitle("Глобус");
        entity.setDescription("Школьный глобус с политической картой");
        entity.setPrice(30);
        entity.setUser(userEntity);

        final Ad resultAd = new Ad(
                1L,
                "Петров",
                "",
                30,
                "Глобус"
        );
        // When
        when(repository.findById(entityId)).thenReturn(Optional.of(entity));
        when(mapping.getReadyForUpdateEntity(entity, null)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapping.getAdFromEntity(entity)).thenReturn(resultAd);
        // Then
        assertEquals(Optional.of(resultAd), service.updateAdvertisementInfo(entityId, null));
        verify(repository, times(1)).findById(entityId);
        verify(repository, times(1)).save(entity);
        verifyNoMoreInteractions(repository);
        verify(mapping, times(1)).getReadyForUpdateEntity(entity, null);
        verify(mapping, times(1)).getAdFromEntity(entity);
        verifyNoMoreInteractions(mapping);
    }
}
