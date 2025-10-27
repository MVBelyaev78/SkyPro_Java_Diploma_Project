package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentService;

import javax.validation.Valid;

/**
 * Контроллер для работы с комментариями к объявлениям.
 */
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@Tag(name = "Комментарии")
public class CommentController {
    private final CommentService service;

    /**
     * Добавление комментария к объявлению.
     *
     * @param id идентификатор объявления, к которому добавляется комментарий
     * @param comment данные комментария для добавления
     * @return созданный комментарий
     */
    @Operation(summary = "Добавление комментария к объявлению")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "401", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @PostMapping("/{id}/comment")
    public ResponseEntity<Comment> addComment(@PathVariable("id") Long id, @RequestBody @Valid CreateOrUpdateComment comment) {
        return ResponseEntity.ok(service.addComment(id, comment)
                .orElseThrow(IllegalArgumentException::new));
    }

    /**
     * Получение комментариев для указанного объявления.
     *
     * @param id идентификатор объявления, для которого нужно получить комментарии
     * @return список комментариев
     */
    @Operation(summary = "Получение комментариев объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Comments.class))),
            @ApiResponse(responseCode = "401", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @GetMapping("/{id}/comments")
    public ResponseEntity<Comments> getComments(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getComments(id));
    }

    /**
     * Обновление существующего комментария.
     *
     * @param adId идентификатор объявления, к которому относится комментарий
     * @param commentId идентификатор обновляемого комментария
     * @param comment данные для обновления комментария
     * @return обновленный комментарий
     */
    @Operation(summary = "Обновление комментария")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "401", content = @Content()),
            @ApiResponse(responseCode = "403", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable("adId") Long adId,
            @PathVariable("commentId") Long commentId,
            @RequestBody CreateOrUpdateComment comment
    ) {
        return ResponseEntity.ok(service.updateComment(adId, commentId, comment));
    }

    /**
     * Удаление комментария.
     *
     * @param id идентификатор объявления, к которому относится комментарий
     * @param commentId идентификатор удаляемого комментария
     * @return статус операции удаления
     */
    @Operation(summary = "Удаление комментария")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content()),
            @ApiResponse(responseCode = "401", content = @Content()),
            @ApiResponse(responseCode = "403", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> rmComment(@PathVariable("adId") Long id, @PathVariable("commentId") Long commentId) {
        service.rmComment(id, commentId);
        return ResponseEntity.ok().build();
    }
}
