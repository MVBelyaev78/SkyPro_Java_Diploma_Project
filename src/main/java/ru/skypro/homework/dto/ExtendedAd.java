package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Расширенная информация об объявлении
 * Применяется в API для управления объявлениями
 */
@Data
@AllArgsConstructor
@Schema(description = "Расширенная информация об объявлении. Применяется в API для управления объявлениями")
public class ExtendedAd {
    /**
     * Уникальный идентификатор объявления
     */
    @Schema(hidden = true)
    private Long pk;

    /**
     * Имя автора объявления
     */
    @Schema(description = "Имя автора объявления",
            example = "Иван",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String authorFirstName;

    /**
     * Фамилия автора объявления
     */
    @Schema(description = "Фамилия автора объявления",
            example = "Иванов",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String authorLastName;

    /**
     * Описание товара
     */
    @Schema(description = "Описание товара",
            example = "Книга \"Грокаем алгоритмы\". Состояние хорошее, 1 экземпляр",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    /**
     * Электронная почта автора объявления
     */
    @Schema(description = "Электронная почта автора объявления",
            example = "123@dummy789.ru",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    /**
     * Наименование изображения для объявления
     */
    @Schema(description = "Наименование изображения для объявления",
            example = "Портрет крупным планом",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String image;

    /**
     * Телефон автора объявления
     */
    @Schema(description = "Телефон автора объявления",
            example = "+78205064779",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;

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
