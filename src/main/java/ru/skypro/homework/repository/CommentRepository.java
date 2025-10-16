package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.CommentEntity;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    /**
     * Находит все комментарии по ID объявления
     *
     * @param idAdvertisement ID объявления
     * @return список комментариев
     */
    List<CommentEntity> findAllByIdAdvertisement_Id(Long idAdvertisement);

    /**
     * Удаляет комментарий по ID комментария и ID объявления
     *
     * @param commentId       ID комментария
     * @param idAdvertisement ID объявления
     */
    void deleteByIdCommentAndIdAdvertisement_Id(int commentId, Long idAdvertisement);
}