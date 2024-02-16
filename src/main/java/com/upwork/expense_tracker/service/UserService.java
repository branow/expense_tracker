package com.upwork.expense_tracker.service;

import com.upwork.expense_tracker.dto.ProfileUpdatingRequest;
import com.upwork.expense_tracker.dto.UserResponse;
import com.upwork.expense_tracker.entity.User;
import com.upwork.expense_tracker.exception.EntityNotFoundException;
import com.upwork.expense_tracker.mapper.UserMapper;
import com.upwork.expense_tracker.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper mapper;

    public UserResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "email", email));
        return mapper.toUserResponse(user);
    }

    public UserResponse updateProfile(String userEmail, ProfileUpdatingRequest profileDto) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "email", userEmail));
        user.setProfile(profileDto.getProfile());
        return mapper.toUserResponse(userRepository.save(user));
    }

}
