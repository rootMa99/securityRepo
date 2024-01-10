package com.securityJava.springsecurity.services;

import com.securityJava.springsecurity.dto.JwtAuthenticationResponse;
import com.securityJava.springsecurity.dto.RefreshTokenRequest;
import com.securityJava.springsecurity.dto.SigninRequest;
import com.securityJava.springsecurity.dto.SignupRequest;
import com.securityJava.springsecurity.entities.User;

public interface AuthenticationService {
    User signUp(SignupRequest signupRequest);

    JwtAuthenticationResponse signIn(SigninRequest signinRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
