package com.upwork.expense_tracker.service;

import com.upwork.expense_tracker.dto.*;
import com.upwork.expense_tracker.entity.RefreshToken;
import com.upwork.expense_tracker.entity.User;
import com.upwork.expense_tracker.exception.EntityAlreadyExistsException;
import com.upwork.expense_tracker.exception.EntityNotFoundException;
import com.upwork.expense_tracker.mapper.UserMapper;
import com.upwork.expense_tracker.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository repository;
    UserMapper mapper;
    AuthenticationManager manager;
    JwtService jwtService;
    RefreshTokenService refreshTokenService;
    PasswordEncoder passwordEncoder;


    public UserResponse register(UserCreatingRequest user) {
        if (repository.existsByEmail(user.getEmail()))
            throw new EntityAlreadyExistsException(User.class, "email", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return mapper.toUserResponse(repository.save(mapper.toUser(user)));
    }

    public UserLoginResponse login(UserLoginRequest user) {
        User userEntity = repository.findByEmail(user.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(User.class, "email", user.getEmail()));

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        Authentication auth = manager.authenticate(token);
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String jwt = jwtService.generateJwt(userDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userEntity.getId());
        return mapper.toUserLoginResponse(userEntity, refreshToken, jwt, "Bearer");
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenService.getByToken(refreshTokenRequest.getToken());
        refreshTokenService.verifyExpiration(refreshToken);
        String jwt = jwtService.generateJwt(mapper.toUserDetails(refreshToken.getUser()));
        return RefreshTokenResponse.builder()
                .refreshToken(refreshToken.getToken())
                .accessToken(jwt)
                .tokenType("Bearer")
                .build();
    }

    public void logout(String userEmail) {
        refreshTokenService.deleteByUserId(repository.findIdByEmail(userEmail));
    }

}








