package com.upwork.expense_tracker.controller;

import com.upwork.expense_tracker.dto.TokenLoginResponse;
import com.upwork.expense_tracker.dto.UserCreatingRequest;
import com.upwork.expense_tracker.dto.UserLoginRequest;
import com.upwork.expense_tracker.dto.UserResponse;
import com.upwork.expense_tracker.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService service;


    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserCreatingRequest user) {
        return new ResponseEntity<>(service.register(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenLoginResponse> login(@RequestBody @Valid UserLoginRequest user) {
        return ResponseEntity.ok(service.login(user));
    }

}
