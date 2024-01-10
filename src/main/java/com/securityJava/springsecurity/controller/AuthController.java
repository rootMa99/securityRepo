package com.securityJava.springsecurity.controller;


import com.securityJava.springsecurity.dto.JwtAuthenticationResponse;
import com.securityJava.springsecurity.dto.RefreshTokenRequest;
import com.securityJava.springsecurity.dto.SigninRequest;
import com.securityJava.springsecurity.dto.SignupRequest;
import com.securityJava.springsecurity.entities.User;
import com.securityJava.springsecurity.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    public ResponseEntity<User> signUp(@RequestBody SignupRequest signupRequest){
        return ResponseEntity.ok(authenticationService.signUp(signupRequest));
    }
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest signupRequest){
        return ResponseEntity.ok(authenticationService.signIn(signupRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> signinref(@RequestBody RefreshTokenRequest signupRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(signupRequest));
    }
}
