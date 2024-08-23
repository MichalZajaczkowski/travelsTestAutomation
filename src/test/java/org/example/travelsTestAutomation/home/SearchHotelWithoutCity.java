package org.example.travelsTestAutomation.home;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.DriverFactory;
import utils.DriverType;
import utils.myParameter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class SearchHotelWithoutCity implements myParameter {

    WebDriver driver;

    @BeforeTest
    public void beforeTest() {
        driver = DriverFactory.getDriver(DriverType.CHROME);
        driver.get(URL_KURS_SELENIUM_DEMO);
    }

    @Test
    public void SearchHotelWithoutCityTest() {

        // Ustaw daty checkin i checkout
        LocalDate today = getTodayDate();
        LocalDate checkinDate = getRandomCheckinDate(today);
        LocalDate checkoutDate = getRandomCheckoutDate(checkinDate);

        driver.findElement(By.name("checkin")).click();
        selectDate(checkinDate.getDayOfMonth());

        WebElement checkOutElement = driver.findElement(By.name("checkout"));
        checkOutElement.clear();
        checkOutElement.sendKeys(checkoutDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        // Ustawienia dla podróżujących
        driver.findElement(By.id("travellersInput")).click();
        driver.findElement(By.id("adultMinusBtn")).click();
        driver.findElement(By.id("childPlusBtn")).click();

        // Wyszukaj hotele
        driver.findElement(By.xpath("(//button[text()=' Search'])")).click();

        WebElement webElement = driver.findElement(By.xpath("//h2[text()='No Results Found']"));
        webElement.getText();

        Assert.assertTrue(webElement.isDisplayed());
        Assert.assertTrue(webElement.getText().contains("No Results Found"));
        Assert.assertEquals(webElement.getText(), "No Results Found");

    }

    private LocalDate getTodayDate() {
        return LocalDate.now();
    }

    private LocalDate getRandomCheckinDate(LocalDate today) {
        Random random = new Random();
        int daysToAddForCheckin = 1 + random.nextInt(10);
        return today.plusDays(daysToAddForCheckin);
    }

    private LocalDate getRandomCheckoutDate(LocalDate checkinDate) {
        Random random = new Random();
        int daysToAddForCheckout = 3 + random.nextInt(5);
        return checkinDate.plusDays(daysToAddForCheckout);
    }

    private void selectDate(int day) {
        List<WebElement> days = driver.findElements(By.xpath("//td[@class='day ' and text()='" + day + "']"));
        days.stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);
    }
}
