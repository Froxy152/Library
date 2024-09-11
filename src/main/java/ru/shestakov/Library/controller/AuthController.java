package ru.shestakov.Library.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shestakov.Library.dto.AuthResponseDto;
import ru.shestakov.Library.dto.UserSignInDto;
import ru.shestakov.Library.entity.User;
import ru.shestakov.Library.service.AuthService;

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
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserSignInDto userSignInDTO){
        String token = authService.login(userSignInDTO);
        return new ResponseEntity<>(new AuthResponseDto(token),HttpStatus.OK);
    }

    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    public void reg(@RequestBody UserSignInDto userSignInDTO){
        authService.register(userSignInDTO);
    }





}
