package com.upwork.expense_tracker.utils;

import com.upwork.expense_tracker.exception.AuthorizationHeaderParsingException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The class describes the basic methods to work with {@link HttpServletRequest} headers.
 * Particularly, it used to read JWT from the authorization header.
 * */
public class HttpRequestHeaders {

    private final HttpServletRequest request;


    public HttpRequestHeaders(HttpServletRequest request) {
        this.request = request;
    }

    public String getJwt() {
        String bearer = "bearer ";
        String headAuth = getAuthorizationHeader();

        if (headAuth == null)
            throw new AuthorizationHeaderParsingException("Authorization header not found");
        if (!headAuth.startsWith(bearer))
            throw new AuthorizationHeaderParsingException("Authorization header does not start with 'bearer' - " + headAuth);

        return headAuth.replaceFirst(bearer, "");
    }

    public String getAuthorizationHeader() {
        return getHeader("Authorization");
    }

    public List<String> getHeaders(String name) {
        List<String> headers = new ArrayList<>();
        request.getHeaders(name).asIterator().forEachRemaining(headers::add);
        return headers;
    }

    public String getHeader(String name) {
        return request.getHeader(name);
    }

    public List<String> getHeaderNames() {
        List<String> headers = new ArrayList<>();
        request.getHeaderNames().asIterator().forEachRemaining(headers::add);
        return headers;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequestHeaders that = (HttpRequestHeaders) o;
        return Objects.equals(request, that.request);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request);
    }

    @Override
    public String toString() {
        return getHeaderNames().toString();
    }

}
