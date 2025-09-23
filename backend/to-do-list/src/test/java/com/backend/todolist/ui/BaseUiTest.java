package com.backend.todolist.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BaseUiTest sets up and tears down the Selenium WebDriver.
 * - Uses WebDriverManager to download/configure the correct ChromeDriver.
 * - Supports headless mode automatically when running in CI (CI=true) or when -Dheadless=true.
 * - Reads base URL and default UI credentials from system properties:
 *   -DbaseUrl=http://localhost:8080  -Dui.username=<user>  -Dui.password=<pass>
 */
public abstract class BaseUiTest {

    protected WebDriver driver;

    protected String baseUrl;
    protected String defaultUsername;
    protected String defaultPassword;

    @BeforeEach
    void setUpDriver() {
        // Resolve configuration from system properties or environment
        String ci = System.getenv().getOrDefault("CI", "false");
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"))
                || "true".equalsIgnoreCase(ci);

        baseUrl = System.getProperty("baseUrl", "http://localhost:3000");
        defaultUsername = System.getProperty("ui.username", "test9");
        defaultPassword = System.getProperty("ui.password", "test9@123");

        // Setup ChromeDriver via WebDriverManager
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
        }
        options.addArguments("--window-size=1400,900");
        // Allow connections from any origin (helps avoid DevTools WS 403 in some environments)
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    @AfterEach
    void tearDownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Wait for a stable "Add Task" control on the dashboard/home page.
     * Tries multiple robust locators: a visible, clickable button or link with text "Add Task",
     * or a data-testid/data-test attribute commonly used for testing hooks.
     */
    protected WebElement waitForAddTaskButton(WebDriverWait wait) {
        By[] candidates = new By[]{
                By.xpath("//button[normalize-space()='Add Task']"),
                By.xpath("//a[normalize-space()='Add Task']"),
                By.cssSelector("[data-testid='add-task'], [data-test='add-task']"),
                By.xpath("//*[self::button or self::a][contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'add task')]")
        };
        for (By by : candidates) {
            try {
                return wait.until(ExpectedConditions.elementToBeClickable(by));
            } catch (Exception ignored) {}
        }
        throw new IllegalStateException("Could not locate a stable 'Add Task' control on the dashboard");
    }

    /**
     * Ensures we are on the dashboard and clicks the Add Task control.
     * It first attempts to wait for the Add Task button/link to be clickable on the current page; if not found,
     * it navigates to baseUrl (/) and retries.
     */
    protected void goToDashboardAndClickAddTask() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            WebElement addTask = waitForAddTaskButton(wait);
            addTask.click();
            return;
        } catch (Exception ignored) {}

        // Fallback: navigate to dashboard root and try again
        driver.navigate().to(baseUrl + "/");
        WebElement addTask = waitForAddTaskButton(new WebDriverWait(driver, Duration.ofSeconds(20)));
        addTask.click();
    }
}
