package ru.rgs.tests.junit5task02;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;;

    @BeforeAll
    static void beforeAll() {

    }

    @BeforeEach
    void before() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        actions = new Actions(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 30, 2000);
    }

    @AfterEach
    void after() { driver.quit(); }

    @AfterAll
    static void afterAll() {

    }
}
