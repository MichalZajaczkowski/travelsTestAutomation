package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;

public class DriverFactory {

    private static WebDriver driver;

    public static WebDriver getDriver(DriverType driverType) {
        if (driver == null) {
            switch (driverType) {
                case CHROME -> {
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--disable-search-engine-choice-screen");
                    chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
                    driver = new ChromeDriver(chromeOptions);
                    driver.manage().window().setSize(new Dimension(1920, 1080));
                }
                case FIREFOX -> {
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    // Dodaj opcje dla Firefox, jeśli potrzebujesz
                    driver = new FirefoxDriver(firefoxOptions);
                    driver.manage().window().setSize(new Dimension(1920, 1080));
                }
                case OPERA -> {
                    OperaOptions operaOptions = new OperaOptions();
                    // Dodaj opcje dla Opery, jeśli potrzebujesz
                    driver = new OperaDriver(operaOptions);
                    driver.manage().window().setSize(new Dimension(1920, 1080));
                }
                case EDGE -> {
                    EdgeOptions edgeOptions = new EdgeOptions();
                    // Dodaj opcje dla Edge, jeśli potrzebujesz
                    driver = new EdgeDriver(edgeOptions);
                    driver.manage().window().setSize(new Dimension(1920, 1080));
                }
                default -> throw new IllegalArgumentException("Unknown browser: " + driverType);
            }
        } else {
            // Jeśli driver nie jest null, ale jest zamknięty, ponownie inicjalizuj sesję
            if (((RemoteWebDriver) driver).getSessionId() == null) {
                quitDriver();  // upewnij się, że driver jest null
                return getDriver(driverType);  // rekurencyjne wywołanie, aby zainicjować nową sesję
            }
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public WebElement waitForElementToExist(By locator) {
        // Użycie FluentWait do bardziej zaawansowanego sprawdzania
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10)) // Zwiększenie czasu oczekiwania na element
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NoSuchElementException.class, ElementNotInteractableException.class);

        return fluentWait.until(driver -> {
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty() && elements.getFirst().isDisplayed()) {
                return elements.getFirst(); // Zwróć pierwszy element
            } else {
                return null;
            }
        });
    }
}
