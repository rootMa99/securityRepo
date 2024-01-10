package com.securityJava.springsecurity.services.impl;

import com.securityJava.springsecurity.dto.JwtAuthenticationResponse;
import com.securityJava.springsecurity.dto.RefreshTokenRequest;
import com.securityJava.springsecurity.dto.SigninRequest;
import com.securityJava.springsecurity.dto.SignupRequest;
import com.securityJava.springsecurity.entities.Role;
import com.securityJava.springsecurity.entities.User;
import com.securityJava.springsecurity.repository.UserRepository;
import com.securityJava.springsecurity.services.AuthenticationService;
import com.securityJava.springsecurity.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    @Override
    public User signUp(SignupRequest signupRequest){
        User user= new User();


        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setRole(Role.USER);
        return userRepository.save(user);

    }

    @Override
    public JwtAuthenticationResponse signIn(SigninRequest signinRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),
                signinRequest.getPassword()));
        var user=userRepository.findByEmail(signinRequest.getEmail())
                .orElseThrow(()->new IllegalArgumentException("Invalid Email or Password"));
        var jwt= jwtService.generateToken(user);
        var refreshToken=jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse= new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){

        String email= jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(email).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt =jwtService.generateToken(user);
            JwtAuthenticationResponse jwtAuthenticationResponse= new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}
