package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.ImageEntity;

import java.io.IOException;

public interface ImageService {
    ImageEntity saveImage(MultipartFile image) throws IOException;

    byte[] getImage(String imagePath) throws IOException;
}
