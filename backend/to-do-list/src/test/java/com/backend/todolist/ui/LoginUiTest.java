package com.backend.todolist.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Selenium UI test that verifies the Login flow.
 * It tries multiple sensible locators so it can adapt to minor UI differences.
 */
public class LoginUiTest extends BaseUiTest {

    private WebElement findUsernameField(WebDriverWait wait) {
        // Try common username/email field locators
        By[] candidates = new By[]{
                // Locators based on provided HTML
                By.xpath("//input[@placeholder='Enter your username']"),
                By.cssSelector("input.form-control.form-control-lg[placeholder='Enter your username']"),
                // Fallbacks
                By.id("username"),
                By.name("username"),
                By.cssSelector("input[type='text']")
        };
        for (By by : candidates) {
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            } catch (Exception ignored) {}
        }
        throw new IllegalStateException("Could not locate username/email input on the login page");
    }

    private WebElement findPasswordField(WebDriverWait wait) {
        By[] candidates = new By[]{
                // Locators based on provided HTML
                By.xpath("//input[@placeholder='Enter your password']"),
                By.cssSelector("input[type='password'][placeholder='Enter your password']"),
                // Fallbacks
                By.id("password"),
                By.name("password")
        };
        for (By by : candidates) {
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            } catch (Exception ignored) {}
        }
        throw new IllegalStateException("Could not locate password input on the login page");
    }

    private WebElement findLoginButton(WebDriverWait wait) {
        By[] candidates = new By[]{
                // Locators based on provided HTML
                By.xpath("//button[contains(normalize-space(), 'Sign In')]"),
                By.cssSelector("button.btn.btn-primary.btn-lg"),
                // Fallbacks
                By.cssSelector("button[type='submit']"),
                By.xpath("//button[normalize-space()='Login']")
        };
        for (By by : candidates) {
            try {
                return wait.until(ExpectedConditions.elementToBeClickable(by));
            } catch (Exception ignored) {}
        }
        throw new IllegalStateException("Could not locate login button on the login page");
    }

    @Test
    @DisplayName("Login: successful login shows dashboard")
    void testSuccessfulLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));

        // 1) Navigate to Login page
        String loginUrl = baseUrl + "/signin";
        driver.navigate().to(loginUrl);

        // 2) Fill credentials
        WebElement username = findUsernameField(wait);
        username.clear();
        username.sendKeys(defaultUsername);

        WebElement password = findPasswordField(wait);
        password.clear();
        password.sendKeys(defaultPassword);

        // 3) Submit
        WebElement submit = findLoginButton(wait);
        submit.click();

        // 4) Post-login: verify that the 'Add Task' button is visible, which indicates a successful login.
        WebElement addTaskButton = super.waitForAddTaskButton(wait);
        assertTrue(addTaskButton.isDisplayed());
    }
}
