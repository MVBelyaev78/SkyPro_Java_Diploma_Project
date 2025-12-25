package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "tbl_user", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "id_user")
    private Long id;

    @ToString.Include
    @EqualsAndHashCode.Exclude
    @Column(name = "nm_email", nullable = false, length = 30)
    private String email;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Column(name = "nm_firstname", nullable = false, length = 20)
    private String firstName;

    @ToString.Include
    @EqualsAndHashCode.Exclude
    @Column(name = "nm_lastname", nullable = false, length = 20)
    private String lastName;

    @ToString.Include
    @EqualsAndHashCode.Exclude
    @Column(name = "nm_phone", nullable = false, length = 20)
    private String phone;

    @ToString.Include
    @EqualsAndHashCode.Exclude
    @Column(name = "nm_role", nullable = false, length = 10)
    private String role;

    @ToString.Include
    @EqualsAndHashCode.Exclude
    @Column(name = "nm_password", nullable = false, length = 100)
    private String password;

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
