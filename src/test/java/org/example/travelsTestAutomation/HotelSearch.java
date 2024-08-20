package org.example.travelsTestAutomation;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.DriverFactory;
import utils.DriverType;
import utils.myParameter;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HotelSearch implements myParameter {

    WebDriver driver;

    @BeforeTest
    public void beforeTest() {
        driver = DriverFactory.getDriver(DriverType.CHROME);
        driver.get(URL_KURS_SELENIUM_DEMO);
    }

    @Test
    public void searchHotel() {


        driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//span[text()='Search by Hotel or City Name']")).click();
        driver.findElement(By.xpath("//div[@id='select2-drop']//input")).sendKeys("Dubai");
        driver.findElement(By.xpath("//span[@class='select2-match' and text()='Dubai']")).click();
    }

    private void waitForElementToExist(By locator) {
        // Pierwsze oczekiwanie: Sprawdzenie, czy element jest wyświetlany
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        // Drugie oczekiwanie: Użycie FluentWait do bardziej zaawansowanego sprawdzania
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(ElementNotInteractableException.class);

        fluentWait.until(driver -> {
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty()) {
                System.out.println("element na stronie");
                return true;
            } else {
                System.out.println("element brak na stronie");
                return false;
            }
        });
    }
}
