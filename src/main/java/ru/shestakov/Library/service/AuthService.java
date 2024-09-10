package ru.shestakov.Library.service;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.shestakov.Library.entity.Role;
import ru.shestakov.Library.entity.User;
import ru.shestakov.Library.repo.RoleRepository;
import ru.shestakov.Library.repo.UserRepository;
import ru.shestakov.Library.security.JWTGenerator;
import ru.shestakov.Library.util.BookAllReadyExistsException;


import java.util.Collections;

@Service
public class AuthService {
    private final UserRepository userRepository;
 private final  AuthenticationManager authenticationManager;
 private  final PasswordEncoder passwordEncoder;
 private final RoleRepository roleRepository;
 private final JWTGenerator jwtGenerator;

    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RoleRepository roleRepository, JWTGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtGenerator = jwtGenerator;
    }

    public String login(User user){
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtGenerator.generateToken(authentication);
    }
    public void register(User user){
        if(userRepository.existsByUsername(user.getUsername())){
            throw new BookAllReadyExistsException();
        }
        User u = new User();
        u.setUsername(user.getUsername());
        u.setPassword( passwordEncoder.encode(user.getPassword()));
        Role roles = roleRepository.findByRole("USER").get();
        u.setRoles(Collections.singletonList(roles));
        userRepository.save(u);
    }
}
