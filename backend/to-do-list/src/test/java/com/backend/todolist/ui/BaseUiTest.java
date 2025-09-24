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
        // Use the correct default password with capital 'T'
        defaultPassword = System.getProperty("ui.password", "Test9@123");

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
     * Tries multiple robust locators based on the application's HTML.
     */
    protected WebElement waitForAddTaskButton(WebDriverWait wait) {
        By[] candidates = new By[]{
                // Locators based on provided HTML: <a class="btn btn-outline-light btn-lg px-4" href="/add">➕ Add New</a>
                By.cssSelector("a.btn[href='/add']"),
                By.xpath("//a[contains(normalize-space(), 'Add New')]"),
                By.partialLinkText("Add New"),
                // Original fallbacks
                By.xpath("//button[normalize-space()='Add Task']"),
                By.cssSelector("[data-testid='add-task']")
        };
        for (By by : candidates) {
            try {
                return wait.until(ExpectedConditions.elementToBeClickable(by));
            } catch (Exception ignored) {}
        }
        throw new IllegalStateException("Could not locate a stable 'Add Task' control on the dashboard");
    }
}
