package org.example.travelsTestAutomation.home;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import utils.BaseTestMethod;
import utils.DriverFactory;
import utils.DriverType;
import utils.myParameter;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.travelsTestAutomation.SignUpTest.*;

public class RegisterTest extends BaseTestMethod implements myParameter {

    @Test
    public void testRegisterWithoutDate() throws InterruptedException {

        driver.findElements(By.xpath("//li[@id='li_myaccount']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);

        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();
        driver.findElement(By.xpath("//button[text()=' Sign Up']")).click();

        WebElement elements = driver.findElement(By.xpath("//div[@class='resultsignup']"));
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(d -> elements.isDisplayed());
        List<String> errors = driver.findElements(By.xpath("//div[@class='alert alert-danger']//p"))
                .stream()
                .map(WebElement::getText)
                .toList();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(errors.contains("The Email field is required."));
        softAssert.assertTrue(errors.contains("The Password field is required."));
        softAssert.assertTrue(errors.contains("The Password field is required."));
        softAssert.assertTrue(errors.contains("The First name field is required."));
        softAssert.assertTrue(errors.contains("The Last Name field is required."));
        softAssert.assertAll();
    }

    @Test
    public void testRegisterWithoutDate1() {

        driver.findElements(By.xpath("//li[@id='li_myaccount']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);

        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();
        driver.findElement(By.name("firstname")).sendKeys("asdf");
        driver.findElement(By.xpath("//button[text()=' Sign Up']")).click();
       // List<WebElement> webElement = waitForElementToExist(By.xpath("//div[@class='alert alert-danger']//p"));
        // 1. Poczekaj aż pojawi się element
        WebElement alertBox = waitForElementToExist(By.cssSelector("div.resultsignup div.alert.alert-danger"));
    //    WebElement alertBox = waitForElementToExist(By.xpath("//div[@class='alert alert-danger']//p"));

        // 2. Pobierz stringi z <p>
        List<WebElement> errorMessages = alertBox.findElements(By.tagName("p"));

        // 3. Sprawdź, czy wszystkie komunikaty są wyświetlone
        for (WebElement errorMessage : errorMessages) {
            Assert.assertTrue(errorMessage.isDisplayed(), "Komunikat nie jest wyświetlony: " + errorMessage.getText());
        }
        // Sprawdź, czy komunikaty są wyświetlone oraz czy pole zostało uzupełnione
        validateFieldAndErrorMessage("firstname", "The First name field is required.", errorMessages);
        validateFieldAndErrorMessage("lastname", "The Last Name field is required.", errorMessages);
       // validateFieldAndErrorMessage("phone", "The Mobile Number field is required.", errorMessages);
        validateFieldAndErrorMessage("email", "The Email field is required.", errorMessages);
        validateFieldAndErrorMessage("password", "The Password field is required.", errorMessages);

        // Opcjonalnie: wypisz wszystkie komunikaty błędów
        errorMessages.forEach(message -> System.out.println("Komunikat błędu: " + message.getText()));
    }

    @Test
    public void emailValidationTest(){
        driver.findElements(By.xpath("//li[@id='li_myaccount']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);

        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();

        String firstName = generateRandomFirstName();
        String lastName = generateRandomLastName();

        driver.findElement(By.name("firstname")).sendKeys(firstName);
        driver.findElement(By.name("lastname")).sendKeys(lastName);
        driver.findElement(By.name("phone")).sendKeys(generateRandomPhoneNumber());
        driver.findElement(By.name("email")).sendKeys("errorMail.pl");

        // Wygenerowanie losowego hasła i przypisanie do zmiennej
        String password = generateRandomPassword();

        // Wprowadzenie hasła do pola password
        WebElement passwordElement = driver.findElement(By.name("password"));
        passwordElement.sendKeys(password);

        // Wprowadzenie tego samego hasła do pola confirmpassword
        WebElement confirmPasswordElement = driver.findElement(By.name("confirmpassword"));
        confirmPasswordElement.sendKeys(password);
        driver.findElement(By.xpath("//button[text()=' Sign Up']")).click();

        // 1. Poczekaj aż pojawi się element
        WebElement alertBox = waitForElementToExist(By.cssSelector("div.alert.alert-danger"));

        // 2. Pobierz stringi z <p>
        List<WebElement> errorMessages = alertBox.findElements(By.tagName("p"));

        // 3. Sprawdź, czy wszystkie komunikaty są wyświetlone
        for (WebElement errorMessage : errorMessages) {
            Assert.assertTrue(errorMessage.isDisplayed(), "Komunikat nie jest wyświetlony: " + errorMessage.getText());
        }
        errorMessages.forEach(message -> System.out.println("Komunikat błędu: " + message.getText()));
    }
    @Test
    public void emailValidationTest2(){
        driver.findElements(By.xpath("//li[@id='li_myaccount']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);

        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();

        String firstName = generateRandomFirstName();
        String lastName = generateRandomLastName();

        driver.findElement(By.name("firstname")).sendKeys(firstName);
        driver.findElement(By.name("lastname")).sendKeys(lastName);
        driver.findElement(By.name("phone")).sendKeys(generateRandomPhoneNumber());
        driver.findElement(By.name("email")).sendKeys("errorMail.pl");

        // Wygenerowanie losowego hasła i przypisanie do zmiennej
        String password = generateRandomPassword();

        // Wprowadzenie hasła do pola password
        WebElement passwordElement = driver.findElement(By.name("password"));
        passwordElement.sendKeys(password);

        // Wprowadzenie tego samego hasła do pola confirmpassword
        WebElement confirmPasswordElement = driver.findElement(By.name("confirmpassword"));
        confirmPasswordElement.sendKeys(password);
        driver.findElement(By.xpath("//button[text()=' Sign Up']")).click();

        // 1. Poczekaj aż pojawi się element
        WebElement alertBox = waitForElementToExist(By.cssSelector("div.alert.alert-danger"));

        // 2. Pobierz stringi z <p>
        List<String> error = driver.findElements(By.xpath("//div[@class='alert alert-danger']//p"))
                .stream()
                .map(WebElement:: getText)
                .collect(Collectors.toList());

        // 3. Sprawdź, czy wszystkie komunikaty są wyświetlone
        Assert.assertTrue(error.contains("The Email field must contain a valid email address."));
    }


    private WebElement waitForElementToExist(By locator) {
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

    // Metoda sprawdzająca, czy pole formularza zostało uzupełnione, oraz czy odpowiedni komunikat błędu został wyświetlony
    private void validateFieldAndErrorMessage(String fieldName, String expectedErrorMessage, List<WebElement> errorMessages) {
        WebElement field = driver.findElement(By.name(fieldName));
        boolean isFieldFilled = !field.getAttribute("value").isEmpty();
        boolean isErrorDisplayed = errorMessages.stream()
                .anyMatch(errorMessage -> errorMessage.getText().equals(expectedErrorMessage));

        if (!isFieldFilled) {
            Assert.assertTrue(isErrorDisplayed, "Brak komunikatu dla pustego pola: " + fieldName);
        } else {
            Assert.assertFalse(isErrorDisplayed, "Nieoczekiwany komunikat błędu dla wypełnionego pola: " + fieldName);
        }
    }
}
