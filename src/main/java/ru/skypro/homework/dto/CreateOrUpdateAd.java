package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Поля объявления, которые меняются пользователем при создании/обновдении объявления
 * Применяется в API для управления объявлениями
 */
@Data
@AllArgsConstructor
@Schema(description = "Поля объявления, которые меняются пользователем при создании/обновдении объявления. Применяется в API для управления объявлениями")
public class CreateOrUpdateAd {
    /**
     * Краткое описание товара
     */
    @Schema(description = "Описание товара",
            example = "Книга \"Грокаем алгоритмы\"",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    /**
     * Цена
     */
    @Schema(description = "Цена",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer price;

    /**
     * Развернутое описание товара
     */
    @Schema(description = "Описание товара",
            example = "Книга \"Грокаем алгоритмы\" в хорошем состоянии",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
}
