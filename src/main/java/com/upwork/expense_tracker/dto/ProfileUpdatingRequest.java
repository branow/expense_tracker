package com.upwork.expense_tracker.dto;

import com.upwork.expense_tracker.validation.Base64Image;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdatingRequest {

    @NotEmpty(message = "The profile is required.")
    @Base64Image(message = "The profile must be a valid base64 image.")
    String profile;

}
