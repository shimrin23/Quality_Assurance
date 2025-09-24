package com.backend.todolist.bdd;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * Configures the Spring Test Context for Cucumber.
 * <p>
 * This class is the bridge between Cucumber and Spring Boot. The @CucumberContextConfiguration
 * annotation tells Cucumber to use this class to bootstrap the Spring application context.
 * The IDE may flag this as "unused", which is expected behavior.
 */
@CucumberContextConfiguration
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@Import(TestWebConfig.class) // Explicitly import test-specific configurations
public class CucumberSpringConfiguration {
    // This class serves as the bridge between Cucumber and Spring Boot
    // No additional code needed - Spring Boot will handle the rest
}