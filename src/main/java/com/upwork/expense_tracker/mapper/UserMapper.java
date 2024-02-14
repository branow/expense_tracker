package com.upwork.expense_tracker.mapper;

import com.upwork.expense_tracker.dto.UserCreatingRequest;
import com.upwork.expense_tracker.entity.User;
import org.springframework.stereotype.Service;


@Service
public class UserMapper {

    public User toUser(UserCreatingRequest dto) {
        return User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .profile(dto.getProfile())
                .build();
    }

}
