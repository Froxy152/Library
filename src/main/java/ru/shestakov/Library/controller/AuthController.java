package ru.shestakov.Library.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shestakov.Library.dto.AuthResponseDTO;
import ru.shestakov.Library.dto.UserSignInDTO;
import ru.shestakov.Library.entity.User;
import ru.shestakov.Library.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final ModelMapper modelMapper;
    @Autowired
    public AuthController(AuthService authService, ModelMapper modelMapper) {
        this.authService = authService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UserSignInDTO userSignInDTO){
        String token = authService.login(UserDTOToUser(userSignInDTO));
        return new ResponseEntity<>(new AuthResponseDTO(token),HttpStatus.OK);
    }

    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    public void reg(@RequestBody UserSignInDTO userSignInDTO){
        authService.register(UserDTOToUser(userSignInDTO));
    }


    User UserDTOToUser(UserSignInDTO userSignInDTO){
       return modelMapper.map(userSignInDTO, User.class);
    }


}
