package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.service.AdvertisementService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> getAdvertisementInfo(@PathVariable Long id) {
        return ResponseEntity.ok(advertisementService.getAdvertisementInfo(id));
    }

    @GetMapping("")
    public ResponseEntity<Ads> getAllAdvertisements() {
        return ResponseEntity.ok(advertisementService.getAllAdvertisements());
    }

    @GetMapping("/me")
    public ResponseEntity<Ads> getAdvertisementsOfAuthorizedUser() {
        return ResponseEntity.ok(advertisementService.getAdvertisementsOfAuthorizedUser());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvertisement(Long id) {
        if (advertisementService.deleteAdvertisement(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
