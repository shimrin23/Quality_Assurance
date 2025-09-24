package com.backend.todolist.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignUpUiTest extends BaseUiTest {

    private WebElement findUsernameField(WebDriverWait wait) {
        By[] candidates = new By[]{
                By.xpath("//input[@placeholder='Choose a username']"),
                By.cssSelector("input.form-control.form-control-lg[placeholder='Choose a username']")
        };
        for (By by : candidates) {
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            } catch (Exception ignored) {}
        }
        throw new IllegalStateException("Could not locate username input on the signup page");
    }

    private WebElement findPasswordField(WebDriverWait wait) {
        By[] candidates = new By[]{
                By.xpath("//input[@placeholder='Choose a password']"),
                By.cssSelector("input[type='password'][placeholder='Choose a password']")
        };
        for (By by : candidates) {
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            } catch (Exception ignored) {}
        }
        throw new IllegalStateException("Could not locate password input on the signup page");
    }

    private WebElement findSignUpButton(WebDriverWait wait) {
        By[] candidates = new By[]{
                By.xpath("//button[contains(normalize-space(), 'Create Account')]"),
                By.cssSelector("button.btn.btn-primary.btn-lg")
        };
        for (By by : candidates) {
            try {
                return wait.until(ExpectedConditions.elementToBeClickable(by));
            } catch (Exception ignored) {}
        }
        throw new IllegalStateException("Could not locate signup button on the signup page");
    }

    @Test
    @DisplayName("Signup: successful signup automatically logs in and shows dashboard")
    void testSuccessfulSignUp() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));

        String signupUrl = baseUrl + "/signup";
        driver.navigate().to(signupUrl);

        // Use a unique username for each test run to avoid conflicts
        String uniqueUsername = "newuser_" + UUID.randomUUID().toString().substring(0, 8);

        WebElement username = findUsernameField(wait);
        username.clear();
        username.sendKeys(uniqueUsername);

        WebElement password = findPasswordField(wait);
        password.clear();
        password.sendKeys("password");

        WebElement submit = findSignUpButton(wait);
        submit.click();

        // Verify that the user is redirected to the dashboard by looking for the 'Add Task' button.
        WebElement addTaskButton = super.waitForAddTaskButton(wait);
        assertTrue(addTaskButton.isDisplayed(), "After signup, the 'Add Task' button should be visible on the dashboard.");
    }
}
