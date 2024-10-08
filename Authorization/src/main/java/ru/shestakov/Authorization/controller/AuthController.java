package ru.shestakov.Authorization.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shestakov.Authorization.dto.AuthResponseDto;
import ru.shestakov.Authorization.dto.UserRequestLoginDto;
import ru.shestakov.Authorization.dto.UserRequestRegistrationDto;
import ru.shestakov.Authorization.service.AuthService;

@Tag(name="Авторизация", description="Происходит регистрация и вход")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @Operation(
            summary = "вход в учетную запись", description = "Позволяет авторизовать пользователя"
    )
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserRequestLoginDto userRequestLoginDTO){
        String token = authService.login(userRequestLoginDTO);
        return new ResponseEntity<>(new AuthResponseDto(token),HttpStatus.OK);
    }
    @Operation(
            summary = "Регистрация пользователя", description = "Позволяет зарегистрировать пользователя"
    )
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registration(@RequestBody UserRequestRegistrationDto userRequestRegistrationDto){
       return new ResponseEntity<>(authService.register(userRequestRegistrationDto),HttpStatus.CREATED);
    }
}
