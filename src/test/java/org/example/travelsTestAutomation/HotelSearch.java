package org.example.travelsTestAutomation;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.BaseTestMethod;
import utils.DriverFactory;
import utils.DriverType;
import utils.myParameter;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class HotelSearch extends BaseTestMethod implements myParameter {

    @Test
    public void searchHotel() {

        driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//span[text()='Search by Hotel or City Name']")).click();
        driver.findElement(By.xpath("//div[@id='select2-drop']//input")).sendKeys("Dubai");
        driver.findElement(By.xpath("//span[@class='select2-match' and text()='Dubai']")).click();

        //driver.findElement(By.name("checkin")).sendKeys("17/09/2024");
        driver.findElement(By.name("checkin")).click();
        driver.findElements(By.xpath("//td[@class='day ' and text()='31']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);
        // driver.findElement(By.name("checkout")).sendKeys("27/09/2024");
        WebElement checkOutElement = driver.findElement(By.name("checkout"));
        checkOutElement.clear();
        checkOutElement.sendKeys("27/09/2024");

        driver.findElement(By.id("travellersInput")).click();
        driver.findElement(By.id("adultMinusBtn")).click();
        driver.findElement(By.id("childPlusBtn")).click();

        driver.findElement(By.xpath("(//button[text()=' Search'])")).click();

        List<String> hotelNameList = driver.findElements(By.xpath("(//h4[contains(@class,'list_title')]//b)"))
                .stream()
                .map(webElement -> webElement.getAttribute("textContent"))
                .toList();
        hotelNameList.forEach(System.out::println);

        Assert.assertEquals("Jumeirah Beach Hotel", hotelNameList.getFirst());
        Assert.assertEquals("Oasis Beach Tower", hotelNameList.get(1));
        Assert.assertEquals("Rose Rayhaan Rotana", hotelNameList.get(2));
        Assert.assertEquals("Hyatt Regency Perth", hotelNameList.getLast());

        // Pobierz listę hoteli
        List<WebElement> hotelElements = driver.findElements(By.xpath("//h4[contains(@class,'list_title')]//b"));

        // Wybierz losowy hotel
        if (!hotelElements.isEmpty()) {
            Random random = new Random();
            WebElement randomHotel = hotelElements.get(random.nextInt(hotelElements.size()));
            String selectedHotelName = randomHotel.getAttribute("textContent").trim().toUpperCase();
            System.out.println("Wybrany hotel: " + selectedHotelName);
            randomHotel.click();

            // Poczekaj na załadowanie strony z informacjami o hotelu (opcjonalne)
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            // Pobierz nazwę hotelu z elementu <span class="ellipsis ttu">
            WebElement hotelNameElement = driver.findElement(By.cssSelector("div.mob-trip-info-head .ellipsis.ttu span"));
            String hotelNameOnPage = hotelNameElement.getText().trim();

            // Porównaj nazwy
            if (selectedHotelName.equals(hotelNameOnPage)) {
                System.out.println("Nazwa hotelu zgadza się: " + hotelNameOnPage);
            } else {
                System.out.println("Nazwa hotelu nie zgadza się! Oczekiwana: " + selectedHotelName + ", Znaleziona: " + hotelNameOnPage);
            }
        } else {
            System.out.println("Nie znaleziono hoteli.");
        }
    }

    @Test
    public void searchHotel1() {
        FluentWait<WebDriver> wait = new FluentWait<>(driver);
        driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//span[text()='Search by Hotel or City Name']")).click();
        driver.findElement(By.xpath("//div[@id='select2-drop']//input")).sendKeys("Dubai");
        driver.findElement(By.xpath("//span[@class='select2-match' and text()='Dubai']")).click();

        // Ustaw daty checkin i checkout
        LocalDate today = LocalDate.now();
        LocalDate checkinDate = getRandomCheckinDate(today);
        LocalDate checkoutDate = getRandomCheckoutDate(checkinDate);

        WebElement checkinElement = wait.until(ExpectedConditions.elementToBeClickable(By.name("checkin")));
        checkinElement.click();
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

        // Pobierz listę hoteli
        List<WebElement> hotelElements = driver.findElements(By.xpath("//h4[contains(@class,'list_title')]//b"));

        // Wybierz losowy hotel i asercje
        if (!hotelElements.isEmpty()) {
            Random random = new Random();
            WebElement randomHotel = hotelElements.get(random.nextInt(hotelElements.size()));
            String selectedHotelName = randomHotel.getText();
            System.out.println("Wybrany hotel: " + randomHotel.getAttribute("textContent"));

            // Kliknij wybrany hotel
            randomHotel.click();

            // Asercje
            Assert.assertNotNull(selectedHotelName, "Nazwa wybranego hotelu jest pusta.");
            Assert.assertFalse(hotelElements.isEmpty(), "Lista hoteli jest pusta.");
        } else {
            System.out.println("Nie znaleziono hoteli.");
            Assert.fail("Brak hoteli w wynikach wyszukiwania.");
        }
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
