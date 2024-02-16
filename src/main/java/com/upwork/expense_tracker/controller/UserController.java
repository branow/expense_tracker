package com.upwork.expense_tracker.controller;

import com.upwork.expense_tracker.dto.*;
import com.upwork.expense_tracker.service.UserService;
import com.upwork.expense_tracker.service.RequestExtractor;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@OpenAPIDefinition(info = @Info(title = "User Controller", description = "This controller consist of 2 operations: get, update-profile"))
public class UserController {

    UserService service;
    RequestExtractor extractor;

    @Operation(summary = "Get User Info")
    @GetMapping("/get")
    public ResponseEntity<UserResponse> get(HttpServletRequest request) {
        String email = extractor.extractJwtSubject(request);
        return ResponseEntity.ok(service.getByEmail(email));
    }

    @Operation(summary = "Update Profile")
    @PutMapping("/update-profile")
    public ResponseEntity<UserResponse> update(@RequestBody @Valid ProfileUpdatingRequest profile,
                                               HttpServletRequest request) {
        String email = extractor.extractJwtSubject(request);
        return ResponseEntity.ok(service.updateProfile(email, profile));
    }

}
