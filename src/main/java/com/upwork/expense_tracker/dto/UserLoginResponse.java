package com.upwork.expense_tracker.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginResponse {

    String refreshToken;
    Integer id;
    String email;
    String profile;
    String accessToken;
    String tokenType;

}
