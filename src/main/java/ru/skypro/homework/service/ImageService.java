package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Сервис для работы с изображениями.
 * Обеспечивает сохранение и чтение файлов изображений.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final String uploadDir = "uploads/image/";

    /**
     * Сохраняет изображение в файловой системе.
     *
     * @param image файл изображения для сохранения
     * @return путь к сохраненному изображению
     * @throws IOException если произошла ошибка при сохранении файла
     */
    public String saveImage(MultipartFile image) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = image.getOriginalFilename();
        String fileExtension = originalFileName != null ?
                originalFileName.substring(originalFileName.lastIndexOf(".")) : ".jpg";
        String fileName = UUID.randomUUID() + fileExtension;

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(image.getInputStream(), filePath);

        log.info("Изображение сохранено: {}", filePath);
        return "/images" + fileName;
    }

    /**
     * Читает изображение из файловой системы.
     *
     * @param imagePath путь к изображению
     * @return бинарные данные изображения
     * @throws IOException если файл не найден или произошла ошибка при чтении
     */
    public byte[] getImage(String imagePath) throws IOException {
        String fileName = imagePath.replace("/images/", "");
        Path filePath = Paths.get(uploadDir).resolve(fileName);

        if (Files.exists(filePath)) {
            return Files.readAllBytes(filePath);
        }
        throw new IOException("Изображение не найдено: " + imagePath);
    }
}
