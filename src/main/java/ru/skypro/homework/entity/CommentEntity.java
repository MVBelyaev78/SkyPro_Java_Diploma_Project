package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Сущность комментариев в базе данных
 *
 * @author Maxim
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntity {
    /**
     * Идентификатор автора комментария
     */
    private int author;

    /**
     * Ссылка на аватарку автора
     */
    private String authorImage;

    /**
     * Имя автора
     */
    private String authorFirstName;

    /**
     * Время создания комментария
     */
    private int createAt;

    /**
     * Идентификатор комментария
     */
    private int pk;

    /**
     * Текст комментария
     */
    private String text;
}
