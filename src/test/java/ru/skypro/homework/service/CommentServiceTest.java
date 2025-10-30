package ru.skypro.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    public void testAddCommentUserDateTime_withRelevantArguments_returnsAddedDto() {
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

        final String userEmail = "ustryalov@mail.ru";
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(2L);
        userEntity.setEmail(userEmail);
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
        when(userRepository.findByEmail(userEmail)).thenReturn(userEntity);
        when(mapping.toEntity(createComment, advertisementEntity, userEntity, currentDatetime))
                .thenReturn(Optional.of(commentEntity));
        when(repository.save(commentEntity)).thenReturn(commentEntity);
        when(mapping.fromEntity(commentEntity)).thenReturn(comment);

        // Then
        assertEquals(Optional.of(comment),
                service.addCommentUserDateTime(advertisementId, createComment, userEmail, currentDatetime));
        verify(advertisementRepository, times(1)).findById(advertisementId);
        verifyNoMoreInteractions(advertisementRepository);
        verify(userRepository, times(1)).findByEmail(userEmail);
        verifyNoMoreInteractions(userRepository);
        verify(mapping, times(1))
                .toEntity(createComment, advertisementEntity, userEntity, currentDatetime);
        verify(mapping, times(1)).fromEntity(commentEntity);
        verifyNoMoreInteractions(mapping);
        verify(repository, times(1)).save(commentEntity);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void testAddCommentUserDateTime_withIrrelevantAdvertisementId_returnsEmptyDto() {
        // Given
        final Long advertisementId = 1L;
        final String userEmail = "ustryalov@mail.ru";
        // When
        when(advertisementRepository.findById(eq(advertisementId))).thenReturn(Optional.empty());
        // Then
        assertEquals(Optional.empty(),
                service.addCommentUserDateTime(
                        advertisementId,
                        new CreateOrUpdateComment("text"),
                        userEmail,
                        ZonedDateTime.now()));
        verify(advertisementRepository, times(1)).findById(advertisementId);
        verifyNoMoreInteractions(advertisementRepository);
        verify(userRepository, atMost(1)).findByEmail(userEmail);
        verifyNoMoreInteractions(userRepository);
        verify(mapping, never()).toEntity(
                any(CreateOrUpdateComment.class),
                any(AdvertisementEntity.class),
                any(UserEntity.class),
                any(ZonedDateTime.class));
        verify(mapping, never()).fromEntity(any(CommentEntity.class));
        verifyNoMoreInteractions(mapping);
        verify(repository, never()).save(any(CommentEntity.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void testAddCommentUserDateTime_withNullableAdvertisementId_returnsEmptyDto() {
        // Given
        final String userEmail = "ustryalov@mail.ru";
        // When
        when(advertisementRepository.findById(eq(null))).thenReturn(Optional.empty());
        // Then
        assertEquals(Optional.empty(),
                service.addCommentUserDateTime(
                        null,
                        new CreateOrUpdateComment("text"),
                        userEmail,
                        ZonedDateTime.now()));
        verify(advertisementRepository, times(1)).findById(null);
        verifyNoMoreInteractions(advertisementRepository);
        verify(userRepository, atMost(1)).findByEmail(userEmail);
        verifyNoMoreInteractions(userRepository);
        verify(mapping, never()).toEntity(
                any(CreateOrUpdateComment.class),
                any(AdvertisementEntity.class),
                any(UserEntity.class),
                any(ZonedDateTime.class));
        verify(mapping, never()).fromEntity(any(CommentEntity.class));
        verifyNoMoreInteractions(mapping);
        verify(repository, never()).save(any(CommentEntity.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void testAddCommentUserDateTime_withNullableUserEmail_returnsEmptyDto() {
        // Given
        // When
        // Then
        assertEquals(Optional.empty(),
                service.addCommentUserDateTime(
                        1L,
                        new CreateOrUpdateComment("text"),
                        null,
                        ZonedDateTime.now()));
    }

    @Test
    public void testAddCommentUserDateTime_withNullableCreatedComment_returnsEmptyDto() {
        // Given
        // When
        // Then
        assertEquals(Optional.empty(),
                service.addCommentUserDateTime(
                        1L,
                        null,
                        "ustryalov@mail.ru",
                        ZonedDateTime.now()));
    }

    @Test
    public void testAddCommentUserDateTime_withNullableDateTime_returnsEmptyDto() {
        // Given
        // When
        // Then
        assertEquals(Optional.empty(),
                service.addCommentUserDateTime(
                        1L,
                        new CreateOrUpdateComment("text"),
                        "ustryalov@mail.ru",
                        null));
    }

    @Test
    public void testUpdateCommentUserDateTime_withRelevantArguments_returnsUpdatedDto() {
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

        final Long entityId = 1L;

        final CommentEntity oldEntity = new CommentEntity();
        oldEntity.setIdComment(entityId);
        oldEntity.setNmText("Старый какой-то у вас глобус");
        oldEntity.setDtCreate(ZonedDateTime.of(2024, 1, 10, 15, 45, 56, 666_000_000,
                ZoneId.of("Europe/Moscow")));
        oldEntity.setIdAdvertisement(advertisementEntity);
        oldEntity.setIdAuthor(userEntity);

        final CommentEntity newEntity = new CommentEntity();
        newEntity.setIdComment(entityId);
        newEntity.setNmText("Нет, глобус вполне себе");
        newEntity.setDtCreate(ZonedDateTime.of(2025, 10, 23, 8, 3, 23, 13_000_000,
                ZoneId.of("Europe/Moscow")));
        newEntity.setIdAdvertisement(advertisementEntity);
        newEntity.setIdAuthor(userEntity);

        final CommentEntity savedEntity = new CommentEntity();
        savedEntity.setIdComment(newEntity.getIdComment());
        savedEntity.setNmText(newEntity.getNmText());
        savedEntity.setDtCreate(newEntity.getDtCreate());
        savedEntity.setIdAdvertisement(newEntity.getIdAdvertisement());
        savedEntity.setIdAuthor(newEntity.getIdAuthor());

        final Comment newComment = new Comment(
                2L,
                "",
                "Алексей",
                1_761_195_803_013L,
                1L,
                "Нет, глобус вполне себе");

        final CreateOrUpdateComment newCreateComment = new CreateOrUpdateComment("Нет, глобус вполне себе");

        // When
        when(repository.findById(entityId)).thenReturn(Optional.of(oldEntity));
        when(advertisementRepository.findById(advertisementId)).thenReturn(Optional.of(advertisementEntity));
        when(userRepository.findByEmail(userEntity.getEmail())).thenReturn(userEntity);
        when(mapping.toEntity(newCreateComment, advertisementEntity, userEntity, newEntity.getDtCreate()))
                .thenReturn(Optional.of(newEntity));
        when(repository.save(newEntity)).thenReturn(savedEntity);
        when(mapping.fromEntity(savedEntity)).thenReturn(newComment);

        // Then
        assertEquals(Optional.of(newComment), service.updateCommentUserDateTime(
                advertisementId,
                entityId,
                newCreateComment,
                userEntity.getEmail(),
                newEntity.getDtCreate()));
        verify(repository, times(1)).findById(entityId);
        verify(repository, times(1)).save(newEntity);
        verifyNoMoreInteractions(repository);
        verify(advertisementRepository, times(1)).findById(advertisementId);
        verifyNoMoreInteractions(advertisementRepository);
        verify(userRepository, times(1)).findByEmail(userEntity.getEmail());
        verifyNoMoreInteractions(userRepository);
        verify(mapping, times(1))
                .toEntity(newCreateComment, advertisementEntity, userEntity, newEntity.getDtCreate());
        verify(mapping, times(1)).fromEntity(savedEntity);
        verifyNoMoreInteractions(mapping);
    }
}

