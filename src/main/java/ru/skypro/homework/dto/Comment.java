package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO для отображения комментариев
 *
 * @author Maxim
 * @version 1.0
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    /**
     * Идентификатор автора комментария
     */
    @Schema(description = "id автора комментария")
    private int author;

    /**
     * Ссылка на аватарку автора
     */
    @Schema(description = "ссылка на аватар автора комментария")
    private String authorImage;

    /**
     * Имя автора
     */
    @Schema(description = "имя создателя комментария")
    private String authorFirstName;

    /**
     * Время создания комментария
     */
    @Schema(description = "дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970")
    private int createAt;

    /**
     * Идентификатор комментария
     */
    @Schema(description = "id комментария")
    private int pk;

    /**
     * Текст комментария
     */
    @Schema(description = "текст комментария")
    private String text;

}


