package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.CommentEntity;

import java.util.List;

/**
 * Репозиторий для работы с CommentEntity
 *
 * @author Maxim
 * @version 1.0
 */
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    /**
     * Находит все комментарии по ID объявления
     *
     * @param idAdvertisement ID объявления
     * @return список комментариев
     */
    List<CommentEntity> findAllByIdAdvertisement_IdAdvertisement(int idAdvertisement);

    /**
     * Удаляет комментарий по ID комментария и ID объявления
     *
     * @param commentId       ID комментария
     * @param idAdvertisement ID объявления
     */
    void deleteByIdCommentAndIdAdvertisement_IdAdvertisement(int commentId, int idAdvertisement);
}
