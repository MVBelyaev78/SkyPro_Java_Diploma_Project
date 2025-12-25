package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Table(name = "tbl_advertisement", schema = "public")
public class AdvertisementEntity {
    @Id
    @GeneratedValue
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "id_advertisement")
    private Long id;

    @ToString.Include
    @EqualsAndHashCode.Exclude
    @Column(name = "nm_title")
    private String title;

    @ToString.Include
    @EqualsAndHashCode.Exclude
    @Column(name = "nm_description")
    private String description;

    @ToString.Include
    @EqualsAndHashCode.Exclude
    @Column(name = "nn_price")
    private Integer price;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private UserEntity user;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "id_image")
    @JsonIgnore
    private ImageEntity image;

    public Optional<ImageEntity> getImage() {
        return Optional.ofNullable(image);
    }
}
