package org.example.travelsTestAutomation.Sekcja14SeleniumPageObjectPattern.pages;

import org.example.travelsTestAutomation.Sekcja14SeleniumPageObjectPattern.model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class SingUpPage {
    private static final String[] FIRST_NAMES = {"John", "Michael", "Sarah", "Jessica", "David", "Emily", "James", "Sophie"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis", "Garcia"};
    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+{}[]|:;<>?,./";
    private static final String ALL_CHARACTERS = UPPER_CASE + LOWER_CASE + DIGITS + SPECIAL_CHARACTERS;
    private static final String DOMAIN = "@example.com";
    private static final Random random = new Random();
    protected WebDriverWait wait;

    public SingUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @FindBy(name="firstname")
    public WebElement firstNameInput;

    @FindBy(name="lastname")
    public WebElement lastNameInput;

    @FindBy(name="phone")
    public WebElement phoneInput;

    @FindBy(name="email")
    public WebElement emailInput;

    @FindBy(name="password")
    public WebElement passwordInput;

    @FindBy(name="confirmpassword")
    public WebElement confirmPasswordInput;

    @FindBy(xpath="//button[text()=' Sign Up']")
    public WebElement singUpBtnClick;

    @FindBy(xpath="//div[@class='alert alert-danger']//p")
    public List<WebElement> errors;


    @FindBy(xpath = "//div[@class='resultsignup']")
    private WebElement resultSignup;


    public void setFirstname(String firstName) {
        firstNameInput.sendKeys(firstName);
    }

    public void setLastNames(String lastNames) {
        lastNameInput.sendKeys(lastNames);
    }

    public void setPhone(String phone) {
        phoneInput.sendKeys(phone);
    }

    public void setEmail(String email) {
        emailInput.sendKeys(email);
    }

    public void setPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void setConfirmPassword(String confirmpassword) {
        confirmPasswordInput.sendKeys(confirmpassword);
    }

    public String getPasswordValue() {
        return passwordInput.getAttribute("value");
    }

    public String getConfirmPasswordValue() {
        return confirmPasswordInput.getAttribute("value");
    }

    public void singUpBtnClick() {
        singUpBtnClick.click();
    }

    public List<String> getErrors() {
        return errors.stream()
                .map(WebElement::getText)
                .toList();
    }

    public void waitForResultSignupVisibility() {
        // Czekaj aż element stanie się widoczny
        WebElement element = wait.until(ExpectedConditions.visibilityOf(resultSignup));
        Assert.assertTrue(element.isDisplayed(), "Element 'resultSignup' nie jest widoczny.");
        // Sprawdź czy element jest widoczny
    }

    public void filSingUpForm() {
        setFirstname(firstName);
        setLastNames(lastName);
        setPhone(generateRandomPhoneNumber());
        setEmail(generateRandomEmail());
        setPassword(password);
        setConfirmPassword(password);
        singUpBtnClick();
    }
    public void filSingUpForm2() {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        phoneInput.sendKeys(generateRandomPhoneNumber());
        emailInput.sendKeys(generateRandomEmail());
        passwordInput.sendKeys(password);
        confirmPasswordInput.sendKeys(password);
        singUpBtnClick.click();
    }

    public void filSingUpForm3(String firstName, String lastName, String phone, String email, String password) {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        phoneInput.sendKeys(phone);
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        confirmPasswordInput.sendKeys(password);
        singUpBtnClick.click();
    }

    public void filSingUpForm4(User user) {
        firstNameInput.sendKeys(user.getFirstName());
        lastNameInput.sendKeys(user.getLastName());
        phoneInput.sendKeys(user.getPhone());
        emailInput.sendKeys(user.getEmail());
        passwordInput.sendKeys(user.getPassword());
        confirmPasswordInput.sendKeys(user.getPassword());
        singUpBtnClick.click();
    }

    String firstName = generateRandomFirstName();
    String lastName = generateRandomLastName();
    String password = generateRandomPassword();

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
