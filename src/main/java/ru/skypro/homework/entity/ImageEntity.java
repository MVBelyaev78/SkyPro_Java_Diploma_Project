package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_image", schema = "public")
public class ImageEntity {
    @Id
    @GeneratedValue
    @Column(name = "id_image")
    private Long id;

    @Column(name ="nm_image")
    private String name;

    @Column(name ="nm_file_path")
    private String filePath;

    @Column(name = "nn_file_size")
    private long fileSize;

    @Column(name = "nm_media_type")
    private String mediaType;

    @Lob
    @JsonIgnore
    @Column(name = "vl_data")
    private byte[] data;
}
