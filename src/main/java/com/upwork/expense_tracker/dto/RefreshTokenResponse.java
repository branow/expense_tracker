package com.upwork.expense_tracker.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenResponse {

    String refreshToken;
    String accessToken;
    String tokenType;

}
