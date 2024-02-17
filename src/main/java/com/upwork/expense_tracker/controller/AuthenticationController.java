package com.upwork.expense_tracker.controller;

import com.upwork.expense_tracker.dto.*;
import com.upwork.expense_tracker.service.AuthenticationService;
import com.upwork.expense_tracker.service.RequestExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService service;
    RequestExtractor extractor;


    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserCreatingRequest user) {
        return new ResponseEntity<>(service.register(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest user) {
        return ResponseEntity.ok(service.login(user));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        return ResponseEntity.ok(service.refreshToken(request));
    }

    @GetMapping("/logout/delete-refresh-token")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String email = extractor.extractJwtSubject(request);
        service.logout(email);
        return ResponseEntity.ok("Logout successful");
    }

}
