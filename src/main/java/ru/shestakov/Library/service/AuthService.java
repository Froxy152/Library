package ru.shestakov.Library.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.shestakov.Library.dto.UserRequestLoginDto;
import ru.shestakov.Library.dto.UserRequestRegistrationDto;
import ru.shestakov.Library.entity.Role;
import ru.shestakov.Library.entity.User;
import ru.shestakov.Library.mapper.UserMapper;
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
 private final UserMapper userMapper;
    @Autowired
    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RoleRepository roleRepository, JWTGenerator jwtGenerator, ModelMapper modelMapper, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtGenerator = jwtGenerator;
        this.userMapper = userMapper;

    }

    public String login(UserRequestLoginDto userRequestLoginDto){
        User user = userMapper.UserDTOToUser(userRequestLoginDto);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtGenerator.generateToken(authentication);
    }
    public void register(UserRequestRegistrationDto userRequestRegistrationDto){
        User user = userMapper.UserRegistrationDtoToUser(userRequestRegistrationDto);
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


}
