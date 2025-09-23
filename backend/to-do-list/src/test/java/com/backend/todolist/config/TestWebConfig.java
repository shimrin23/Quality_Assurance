package com.backend.todolist.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Provides a test-specific CORS configuration.
 * This ensures that tests run in a controlled environment without relying on the main application's CORS setup.
 */
@TestConfiguration
public class TestWebConfig {

    /**
     * Overrides the main CORS configuration with a test-specific one.
     * The @Primary annotation ensures this bean is chosen over the production one during tests.
     */
    @Bean
    @Primary
    @SuppressWarnings("unused")
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}