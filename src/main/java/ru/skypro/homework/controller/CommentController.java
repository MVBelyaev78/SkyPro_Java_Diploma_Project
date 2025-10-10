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

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@Tag(name = "Комментарии")
public class CommentController {
    private final CommentService service;

    @Operation(summary = "Добавление комментария к объявлению")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "401", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @PostMapping("/{id}/comment")
    public ResponseEntity<Comment> addComment(@PathVariable("id") int id, @RequestBody @Valid CreateOrUpdateComment comment) {
        return ResponseEntity.ok(service.addComment(id, comment));
    }

    @Operation(summary = "Получение комментариев объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Comments.class))),
            @ApiResponse(responseCode = "401", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @GetMapping("/{id}/comments")
    public ResponseEntity<Comments> getComments(@PathVariable("id") int id) {
        return ResponseEntity.ok(service.getComments(id));
    }

    @Operation(summary = "Обновление комментария")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "401", content = @Content()),
            @ApiResponse(responseCode = "403", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable("adId") int adId,
            @PathVariable("commentId") int commentId,
            @RequestBody CreateOrUpdateComment comment
    ) {
        return ResponseEntity.ok(service.updateComment(adId, commentId, comment));
    }

    @Operation(summary = "Удаление комментария")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content()),
            @ApiResponse(responseCode = "401", content = @Content()),
            @ApiResponse(responseCode = "403", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> rmComment(@PathVariable("adId") int id, @PathVariable("commentId") int commentId) {
        service.rmComment(id, commentId);
        return ResponseEntity.ok().build();
    }
}
