package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Сервис для работы с изображениями.
 * Обеспечивает сохранение и чтение файлов изображений.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository repository;
    @Value("${app.upload.dir}")
    private String uploadDir;
    /**
     * Сохраняет изображение в файловой системе.
     *
     * @param image файл изображения для сохранения
     * @return путь к сохраненному изображению
     * @throws IOException если произошла ошибка при сохранении файла
     */
    public ImageEntity saveImage(MultipartFile image) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = image.getOriginalFilename();
        String fileExtension = originalFileName != null ?
                originalFileName.substring(originalFileName.lastIndexOf(".")) : ".jpg";
        String fileName = UUID.randomUUID() + fileExtension;

        Path filePath = uploadPath.resolve(fileName);
        try (InputStream inputStream = image.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        String contentType = image.getContentType();

        ImageEntity entity = new ImageEntity();
        entity.setName(originalFileName);
        entity.setMediaType(contentType);
        entity.setFilePath("/"+filePath.toString().replace("\\", "/"));
        entity.setFileSize(image.getSize());
        entity.setData(image.getBytes());

        log.info("Изображение сохранено: {}", entity.getFilePath());
        return repository.save(entity);
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
