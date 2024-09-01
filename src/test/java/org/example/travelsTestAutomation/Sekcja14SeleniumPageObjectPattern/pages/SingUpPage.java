package org.example.travelsTestAutomation.Sekcja14SeleniumPageObjectPattern.pages;

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
    private WebDriver driver;

    public SingUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.driver = driver;
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


    public SingUpPage setFirstname(String firstName) {
        firstNameInput.sendKeys(firstName);
        return this;
    }

    public SingUpPage setLastNames(String lastNames) {
        lastNameInput.sendKeys(lastNames);
        return this;
    }

    public SingUpPage setPhone(String phone) {
        phoneInput.sendKeys(phone);
        return this;
    }

    public SingUpPage setEmail(String email) {
        emailInput.sendKeys(email);
        return this;
    }

    public SingUpPage setPassword(String password) {
        passwordInput.sendKeys(password);
        return this;
    }

    public SingUpPage setConfirmPassword(String confirmpassword) {
        confirmPasswordInput.sendKeys(confirmpassword);
        return this;
    }

    public String getPasswordValue() {
        return passwordInput.getAttribute("value");
    }

    public String getConfirmPasswordValue() {
        return confirmPasswordInput.getAttribute("value");
    }

    public LoggedUserPage singUp() {
        singUpBtnClick.click();
        return new LoggedUserPage(driver);
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

}
