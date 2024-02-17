package com.upwork.expense_tracker.service;

import com.upwork.expense_tracker.entity.User;
import com.upwork.expense_tracker.exception.EntityNotFoundException;
import com.upwork.expense_tracker.mapper.UserMapper;
import com.upwork.expense_tracker.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * {@code UserDetailsServiceImpl} is a service class that implements {@code UserDetailsService} and has only one
 * method {@link #loadUserByUsername(String)}. The implementation of the interface is required by the Spring Security
 * Framework and used during the authentication process.
 * */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepository repository;
    UserMapper mapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "email", username));
        return mapper.toUserDetails(user);
    }

}
