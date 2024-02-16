package com.upwork.expense_tracker.mapper;

import com.upwork.expense_tracker.dto.UserCreatingRequest;
import com.upwork.expense_tracker.dto.UserDetailsImpl;
import com.upwork.expense_tracker.dto.UserResponse;
import com.upwork.expense_tracker.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserMapper {

    public User toUser(UserCreatingRequest dto) {
        return User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .profile(dto.getProfile())
                .build();
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .profile(user.getProfile())
                .build();
    }

    public UserDetails toUserDetails(User user) {
        return UserDetailsImpl.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }

}
