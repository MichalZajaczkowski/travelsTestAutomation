package org.example.travelsTestAutomation;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.DriverFactory;
import utils.DriverType;
import utils.myParameter;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class SignUpTest implements myParameter {

    WebDriver driver;

    private static final String[] FIRST_NAMES = {"John", "Michael", "Sarah", "Jessica", "David", "Emily", "James", "Sophie"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis", "Garcia"};
    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+{}[]|:;<>?,./";

    private static final String ALL_CHARACTERS = UPPER_CASE + LOWER_CASE + DIGITS + SPECIAL_CHARACTERS;

    private static final String DOMAIN = "@example.com";
    private static final Random random = new Random();

    @BeforeTest
    public void beforeTest() {
        driver = DriverFactory.getDriver(DriverType.CHROME);
        driver.get(URL_KURS_SELENIUM_DEMO);
    }

    @Test
    public void singUp() {

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
        driver.findElement(By.name("email")).sendKeys(generateRandomEmail());

        // Wygenerowanie losowego hasła i przypisanie do zmiennej
        String password = generateRandomPassword();

        // Wprowadzenie hasła do pola password
        WebElement passwordElement = driver.findElement(By.name("password"));
        passwordElement.sendKeys(password);

        // Wprowadzenie tego samego hasła do pola confirmpassword
        WebElement confirmPasswordElement = driver.findElement(By.name("confirmpassword"));
        confirmPasswordElement.sendKeys(password);

        // Asercja sprawdzająca, czy oba hasła są takie same
        String passwordValue = passwordElement.getAttribute("value");
        String confirmPasswordValue = confirmPasswordElement.getAttribute("value");
        Assert.assertEquals(passwordValue, confirmPasswordValue, "Passwords do not match!");

        //driver.findElement(By.xpath("//button[@type='submit' and text()=' Sign Up']"));
        driver.findElement(By.xpath("//button[text()=' Sign Up']")).click();

        WebElement textH3 = waitForElementToExist(By.xpath("//h3[@class='RTL']"));
        Assert.assertTrue(textH3.getText().contains("Hi, " + firstName + " " + lastName), "Tekst powitalny nie zawiera poprawnego imienia i nazwiska.");
        Assert.assertEquals(textH3.getText(), "Hi, " + firstName + " " + lastName, "jest ok");
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

    // Metoda do generowania losowego imienia
    public static String generateRandomFirstName() {
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
    }

    // Metoda do generowania losowego nazwiska
    public static String generateRandomLastName() {
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }

    // Metoda do generowania losowego numeru telefonu
    public static String generateRandomPhoneNumber() {
        StringBuilder phoneNumber = new StringBuilder();
        phoneNumber.append("+48 "); // Prefiks kraju (Polska)
        for (int i = 0; i < 9; i++) {
            phoneNumber.append(random.nextInt(10)); // Dodaj cyfry do numeru telefonu
            if (i == 2 || i == 5) {
                phoneNumber.append(" "); // Dodaj spacje po trzeciej i szóstej cyfrze
            }
        }
        return phoneNumber.toString();
    }

    // Metoda do generowania losowego poprawnego adresu e-mail
    public static String generateRandomEmail() {
        String firstName = generateRandomFirstName().toLowerCase();
        String lastName = generateRandomLastName().toLowerCase();
        int number = random.nextInt(1000); // Dodaj losowy numer na końcu, aby uniknąć powtórzeń
        return firstName + "." + lastName + number + DOMAIN;
    }

    // Metoda do generowania losowego hasła
    public static String generateRandomPassword() {
        int passwordLength = 8 + random.nextInt(9); // Losowa długość od 8 do 16 znaków
        StringBuilder password = new StringBuilder();

        // Dodaj losowo wybrane znaki do hasła
        for (int i = 0; i < passwordLength; i++) {
            char randomChar = ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length()));
            password.append(randomChar);
        }

        return password.toString();
    }
}
