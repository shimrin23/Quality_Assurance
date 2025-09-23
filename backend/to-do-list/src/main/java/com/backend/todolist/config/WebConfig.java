package com.backend.todolist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 

/**
 * Configures web-related beans for the main application profile.
 * <p>
 * This class is annotated with {@code @Profile("!test")} to ensure it is not loaded
 * during test runs, allowing a separate {@code TestWebConfig} to be used instead.
 */
@Configuration
@EnableWebMvc
@Profile("!test") // Do not load this configuration during tests
public class WebConfig implements WebMvcConfigurer {

    // CORS is configured in SecurityConfig.corsConfigurationSource().
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Swagger UI resources
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
        
        // WebJars for Swagger UI
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        
        // Swagger UI
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Redirect root to Swagger UI
        registry.addRedirectViewController("/", "/swagger-ui/");
        
        // Redirect to Swagger UI
        registry.addRedirectViewController("/swagger-ui", "/swagger-ui/");
        registry.addRedirectViewController("/swagger-ui/", "/swagger-ui/index.html");
        
        // Swagger UI v2
        registry.addRedirectViewController("/v2/api-docs", "/v2/api-docs");
        registry.addRedirectViewController(
            "/swagger-resources/configuration/ui", 
            "/swagger-resources/configuration/ui");
        registry.addRedirectViewController(
            "/swagger-resources/configuration/security", 
            "/swagger-resources/configuration/security");
        registry.addRedirectViewController(
            "/swagger-resources", 
            "/swagger-resources");
    }
}
