package com.backend.todolist.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Selenium UI test that verifies adding a task/item via the UI.
 */
public class AddTaskUiTest extends BaseUiTest {

    private void doLoginIfNeeded(WebDriverWait wait) {
        // If not already authenticated, go to /signin and login with defaults
        driver.navigate().to(baseUrl + "/signin");
        if (driver.getCurrentUrl().contains("/signin")) {
            // Fill username (React typical)
            WebElement user = tryFind(wait,
                    new By[]{
                            By.cssSelector("input[type='text']"),
                            By.xpath("//input[@placeholder='Username']"),
                            // Fallbacks
                            By.id("username"), By.name("username"), By.id("email"), By.name("email"),
                            By.cssSelector("input[type='email']"), By.cssSelector("input[placeholder*='username' i]"),
                            By.cssSelector("input[placeholder*='email' i]")});
            user.clear();
            user.sendKeys(defaultUsername);

            // Fill password
            WebElement pass = tryFind(wait,
                    new By[]{
                            By.cssSelector("input[type='password']"),
                            By.cssSelector("input.form-control.form-control-lg"),
                            By.xpath("//input[@placeholder='Enter your password']"),
                            By.cssSelector("input[placeholder='Enter your password']"),
                            // Fallbacks
                            By.id("password"), By.name("password"), By.cssSelector("input[placeholder*='password' i]")
                    });
            pass.clear();
            pass.sendKeys(defaultPassword);

            // Click submit
            WebElement submit = tryFindClickable(wait,
                    new By[]{
                            By.cssSelector("button.btn.btn-primary.btn-lg"),
                            By.xpath("//button[contains(text(), 'Sign In')]"),
                            By.xpath("//button[contains(@class, 'btn-primary')]"),
                            By.cssSelector("button[class*='btn-primary']"),
                            // Fallbacks
                            By.cssSelector("button[type='submit']"), By.cssSelector("input[type='submit']")
                    });
            submit.click();

            // Wait until we leave /signin
            wait.until(drv -> !drv.getCurrentUrl().contains("/signin"));
        }
    }

    private WebElement tryFind(WebDriverWait wait, By[] candidates) {
        for (By by : candidates) {
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            } catch (Exception ignored) {}
        }
        throw new IllegalStateException("Could not find expected element using provided locators");
    }

    private WebElement tryFindClickable(WebDriverWait wait, By[] candidates) {
        for (By by : candidates) {
            try {
                return wait.until(ExpectedConditions.elementToBeClickable(by));
            } catch (Exception ignored) {}
        }
        throw new IllegalStateException("Could not find clickable element using provided locators");
    }

    @Test
    @DisplayName("Tasks: add a new task and verify it appears in the list or success indicator is shown")
    void testAddTask() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // 1) Ensure we are logged in
        doLoginIfNeeded(wait);

        // 2) From dashboard, click the stable "Add Task" control
        goToDashboardAndClickAddTask();

        // 3) Fill the task form
        String taskTitle = "UI Task " + System.currentTimeMillis();
        String taskDesc = "Created by Selenium test";

        WebElement title = tryFind(wait, new By[]{
                By.cssSelector("input[placeholder='Task Name']"),
                By.xpath("//input[@name='taskName']"),
                // Fallbacks
                By.id("title"), By.name("title"),
                By.cssSelector("input[name='title']"),
                By.cssSelector("input[placeholder*='title' i]")
        });
        title.clear();
        title.sendKeys(taskTitle);

        WebElement description = tryFind(wait, new By[]{
                By.cssSelector("textarea[placeholder='Description']"),
                By.xpath("//textarea[@name='description']"),
                // Fallbacks
                By.id("description"), By.name("description"),
                By.cssSelector("textarea[name='description']"),
                By.cssSelector("textarea[placeholder*='description' i]")
        });
        description.clear();
        description.sendKeys(taskDesc);

        // Optional: due date if exists
        try {
            WebElement due = tryFind(wait, new By[]{
                    By.id("dueDate"), By.name("dueDate"), By.cssSelector("input[name='dueDate']"),
                    By.cssSelector("input[type='date']")
            });
            // Set today as due date if field is empty
            if (due.getAttribute("value") == null || due.getAttribute("value").isEmpty()) {
                // Simple ISO date (yyyy-MM-dd) is typically accepted by input[type=date]
                java.time.LocalDate today = java.time.LocalDate.now();
                due.sendKeys(today.toString());
            }
        } catch (Exception ignored) {}

        // 4) Submit the form
        WebElement save = tryFindClickable(wait, new By[]{
                By.cssSelector("button.btn.btn-primary"),
                By.xpath("//button[contains(text(),'Add Task')]"),
                // Fallbacks
                By.cssSelector("button[type='submit']"),
                By.cssSelector("input[type='submit']"),
                By.xpath("//button[normalize-space()='Save' or normalize-space()='Add' or normalize-space()='Create']"),
                By.xpath("//input[@type='submit' and (@value='Save' or @value='Add' or @value='Create')]")
        });
        save.click();

        // 5) Verify: either success message, redirect to list, or presence of the task title somewhere
        boolean success = false;
        try {
            // a) Look for a toast/alert
            WebElement alert = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(
                    ".alert-success, .toast-success, .notification-success")));
            success = alert.isDisplayed();
        } catch (Exception ignored) {}

        if (!success) {
            // b) Check URL contains /tasks or /list
            try {
                wait.until(drv -> drv.getCurrentUrl().contains("/tasks") || drv.getCurrentUrl().contains("/list"));
                success = true;
            } catch (Exception ignored) {}
        }

        if (!success) {
            // c) Search the DOM for the task title in a common list container
            try {
                List<WebElement> matches = driver.findElements(By.xpath("//*[contains(normalize-space(text()), '" + taskTitle + "')]"));
                success = !matches.isEmpty();
            } catch (Exception ignored) {}
        }

        assertTrue(success, "Expected to see evidence that the task was added successfully");
    }
}
