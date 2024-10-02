package ru.shestakov.Authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shestakov.Authorization.dto.AuthResponseDto;
import ru.shestakov.Authorization.dto.UserRequestLoginDto;
import ru.shestakov.Authorization.dto.UserRequestRegistrationDto;
import ru.shestakov.Authorization.service.AuthService;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserRequestLoginDto userRequestLoginDTO){
        String token = authService.login(userRequestLoginDTO);
        return new ResponseEntity<>(new AuthResponseDto(token),HttpStatus.OK);
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registration(@RequestBody UserRequestRegistrationDto userRequestRegistrationDto){
       return new ResponseEntity<>(authService.register(userRequestRegistrationDto),HttpStatus.CREATED);
    }
}
