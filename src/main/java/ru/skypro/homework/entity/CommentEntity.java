package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * Сущность комментариев в базе данных
 *
 * @author Maxim
 * @version 1.0
 */


@Entity
@Table(name = "tbl_comment", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_comment")
    private int idComment;

    /**
     * Текст комментария
     */
    @Column(name = "nm_text", nullable = false, length = 200)
    private String nmText;

    /**
     * Время создания комментария
     */
    @Column(name = "dt_create")
    private int dtCreate;

    /**
     * Идентификатор объявления
     */
    @ManyToOne
    @JoinColumn(name = "id_advertisement", nullable = false)
    @JsonIgnore
    private AdvertisementEntity idAdvertisement;

    /**
     * Идентификатор автора комментария
     */
    @ManyToOne
    @JoinColumn(name = "id_author", nullable = false)
    @JsonIgnore
    private UserEntity idAuthor;
}
