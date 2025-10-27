package ru.skypro.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.component.mapping.CommentMapping;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.entity.AdvertisementEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.impl.CommentServiceImpl;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository repository;

    @Mock
    private CommentMapping mapping;

    @InjectMocks
    private CommentServiceImpl service;

    @Test
    public void testGetComments_withRelevantId_returnsRelevantDto() {
        // Given
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("wertin@bk.ru");
        userEntity.setFirstName("Сергей");
        userEntity.setLastName("Петров");
        userEntity.setPhone("+79356661300");
        userEntity.setRole("USER");
        userEntity.setPassword("qw123456");

        final Long advertisementId = 1L;
        final AdvertisementEntity advertisementEntity = new AdvertisementEntity();
        advertisementEntity.setId(advertisementId);
        advertisementEntity.setTitle("Глобус");
        advertisementEntity.setDescription("Школьный глобус с политической картой");
        advertisementEntity.setPrice(30);
        advertisementEntity.setUser(userEntity);

        final UserEntity authorEntity = new UserEntity();
        authorEntity.setId(2L);
        authorEntity.setEmail("ustryalov@mail.ru");
        authorEntity.setFirstName("Алексей");
        authorEntity.setLastName("Устрялов");
        authorEntity.setPhone("+79357760102");
        authorEntity.setRole("USER");
        authorEntity.setPassword("qw123457");

        final CommentEntity entity = new CommentEntity();
        entity.setIdComment(1L);
        entity.setNmText("Старый какой-то у вас глобус");
        entity.setDtCreate(ZonedDateTime.of(2024, 1, 10, 15, 45, 56, 666000,
                ZoneId.of("Europe/Moscow")));
        entity.setIdAdvertisement(advertisementEntity);
        entity.setIdAuthor(authorEntity);

        final Comment comment = new Comment(
                2L,
                "",
                "Алексей",
                1_704_894_416_666L,
                1L,
                "Старый какой-то у вас глобус");
        final Comments comments = new Comments(1, List.of(comment));

        // When
        when(repository.findAllByIdAdvertisement_Id(advertisementId)).thenReturn(List.of(entity));
        when(mapping.fromEntities(List.of(entity))).thenReturn(comments);

        // Then
        assertEquals(comments, service.getComments(advertisementId));
        verify(repository, times(1)).findAllByIdAdvertisement_Id(advertisementId);
        verifyNoMoreInteractions(repository);
        verify(mapping, times(1)).fromEntities(List.of(entity));
        verifyNoMoreInteractions(mapping);
    }

    @Test
    public void testGetComments_withIrrelevantId_returnsEmptyDto() {
        // Given
        final Long advertisementId = 1L;
        final Comments comments = new Comments(0, List.of());
        // When
        when(repository.findAllByIdAdvertisement_Id(advertisementId)).thenReturn(List.of());
        when(mapping.fromEntities(List.of())).thenReturn(comments);
        // Then
        assertEquals(comments, service.getComments(advertisementId));
        verify(repository, times(1)).findAllByIdAdvertisement_Id(advertisementId);
        verifyNoMoreInteractions(repository);
        verify(mapping, times(1)).fromEntities(List.of());
        verifyNoMoreInteractions(mapping);
    }

    @Test
    public void testGetComments_withNullId_returnsEmptyDto() {
        // Given
        final Comments comments = new Comments(0, List.of());
        // When
        when(repository.findAllByIdAdvertisement_Id(null)).thenReturn(List.of());
        when(mapping.fromEntities(List.of())).thenReturn(comments);
        // Then
        assertEquals(comments, service.getComments(null));
        verify(repository, times(1)).findAllByIdAdvertisement_Id(null);
        verifyNoMoreInteractions(repository);
        verify(mapping, times(1)).fromEntities(List.of());
        verifyNoMoreInteractions(mapping);
    }
}
