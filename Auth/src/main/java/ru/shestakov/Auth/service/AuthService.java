package ru.shestakov.Auth.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shestakov.Auth.dto.UserRequestLoginDto;
import ru.shestakov.Auth.dto.UserRequestRegistrationDto;
import ru.shestakov.Auth.entity.Role;
import ru.shestakov.Auth.entity.User;
import ru.shestakov.Auth.exceptions.UserAllReadyExistsException;
import ru.shestakov.Auth.mapper.UserMapper;
import ru.shestakov.Auth.repository.RoleRepository;
import ru.shestakov.Auth.repository.UserRepository;
import ru.shestakov.Auth.security.JWTGenerator;


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
            throw new UserAllReadyExistsException();
        }
        User u = new User();
        u.setUsername(user.getUsername());
        u.setPassword( passwordEncoder.encode(user.getPassword()));
        Role roles = roleRepository.findByRole("USER").get();
        u.setRoles(Collections.singletonList(roles));
        userRepository.save(u);
    }


}
