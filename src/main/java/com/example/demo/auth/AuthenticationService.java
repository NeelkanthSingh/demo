package com.example.demo.auth;

import com.example.demo.service.JwtService;
import com.example.demo.model.request.RegisterRequest;
import com.example.demo.util.Role;
import com.example.demo.entity.User;
import com.example.demo.model.request.AuthenticationRequest;
import com.example.demo.model.response.AuthenticationResponse;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


// Service class to save the user details
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Method to save the user details
    public AuthenticationResponse register(RegisterRequest request){

        var user = User.builder().lastName(request.getLastname()).firstName(request.getFirstname()).email(request.getEmail()).role(Role.USER).password(passwordEncoder.encode(request.getPassword())).build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
