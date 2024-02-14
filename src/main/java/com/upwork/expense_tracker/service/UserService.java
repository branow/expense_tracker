package com.upwork.expense_tracker.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upwork.expense_tracker.dto.ProfileUpdatingRequest;
import com.upwork.expense_tracker.dto.UserCreatingRequest;
import com.upwork.expense_tracker.dto.UserLoginRequest;
import com.upwork.expense_tracker.exception.EntityAlreadyExistsException;
import com.upwork.expense_tracker.exception.EntityNotFoundException;
import com.upwork.expense_tracker.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upwork.expense_tracker.constant.Messages;
import com.upwork.expense_tracker.entity.LoginUser;
import com.upwork.expense_tracker.entity.User;
import com.upwork.expense_tracker.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenUtility tokenUtility;

    @Autowired
    private UserMapper mapper;

    public List<String> createUser(UserCreatingRequest user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EntityAlreadyExistsException(User.class, "email", user.getEmail());
        }
        userRepository.save(mapper.toUser(user));
        return Arrays.asList(Messages.SUCCESS);
    }

    public List<String> loginUser(UserLoginRequest user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            throw new EntityNotFoundException(User.class, "email", user.getEmail());
        }
        if (!userRepository.existsByEmailAndPassword(user.getEmail(), user.getPassword())) {
            return Arrays.asList(Messages.WRONG_PASSWORD);
        }
        return Arrays.asList(tokenUtility.generateToken(user.getEmail()));
    }

    public List<String> updateProfile(String token, ProfileUpdatingRequest profileDto) {

        if (token == null) {
            return Arrays.asList(Messages.EMPTY_TOKEN);
        }
        if (!tokenUtility.validateToken(token)) {
            return Arrays.asList(Messages.INVALID_TOKEN);
        }
        String userName = tokenUtility.extractUsername(token);

        User user = userRepository.findByEmail(userName);
        user.setProfile(profileDto.getProfile());

        userRepository.save(user);
        return Arrays.asList(Messages.SUCCESS);
    }

    public List<String> signout(String token) {

        tokenUtility.revokeToken(token);
        return Arrays.asList(Messages.SIGNOUT);
    }

    public Map<String, String> getUser(String token) {

        HashMap<String, String> map = new HashMap<>();
        if (token == null) {
            map.put("error", Messages.EMPTY_TOKEN);
            return map;
        }
        if (!tokenUtility.validateToken(token)) {
            map.put("error", Messages.INVALID_TOKEN);
            return map;
        }
        String userName = tokenUtility.extractUsername(token);
        User user= userRepository.findByEmail(userName);

        map.put("user_name", user.getEmail());
        map.put("profile", user.getProfile());
        return map;
    }
}
