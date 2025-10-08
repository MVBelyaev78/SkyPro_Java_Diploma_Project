package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ad {
    private Long pk;
    private String author;
    private String image;
    private Integer price;
    private String title;
}
