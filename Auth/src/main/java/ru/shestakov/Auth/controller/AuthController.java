package ru.shestakov.Auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shestakov.Auth.dto.AuthResponseDto;
import ru.shestakov.Auth.dto.UserRequestLoginDto;
import ru.shestakov.Auth.dto.UserRequestRegistrationDto;
import ru.shestakov.Auth.service.AuthService;

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

    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    public void reg(@RequestBody UserRequestRegistrationDto userRequestRegistrationDto){
        authService.register(userRequestRegistrationDto);
    }





}
