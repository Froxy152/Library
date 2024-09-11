package ru.shestakov.Library.service;


import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.shestakov.Library.dto.UserSignInDto;
import ru.shestakov.Library.entity.Role;
import ru.shestakov.Library.entity.User;
import ru.shestakov.Library.repo.RoleRepository;
import ru.shestakov.Library.repo.UserRepository;
import ru.shestakov.Library.security.JWTGenerator;
import ru.shestakov.Library.exceptions.UserAlReadyExistsException;


import java.util.Collections;

@Service
public class AuthService {
    private final UserRepository userRepository;
 private final  AuthenticationManager authenticationManager;
 private  final PasswordEncoder passwordEncoder;
 private final RoleRepository roleRepository;
 private final JWTGenerator jwtGenerator;
 private final ModelMapper modelMapper;

    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RoleRepository roleRepository, JWTGenerator jwtGenerator, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtGenerator = jwtGenerator;
        this.modelMapper = modelMapper;
    }

    public String login(UserSignInDto userSignInDto){
        User user = UserDTOToUser(userSignInDto);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtGenerator.generateToken(authentication);
    }
    public void register(UserSignInDto userSignInDto){
        User user = UserDTOToUser(userSignInDto);
        if(userRepository.existsByUsername(user.getUsername())){
            throw new UserAlReadyExistsException();
        }
        User u = new User();
        u.setUsername(user.getUsername());
        u.setPassword( passwordEncoder.encode(user.getPassword()));
        Role roles = roleRepository.findByRole("USER").get();
        u.setRoles(Collections.singletonList(roles));
        userRepository.save(u);
    }

    User UserDTOToUser(UserSignInDto userSignInDTO){
        return modelMapper.map(userSignInDTO, User.class);
    }
}
