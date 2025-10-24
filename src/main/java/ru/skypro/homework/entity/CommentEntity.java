package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;


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
    @ToString.Include
    @EqualsAndHashCode.Include
    @GeneratedValue
    @Column(name = "id_comment")
    private Integer idComment;

    /**
     * Текст комментария
     */
    @ToString.Include
    @EqualsAndHashCode.Exclude
    @Column(name = "nm_text", nullable = false, length = 200)
    private String nmText;

    /**
     * Время создания комментария
     */
    @ToString.Include
    @EqualsAndHashCode.Exclude
    @Column(name = "dt_create")
    private Instant dtCreate;

    /**
     * Идентификатор объявления
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "id_advertisement", nullable = false)
    @JsonIgnore
    private AdvertisementEntity idAdvertisement;

    /**
     * Идентификатор автора комментария
     */
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "id_author", nullable = false)
    @JsonIgnore
    private UserEntity idAuthor;

    public Long getDtCreateAsMillis() {
        return dtCreate != null ? dtCreate.toEpochMilli() : 0;
    }
}