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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdvertisementService;

/**
 * API для управления объявлениями
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
@Tag(name = "Объявления", description = "API для управления объявлениями")
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
                    content = @Content(schema = @Schema(implementation = ExtendedAd.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = ""))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getAdvertisementInfo(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(advertisementService.getAdvertisementInfo(id)
                    .orElseThrow(IllegalArgumentException::new));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Получение всех объявлений
     *
     * @return Набор краткой информации об объявлениях
     */
    @GetMapping("")
    @Operation(summary = "Получение всех объявлений",
            description = "Получение краткой информации о каждом из всех объявлений в системе")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = Ads.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<Ads> getAllAdvertisements() {
        return ResponseEntity.ok(advertisementService.getAllAdvertisements());
    }

    /**
     * Получение объявлений авторизованного пользователя
     *
     * @return Набор краткой информации об объявлениях
     */
    @GetMapping("/me")
    @Operation(summary = "Получение объявлений авторизованного пользователя",
            description = "Получение краткой информации о каждом объявлении авторизованного пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = Ads.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = ""))
    })
    public ResponseEntity<Ads> getAdvertisementsOfAuthorizedUser() {
        return ResponseEntity.ok(advertisementService.getAdvertisementsOfAuthorizedUser());
    }

    /**
     * Удаление объявления
     *
     * @param id Идентификатор объявления
     * @return ResponseEntity с кодом HTTP-ответа
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление объявления",
            description = "Удаление объявления")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable("id") Long id) {
        if (advertisementService.deleteAdvertisement(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Обновление информации об объявлении
     *
     * @param id               Идентификатор объявления
     * @param createOrUpdateAd Обновляемые поля объявления
     * @return информация об объявлении
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Обновление информации об объявлении",
            description = "Обновление информации об объявлении")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = Ad.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = ""))
    })
    public ResponseEntity<Ad> updateAdvertisementInfo(@PathVariable("id") Long id,
                                                      @RequestBody CreateOrUpdateAd createOrUpdateAd) {
        try {
            return ResponseEntity.ok(advertisementService.updateAdvertisementInfo(id, createOrUpdateAd)
                    .orElseThrow(IllegalArgumentException::new));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Обновление картинки объявления
     *
     * @param id    Идентификатор объявления
     * @param image Картинка
     */
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление картинки объявления",
            description = "Обновление картинки объявления")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = ""))
    })
    public ResponseEntity<?> updateAdvertisementImage(@PathVariable("id") Long id,
                                                                          @RequestBody MultipartFile image) {
        try {
            return ResponseEntity.ok(advertisementService.updateAdvertisementImage(id, image));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Добавление объявления
     *
     * @param createOrUpdateAd Поля объявления
     * @param image Картинка
     * @return информация об объявлении
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Добавление объявления",
            description = "Добавление объявления")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(schema = @Schema(implementation = Ad.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = ""))
    })
    public ResponseEntity<Ad> createAdvertisement(@RequestPart("properties") CreateOrUpdateAd createOrUpdateAd,
                                                  @RequestPart("image") MultipartFile image) {
        try {
            return ResponseEntity.ok(advertisementService.createAdvertisement(createOrUpdateAd, image));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
