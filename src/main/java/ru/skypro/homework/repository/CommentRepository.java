package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.AdvertisementEntity;
import ru.skypro.homework.entity.CommentEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    /**
     * Поиск комментариев по ID объявления
     *
     * @param idAdvertisement ID объявления
     * @return список комментариев
     */
    List<CommentEntity> findAllByIdAdvertisement_Id(Long idAdvertisement);

    /**
     * Удаление комментария по ID комментария и ID объявления
     *
     * @param commentId       ID комментария
     * @param idAdvertisement ID объявления
     */
    void deleteByIdCommentAndIdAdvertisement_Id(Long commentId, Long idAdvertisement);
}