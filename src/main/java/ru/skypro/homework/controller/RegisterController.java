package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.service.RegisterService;

/**
 * Контроллер регистрации пользователей
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Tag(name = "Регистрация")
public class RegisterController {

    private final RegisterService registerService;

    /**
     * Метод для регистрации нового пользователя
     *
     * @param register объект, содержащий данные для регистрации пользователя
     * @return ResponseEntity с кодом состояния 201 (Created) при успешной регистрации или 400 (Bad Request) при ошибке
     */
    @Operation(summary = "Регистрация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = ""))
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Register register) {
        if (registerService.register(register)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
