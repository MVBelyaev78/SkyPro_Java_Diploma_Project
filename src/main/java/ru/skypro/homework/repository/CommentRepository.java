package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.CommentEntity;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
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
    @Modifying
    @Query(value = "DELETE FROM public.tbl_comment c WHERE c.id_advertisement = ?2 AND c.id_comment = ?1",
            nativeQuery = true)
    void deleteByCommentIdAndAdvertisementId(Long commentId, Long idAdvertisement);
}