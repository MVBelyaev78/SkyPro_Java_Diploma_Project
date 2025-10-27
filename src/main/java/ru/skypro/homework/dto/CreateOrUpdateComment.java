package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.skypro.homework.controller.CommentController;

/**
 * DTO для создания и обновления комментария
 *
 */
@Data
@AllArgsConstructor
public class CreateOrUpdateComment {

    /**
     * Текст комментария
     */
    @Schema(description = "текст комментария", minLength = 8, maxLength = 64)
    private String text;
}
