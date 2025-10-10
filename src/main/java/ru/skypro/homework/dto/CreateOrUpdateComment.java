package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.skypro.homework.controller.CommentController;

/**
 * DTO для создания и обновления комментария
 *
 * @see CommentController#addComment(int, CreateOrUpdateComment)
 * @see CommentController#updateComment(int, int, CreateOrUpdateComment)
 */
@Data
public class CreateOrUpdateComment {

    /**
     * Текст комментария
     */
    @Schema(description = "текст комментария", minLength = 8, maxLength = 64)
    private String text;
}
