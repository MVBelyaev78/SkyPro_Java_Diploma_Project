package ru.skypro.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.skypro.homework.component.mapping.CommentMapping;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.AdvertisementEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.AdvertisementRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.CommentServiceImpl;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

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

    @Mock
    private AdvertisementRepository advertisementRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentServiceImpl service;

    @Test
    public void testGetComments_withRelevantId_returnsRelevantDto() {
        // Given
        final UserEntity advertisementUserEntity = new UserEntity();
        advertisementUserEntity.setId(1L);
        advertisementUserEntity.setEmail("wertin@bk.ru");
        advertisementUserEntity.setFirstName("Сергей");
        advertisementUserEntity.setLastName("Петров");
        advertisementUserEntity.setPhone("+79356661300");
        advertisementUserEntity.setRole("USER");
        advertisementUserEntity.setPassword("qw123456");

        final Long advertisementId = 1L;
        final AdvertisementEntity advertisementEntity = new AdvertisementEntity();
        advertisementEntity.setId(advertisementId);
        advertisementEntity.setTitle("Глобус");
        advertisementEntity.setDescription("Школьный глобус с политической картой");
        advertisementEntity.setPrice(30);
        advertisementEntity.setUser(advertisementUserEntity);

        final UserEntity userEntity = new UserEntity();
        userEntity.setId(2L);
        userEntity.setEmail("ustryalov@mail.ru");
        userEntity.setFirstName("Алексей");
        userEntity.setLastName("Устрялов");
        userEntity.setPhone("+79357760102");
        userEntity.setRole("USER");
        userEntity.setPassword("qw123457");

        final CommentEntity entity = new CommentEntity();
        entity.setIdComment(1L);
        entity.setNmText("Старый какой-то у вас глобус");
        entity.setDtCreate(ZonedDateTime.of(2024, 1, 10, 15, 45, 56, 666000,
                ZoneId.of("Europe/Moscow")));
        entity.setIdAdvertisement(advertisementEntity);
        entity.setIdAuthor(userEntity);

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

    @Test
    public void testAddCommentUser_withRelevantArguments_returnsAddedDto() {
        final UserEntity advertisementUserEntity = new UserEntity();
        advertisementUserEntity.setId(1L);
        advertisementUserEntity.setEmail("wertin@bk.ru");
        advertisementUserEntity.setFirstName("Сергей");
        advertisementUserEntity.setLastName("Петров");
        advertisementUserEntity.setPhone("+79356661300");
        advertisementUserEntity.setRole("USER");
        advertisementUserEntity.setPassword("qw123456");

        final Long advertisementId = 1L;
        final AdvertisementEntity advertisementEntity = new AdvertisementEntity();
        advertisementEntity.setId(advertisementId);
        advertisementEntity.setTitle("Глобус");
        advertisementEntity.setDescription("Школьный глобус с политической картой");
        advertisementEntity.setPrice(30);
        advertisementEntity.setUser(advertisementUserEntity);

        final UserEntity userEntity = new UserEntity();
        userEntity.setId(2L);
        userEntity.setEmail("ustryalov@mail.ru");
        userEntity.setFirstName("Алексей");
        userEntity.setLastName("Устрялов");
        userEntity.setPhone("+79357760102");
        userEntity.setRole("USER");
        userEntity.setPassword("qw123457");

        final CreateOrUpdateComment createComment = new CreateOrUpdateComment("Старый какой-то у вас глобус");

        final ZonedDateTime currentDatetime = ZonedDateTime.now();
        final CommentEntity commentEntity = new CommentEntity();
        commentEntity.setNmText("Старый какой-то у вас глобус");
        commentEntity.setDtCreate(currentDatetime);
        commentEntity.setIdAdvertisement(advertisementEntity);
        commentEntity.setIdAuthor(userEntity);

        final Comment comment = new Comment(
                1L,
                "",
                "Алексей",
                commentEntity.getDtCreateAsMillis(),
                commentEntity.getIdComment(),
                "Старый какой-то у вас глобус");

        // When
        when(advertisementRepository.findById(advertisementId)).thenReturn(Optional.of(advertisementEntity));
        when(mapping.toEntity(createComment, advertisementEntity, userEntity, currentDatetime))
                .thenReturn(Optional.of(commentEntity));
        when(repository.save(commentEntity)).thenReturn(commentEntity);
        when(mapping.fromEntity(commentEntity)).thenReturn(comment);

        // Then
        assertEquals(comment, service.addCommentUser(advertisementId, createComment, userEntity));
    }
}
