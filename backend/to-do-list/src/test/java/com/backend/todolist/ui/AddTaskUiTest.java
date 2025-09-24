package com.backend.todolist.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddTaskUiTest extends BaseUiTest {

    // Helper method to log in, copied from LoginUiTest for simplicity
    private void performLogin(WebDriverWait wait) {
        driver.navigate().to(baseUrl + "/signin");
        wait.until(ExpectedConditions.urlContains("/signin"));

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter your username']")));
        usernameField.clear();
        usernameField.sendKeys(defaultUsername);

        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter your password']")));
        passwordField.clear();
        passwordField.sendKeys(defaultPassword);

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(normalize-space(), 'Sign In')]")));
        loginButton.click();

        // Wait for dashboard to load
        wait.until(ExpectedConditions.urlToBe(baseUrl + "/"));
    }

    @Test
    @DisplayName("Add Task: successfully creates a new task without a date")
    void testAddTask() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // 1. Log in first
        performLogin(wait);

        // 2. Click the 'Add New' link
        WebElement addNewButton = waitForAddTaskButton(wait);
        addNewButton.click();

        // 3. Wait for the add task page to load
        wait.until(ExpectedConditions.urlContains("/add"));

        // 4. Fill in the form (skipping the optional date field)
        WebElement titleField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("todoTitle")));
        titleField.clear();
        titleField.sendKeys("My New Automated Task (No Date)");

        // 5. Submit the form
        WebElement createTodoButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(normalize-space(), 'Create Todo')]")));
        createTodoButton.click();

        // 6. Verify success message
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'alert-success') and contains(., 'Todo successfully created')]")));
        assertTrue(successMessage.isDisplayed(), "Success message 'Todo successfully created' should be displayed.");
    }
}
