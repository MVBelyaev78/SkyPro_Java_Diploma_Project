package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "id_advertisement")
    private Long id;

    @Column(name = "nm_title")
    private String title;

    @Column(name = "nm_description")
    private String description;

    @Column(name = "nn_price")
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "id_image")
    @JsonIgnore
    private ImageEntity image;

    public Optional<ImageEntity> getImage() {
        return Optional.ofNullable(image);
    }
}
