package utils;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

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
                    driver.manage().window().setSize(new Dimension(1920,1080));
                }
                case FIREFOX -> {
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    // Dodaj opcje dla Firefox, jeśli potrzebujesz
                    driver = new FirefoxDriver(firefoxOptions);
                }
                case OPERA -> {
                    OperaOptions operaOptions = new OperaOptions();
                    // Dodaj opcje dla Opery, jeśli potrzebujesz
                    driver = new OperaDriver(operaOptions);
                }
                case EDGE -> {
                    EdgeOptions edgeOptions = new EdgeOptions();
                    // Dodaj opcje dla Edge, jeśli potrzebujesz
                    driver = new EdgeDriver(edgeOptions);
                }
                default -> throw new IllegalArgumentException("Unknown browser: " + driverType);
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
}
