package com.upwork.expense_tracker.service;

import com.upwork.expense_tracker.utils.HttpRequestHeaders;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

/**
 * The service class that describes the methods for quick work with request instances,
 * for example, parsing JWT subject.
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestExtractor {


    JwtService jwtService;


    public String extractJwtSubject(HttpServletRequest request) {
        String jwt = new HttpRequestHeaders(request).getJwt();
        return jwtService.getSubject(jwt);
    }

}
