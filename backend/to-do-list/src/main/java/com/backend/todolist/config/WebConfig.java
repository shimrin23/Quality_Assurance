package com.backend.todolist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configures web-related beans for the main application profile.
 * <p>
 * This class is annotated with {@code @Profile("!test")} to ensure it is not loaded
 * during test runs, allowing a separate {@code TestWebConfig} to be used instead.
 * <p>
 * The IDE may flag this class and its {@code @Bean} methods as "unused". This is expected,
 * as they are part of Spring's inversion of control (IoC) container and are wired up
 * automatically at runtime.
 */
@Configuration
@Profile("!test") // Do not load this configuration during tests
public class WebConfig {

    /**
     * Defines the global CORS (Cross-Origin Resource Sharing) configuration.
     * <p>
     * This {@code CorsConfigurationSource} bean is automatically detected and applied by Spring Security,
     * thanks to the {@code .cors()} configuration in {@code SecurityConfig}.
     * @return A {@code CorsConfigurationSource} instance for the entire application (/**).
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}