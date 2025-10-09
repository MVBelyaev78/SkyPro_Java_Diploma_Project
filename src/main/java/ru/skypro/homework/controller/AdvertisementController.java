package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.service.AdvertisementService;

/**
 * API для управления объявлениями
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
@Tag(name = "Advertisement Controller", description = "API для управления объявлениями")
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    /**
     * Получение информации об объявлении
     *
     * @param id Идентификатор объявления
     * @return Расширенная информация об объявлении
     */
    @Operation(summary = "Получение информации об объявлении",
            description = "Получение подробной информации об объявлении")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = AdvertisementService.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не зарегистрирован в системе"),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> getAdvertisementInfo(@PathVariable Long id) {
        return ResponseEntity.ok(advertisementService.getAdvertisementInfo(id));
    }

    @GetMapping("")
    @Operation(summary = "Получение всех объявлений",
            description = "Получение краткой информации о каждом из всех объявлений в системе")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = AdvertisementService.class)))
    })
    public ResponseEntity<Ads> getAllAdvertisements() {
        return ResponseEntity.ok(advertisementService.getAllAdvertisements());
    }

    @GetMapping("/me")
    @Operation(summary = "Получение объявлений авторизованного пользователя",
            description = "Получение краткой информации о каждом объявлении авторизованного пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = AdvertisementService.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не зарегистрирован в системе")
    })
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
