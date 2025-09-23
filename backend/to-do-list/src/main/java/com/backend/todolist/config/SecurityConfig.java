package com.backend.todolist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import com.backend.todolist.auth.jwt.JwtConfigurer;
import com.backend.todolist.auth.jwt.JwtTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configures application security, including the security filter chain and CORS.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;

    /**
     * Defines the security filter chain for the application.
     * This is the primary way to configure security rules, including CORS.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                // Swagger and API docs
                .antMatchers(
                    "/v2/api-docs",
                    "/swagger-resources/**",
                    "/swagger-ui/**",
                    "/webjars/**"
                ).permitAll()
                // Auth endpoints
                .antMatchers(
                    "/api/auth/signin",
                    "/api/auth/signup"
                ).permitAll()
                // H2 console (dev only)
                .antMatchers("/h2-console/**").permitAll()
                // Everything else requires auth
                .anyRequest().authenticated()
            )
            // Allow H2 console frames
            .headers(headers -> headers.frameOptions().sameOrigin());

        // Apply JWT configuration
        http.apply(new JwtConfigurer(jwtTokenGenerator));
        return http.build();
    }

    // Expose AuthenticationManager for injection (e.g., in controllers/services)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Password encoder bean (replaces legacy bean in WebSecurityConfiguration)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(false);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}