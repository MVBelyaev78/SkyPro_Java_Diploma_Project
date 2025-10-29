package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_image", schema = "public")
public class ImageEntity {
    @Id
    @ToString.Include
    @EqualsAndHashCode.Include
    @GeneratedValue
    @Column(name = "id_image")
    private Long id;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name ="nm_image")
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Column(name ="nm_file_path")
    private String filePath;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Column(name = "nn_file_size")
    private long fileSize;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Column(name = "nm_media_type")
    private String mediaType;

    @Lob
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Column(name = "vl_data")
    private byte[] data;
}
