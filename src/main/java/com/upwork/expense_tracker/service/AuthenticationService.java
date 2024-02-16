package com.upwork.expense_tracker.service;

import com.upwork.expense_tracker.dto.TokenLoginResponse;
import com.upwork.expense_tracker.dto.UserCreatingRequest;
import com.upwork.expense_tracker.dto.UserLoginRequest;
import com.upwork.expense_tracker.dto.UserResponse;
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
    PasswordEncoder passwordEncoder;


    public UserResponse register(UserCreatingRequest user) {
        if (repository.existsByEmail(user.getEmail()))
            throw new EntityAlreadyExistsException(User.class, "email", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return mapper.toUserResponse(repository.save(mapper.toUser(user)));
    }

    public TokenLoginResponse login(UserLoginRequest user) {
        if (!repository.existsByEmail(user.getEmail()))
            throw new EntityNotFoundException(User.class, "email", user.getEmail());

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        Authentication auth = manager.authenticate(token);
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String jwt = jwtService.generateJwt(userDetails);

        return TokenLoginResponse.builder()
                .jwt(jwt)
                .build();
    }

}








