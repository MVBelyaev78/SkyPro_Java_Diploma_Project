package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Краткая информация об объявлении
 * Применяется в API для управления объявлениями
 */
@Data
@AllArgsConstructor
@Schema(description = "Краткая информация об объявлении. Применяется в API для управления объявлениями")
public class Ad {
    /**
     * Уникальный идентификатор объявления
     */
    @Schema(hidden = true)
    private Long pk;

    /**
     * Автор объявления
     */
    @Schema(description = "Автор объявления",
            example = "Иванов Петр Алексеевич",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String author;

    /**
     * Наименование изображения для объявления
     */
    @Schema(description = "Наименование изображения для объявления",
            example = "Портрет крупным планом",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String image;

    /**
     * Цена
     */
    @Schema(description = "Цена",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer price;

    /**
     * Краткое описание товара
     */
    @Schema(description = "Описание товара",
            example = "Книга \"Грокаем алгоритмы\"",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
}
