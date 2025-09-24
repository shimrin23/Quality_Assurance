package com.backend.todolist.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Selenium UI test that verifies the Login flow.
 * It tries multiple sensible locators so it can adapt to minor UI differences.
 */
public class LoginUiTest extends BaseUiTest {

    private WebElement findUsernameField(WebDriverWait wait) {
        // Try common username/email field locators
        By[] candidates = new By[]{
                // React typical username field
                By.cssSelector("input[type='text']"),
                By.xpath("//input[@placeholder='Username']"),
                // Fallbacks
                By.id("username"),
                By.name("username"),
                By.id("email"),
                By.name("email"),
                By.cssSelector("input[type='email']"),
                By.cssSelector("input[placeholder*='email' i]"),
                By.cssSelector("input[placeholder*='username' i]"),
                // Handle placeholders with typos like: "Enteer  your user name"
                By.cssSelector("input[placeholder*='Enteer' i]"),
                By.cssSelector("input[placeholder*='user name' i]")
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
                // Provided React locators
                By.cssSelector("input[type='password']"),
                By.cssSelector("input.form-control.form-control-lg"),
                By.xpath("//input[@placeholder='Enter your password']"),
                By.cssSelector("input[placeholder='Enter your password']"),
                // Fallbacks
                By.id("password"),
                By.name("password"),
                By.cssSelector("input[placeholder*='password' i]"),
                // Handle placeholders with typos like: "Enteer  your password"
                By.cssSelector("input[placeholder*='Enteer' i]")
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
                // Provided React locators
                By.cssSelector("button.btn.btn-primary.btn-lg"),
                By.xpath("//button[contains(text(), 'Sign In')]")
                ,By.xpath("//button[contains(@class, 'btn-primary')]")
                ,By.cssSelector("button[class*='btn-primary']"),
                // Fallbacks
                By.cssSelector("button[type='submit']"),
                By.cssSelector("input[type='submit']"),
                By.xpath("//button[normalize-space()='Login' or normalize-space()='Sign In']"),
                By.xpath("//input[@type='submit' and (@value='Login' or @value='Sign In')]")
        };
        for (By by : candidates) {
            try {
                return wait.until(ExpectedConditions.elementToBeClickable(by));
            } catch (Exception ignored) {}
        }
        throw new IllegalStateException("Could not locate login button on the login page");
    }

    @Test
    @DisplayName("Login: successful login shows dashboard and allows clicking 'Add Task'")
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

        // 4) Post-login: use the same robust flow as AddTaskUiTest
        // This method first tries to find the Add Task button on the current page;
        // if not found, it navigates to baseUrl (/) and retries.
        super.goToDashboardAndClickAddTask();
    }
}
