package ru.skypro.homework.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.ExtendedAd;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AdvertisementServiceIntegrationTest {
    @Autowired
    private AdvertisementService advertisementService;

    @Test
    public void should_getAdvertisementInfo_succeed() {
        // Given
        final Optional<ExtendedAd> expectedExtendedAd = Optional.of(new ExtendedAd(
                1L,
                "Антон",
                "Сергеев",
                "Роман Л. Толстого в четырех томах",
                "ncwhite@bk.ru",
                "",
                "+784320006660",
                10,
                "Война и мир"));
        // When
        final Optional<ExtendedAd> actualExtendedAd = advertisementService.getAdvertisementInfo(1L);
        // Then
        assertEquals(expectedExtendedAd, actualExtendedAd);
    }

    @Test
    public void should_getAdvertisementInfo_not_found() {
        // Given
        final Optional<ExtendedAd> expectedExtendedAd = Optional.empty();
        // When
        final Optional<ExtendedAd> actualExtendedAd = advertisementService.getAdvertisementInfo(100L);
        // Then
        assertEquals(expectedExtendedAd, actualExtendedAd);
    }

    @Test
    public void should_getAllAdvertisements_succeed() {
        // Given
        final Ads expectedAds = new Ads(2,
                List.of(new Ad(1L, "Сергеев", "", 10, "Война и мир"),
                        new Ad(2L, "Сергеев", "", 3, "Драма на охоте")));
        // When
        final Ads actualAds = advertisementService.getAllAdvertisements();
        final List<Ad> adList = actualAds.getResults();
        adList.sort(Comparator.comparing(Ad::getPk));
        actualAds.setResults(adList);
        // Then
        assertEquals(expectedAds, actualAds);
    }
}
