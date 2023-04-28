package com.springJWT.Service;

import com.springJWT.Dto.LoginRequest;
import com.springJWT.Dto.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
    ResponseEntity<?> registerUser(SignupRequest signUpRequest);
}
