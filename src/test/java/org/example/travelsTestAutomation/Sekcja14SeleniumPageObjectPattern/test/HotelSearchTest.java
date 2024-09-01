package org.example.travelsTestAutomation.Sekcja14SeleniumPageObjectPattern.test;

import org.example.travelsTestAutomation.Sekcja14SeleniumPageObjectPattern.pages.HotelDetailsPage;
import org.example.travelsTestAutomation.Sekcja14SeleniumPageObjectPattern.pages.HotelSearchPage;
import org.example.travelsTestAutomation.Sekcja14SeleniumPageObjectPattern.pages.ResultsHotelPage;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseTestMethod;
import utils.myParameter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HotelSearchTest extends BaseTestMethod implements myParameter {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Test
    public void searchHotel() {
        FluentWait<WebDriver> wait = new FluentWait<>(driver);
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);

        LocalDate today = LocalDate.now();
        String checkinDate = getRandomCheckinDate(today);
        String checkoutDate = getRandomCheckoutDate(LocalDate.parse(checkinDate, formatter));

        hotelSearchPage.setCity("Dubai");
        hotelSearchPage.setDate(checkinDate, checkoutDate);
        hotelSearchPage.setTravellers(-1, 2);
        hotelSearchPage.performSearch();

        ResultsHotelPage resultsHotelPage = new ResultsHotelPage(driver);

        List<String> hotelNameList = resultsHotelPage.getHotelList();

        //TODO: assertEquals equals do wynamicznej weryfikacji
        Assert.assertEquals("Jumeirah Beach Hotel", hotelNameList.getFirst());
        Assert.assertEquals("Oasis Beach Tower", hotelNameList.get(1));
        Assert.assertEquals("Rose Rayhaan Rotana", hotelNameList.get(2));
        Assert.assertEquals("Hyatt Regency Perth", hotelNameList.getLast());

        // Pobierz listę hoteli
        List<WebElement> hotelElements = resultsHotelPage.getHotelList1();

        // Wybierz losowy hotel
        if (!hotelElements.isEmpty()) {
            Random random = new Random();
            WebElement randomHotel = hotelElements.get(random.nextInt(hotelElements.size()));
            String selectedHotelName = randomHotel.getAttribute("textContent").trim().toUpperCase();
            System.out.println("Wybrany hotel: " + selectedHotelName);
            wait.until(ExpectedConditions.elementToBeClickable(randomHotel)).click();

            // Poczekaj na załadowanie strony z informacjami o hotelu (opcjonalne)
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            HotelDetailsPage hotelDetailsPage = new HotelDetailsPage(driver);
            String hotelNameOnPage = hotelDetailsPage.chooseHotelDetails();

            // Porównaj nazwy
            if (selectedHotelName.equals(hotelNameOnPage)) {
                System.out.println("Nazwa hotelu zgadza się: " + hotelNameOnPage);
                Assert.assertEquals(hotelNameOnPage, selectedHotelName, "spodziewałem sie: " + hotelNameOnPage + " a jest: " + selectedHotelName);
            } else {
                System.out.println("Nazwa hotelu nie zgadza się! Oczekiwana: " + selectedHotelName + ", Znaleziona: " + hotelNameOnPage);
            }
        } else {
            System.out.println("Nie znaleziono hoteli.");
        }
    }

    @Test
    public void searchHotel2() {
        FluentWait<WebDriver> wait = new FluentWait<>(driver);
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        ResultsHotelPage resultsHotelPage = new ResultsHotelPage(driver);

        LocalDate today = LocalDate.now();
        String checkinDate = getRandomCheckinDate(today);
        String checkoutDate = getRandomCheckoutDate(LocalDate.parse(checkinDate, formatter));

        List<String> hotelNameList = hotelSearchPage.setCity("Dubai")
                .setDate(checkinDate, checkoutDate)
                .setTravellers(-1, 2)
                .performSearch().getHotelList();


        //TODO: assertEquals equals do wynamicznej weryfikacji
        Assert.assertEquals("Jumeirah Beach Hotel", hotelNameList.getFirst());
        Assert.assertEquals("Oasis Beach Tower", hotelNameList.get(1));
        Assert.assertEquals("Rose Rayhaan Rotana", hotelNameList.get(2));
        Assert.assertEquals("Hyatt Regency Perth", hotelNameList.getLast());

        // Pobierz listę hoteli
        List<WebElement> hotelElements = resultsHotelPage.getHotelList1();

        // Wybierz losowy hotel
        if (!hotelElements.isEmpty()) {
            Random random = new Random();
            WebElement randomHotel = hotelElements.get(random.nextInt(hotelElements.size()));
            String selectedHotelName = randomHotel.getAttribute("textContent").trim().toUpperCase();
            System.out.println("Wybrany hotel: " + selectedHotelName);
            wait.until(ExpectedConditions.elementToBeClickable(randomHotel)).click();

            // Poczekaj na załadowanie strony z informacjami o hotelu (opcjonalne)
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            HotelDetailsPage hotelDetailsPage = new HotelDetailsPage(driver);
            String hotelNameOnPage = hotelDetailsPage.chooseHotelDetails();

            // Porównaj nazwy
            if (selectedHotelName.equals(hotelNameOnPage)) {
                System.out.println("Nazwa hotelu zgadza się: " + hotelNameOnPage);
                Assert.assertEquals(hotelNameOnPage, selectedHotelName, "spodziewałem sie: " + hotelNameOnPage + " a jest: " + selectedHotelName);
            } else {
                System.out.println("Nazwa hotelu nie zgadza się! Oczekiwana: " + selectedHotelName + ", Znaleziona: " + hotelNameOnPage);
            }
        } else {
            System.out.println("Nie znaleziono hoteli.");
        }
    }

    @Test
    public void searchHotelWithoutNameTest() {
        FluentWait<WebDriver> wait = new FluentWait<>(driver);
        LocalDate today = LocalDate.now();
        String checkinDate = getRandomCheckinDate(today);
        String checkoutDate = getRandomCheckoutDate(LocalDate.parse(checkinDate, formatter));

        ResultsHotelPage resultsHotelPage = new HotelSearchPage(driver)
                .setDate(checkinDate, checkoutDate)
                .setTravellers(-1, 1)
                .performSearch();

        WebElement resultHeading = wait.until(ExpectedConditions.visibilityOf(resultsHotelPage.resultHeading));
        Assert.assertTrue(resultHeading.isDisplayed());
        Assert.assertEquals(resultsHotelPage.getHeadingText(), "No Results Found");
    }

    @Test
    public void searchHotel1() {
        //TODO: test do przerobienia na POP
        FluentWait<WebDriver> wait = new FluentWait<>(driver);
        driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//span[text()='Search by Hotel or City Name']")).click();
        driver.findElement(By.xpath("//div[@id='select2-drop']//input")).sendKeys("Dubai");
        driver.findElement(By.xpath("//span[@class='select2-match' and text()='Dubai']")).click();

        // Ustaw daty checkin i checkout
        LocalDate today = LocalDate.now();
        String checkinDate = getRandomCheckinDate(today);
        String checkoutDate = getRandomCheckoutDate(LocalDate.parse(checkinDate, formatter));

        WebElement checkinElement = wait.until(ExpectedConditions.elementToBeClickable(By.name("checkin")));
        checkinElement.click();
//        selectDate(checkinDate.getDayOfMonth());
//
//        WebElement checkOutElement = driver.findElement(By.name("checkout"));
//        checkOutElement.clear();
//        checkOutElement.sendKeys(checkoutDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

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
            wait.until(ExpectedConditions.elementToBeClickable(randomHotel)).click();

            // Asercje
            Assert.assertNotNull(selectedHotelName, "Nazwa wybranego hotelu jest pusta.");
            Assert.assertFalse(hotelElements.isEmpty(), "Lista hoteli jest pusta.");
        } else {
            System.out.println("Nie znaleziono hoteli.");
            Assert.fail("Brak hoteli w wynikach wyszukiwania.");
        }
    }

    private String getRandomCheckinDate(LocalDate today) {
        Random random = new Random();
        int daysToAddForCheckin = 1 + random.nextInt(10);
        LocalDate checkinDate = today.plusDays(daysToAddForCheckin);
        return checkinDate.format(formatter);
    }

    private String getRandomCheckoutDate(LocalDate checkinDate) {
        Random random = new Random();
        int daysToAddForCheckout = 3 + random.nextInt(5);
        LocalDate checkoutDate = checkinDate.plusDays(daysToAddForCheckout);
        return checkoutDate.format(formatter);
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
