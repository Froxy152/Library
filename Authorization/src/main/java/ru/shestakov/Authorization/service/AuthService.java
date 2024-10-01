package ru.shestakov.Authorization.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shestakov.Authorization.dto.UserRequestLoginDto;
import ru.shestakov.Authorization.dto.UserRequestRegistrationDto;
import ru.shestakov.Authorization.dto.UserResponseRegistrationDto;
import ru.shestakov.Authorization.entity.Role;
import ru.shestakov.Authorization.entity.User;
import ru.shestakov.Authorization.exceptions.UserAllReadyExistsException;
import ru.shestakov.Authorization.mapper.UserMapper;
import ru.shestakov.Authorization.repository.RoleRepository;
import ru.shestakov.Authorization.repository.UserRepository;
import ru.shestakov.Authorization.security.JWTGenerator;


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
        User user = userMapper.userDTOToUser(userRequestLoginDto);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtGenerator.generateToken(authentication);
    }
    public UserResponseRegistrationDto register(UserRequestRegistrationDto userRequestRegistrationDto){
        if(userRepository.existsByUsername(userRequestRegistrationDto.getUsername())){
            throw new UserAllReadyExistsException();
        }
        User user = new User();
        user.setUsername(userRequestRegistrationDto.getUsername());
        user.setPassword( passwordEncoder.encode(userRequestRegistrationDto.getPassword()));
        Role roles = roleRepository.findByRole("USER").get();
        user.setRoles(Collections.singletonList(roles));
        return userMapper.userToUserRegistration(userRepository.save(user));
    }


}
