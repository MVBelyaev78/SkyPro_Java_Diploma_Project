package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Набор краткой информации об объявлениях
 * Применяется в API для получения набора объявлений
 */
@Data
@AllArgsConstructor
@Schema(description = "Набор краткой информации об объявлениях. Применяется в API для получения набора объявлений")
public class Ads {
    /**
     * Количество объявлений в наборе
     */
    @Schema(description = "Количество объявлений в наборе",
            requiredMode = Schema.RequiredMode.REQUIRED)
    Integer count;

    /**
     * Массив краткой информации об объявлениях
     */
    @Schema(description = "Массив краткой информации об объявлениях",
            requiredMode = Schema.RequiredMode.REQUIRED)
    List<Ad> results;
}
