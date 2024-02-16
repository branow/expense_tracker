package com.upwork.expense_tracker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Value("${security.cors.origin}")
    private String corsOrigin;


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    //The bean is needed for login endpoint
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(corsOrigin));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            CorsConfigurationSource corsConfigurationSource,
                                            JwtAuthenticationConverter jwtAuthenticationConverter)
            throws Exception {

        //Allow frontend to send requests to the server
        http.cors(cors -> cors.configurationSource(corsConfigurationSource));

        //It should be here otherwise the 403 response will be for any request
        http.csrf(AbstractHttpConfigurer::disable);

        http.oauth2ResourceServer((oauth) -> oauth.jwt((jwt) -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)));

        //Turn off default session creation
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests((auth) -> {
            //Authentication Controller
            auth.requestMatchers(HttpMethod.POST, "/register").permitAll();
            auth.requestMatchers(HttpMethod.POST, "/login").permitAll();
            //User Controller
            auth.requestMatchers(HttpMethod.GET, "/user/get").authenticated();
            auth.requestMatchers(HttpMethod.PUT, "/user/update-profile").authenticated();
            //Transaction Controller
            auth.requestMatchers(HttpMethod.GET, "/transaction/get-all").authenticated();
            auth.requestMatchers(HttpMethod.POST, "/transaction/create").authenticated();
            auth.requestMatchers(HttpMethod.PUT, "/transaction/update").authenticated();
            auth.requestMatchers(HttpMethod.DELETE, "/transaction/delete/*").authenticated();

            auth.anyRequest().permitAll();
        });

        return http.build();
    }

}
