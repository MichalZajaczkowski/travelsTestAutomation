package org.example.travelsTestAutomation.Sekcja14SeleniumPageObjectPattern.test;

import org.example.travelsTestAutomation.Sekcja14SeleniumPageObjectPattern.model.User;
import org.example.travelsTestAutomation.Sekcja14SeleniumPageObjectPattern.pages.HotelSearchPage;
import org.example.travelsTestAutomation.Sekcja14SeleniumPageObjectPattern.pages.LoggedUserPage;
import org.example.travelsTestAutomation.Sekcja14SeleniumPageObjectPattern.pages.SingUpPage;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseTestMethod;
import utils.myParameter;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class SignUpPOPTest extends BaseTestMethod implements myParameter {


    private static final String[] FIRST_NAMES = {"John", "Michael", "Sarah", "Jessica", "David", "Emily", "James", "Sophie"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis", "Garcia"};
    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+{}[]|:;<>?,./";
    private static final String ALL_CHARACTERS = UPPER_CASE + LOWER_CASE + DIGITS + SPECIAL_CHARACTERS;
    private static final String DOMAIN = "@example.com";
    private static final Random random = new Random();

    @Test
    public void singUpTest() {

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        SingUpPage singUpPage = new SingUpPage(driver);
        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);
        hotelSearchPage.openNewAccount();

        String firstName = generateRandomFirstName();
        String lastName = generateRandomLastName();

        singUpPage.setFirstname(firstName);
        singUpPage.setLastNames(lastName);
        singUpPage.setPhone(generateRandomPhoneNumber());
        singUpPage.setEmail(generateRandomEmail());

        // Wygenerowanie losowego hasła i przypisanie do zmiennej
        String password = generateRandomPassword();
        singUpPage.setPassword(password);
        singUpPage.setConfirmPassword(password);

        // Pobranie wartości haseł
        String passwordValue = singUpPage.getPasswordValue();
        String confirmPasswordValue = singUpPage.getConfirmPasswordValue();
        Assert.assertEquals(passwordValue, confirmPasswordValue, "Passwords do not match!");

        singUpPage.singUpBtnClick();

        //WebElement textH3 = waitForElementToExist(By.xpath("//h3[@class='RTL']"));
        loggedUserPage.waitForRtl();
        String textH3 = loggedUserPage.rtl();
        Assert.assertTrue(textH3.contains("Hi, " + firstName + " " + lastName), "Tekst powitalny nie zawiera poprawnego imienia i nazwiska.");
        Assert.assertEquals(textH3, "Hi, " + firstName + " " + lastName, "jest ok");
    }

    @Test
    public void singUpTest2() {

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        SingUpPage singUpPage = new SingUpPage(driver);
        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);
        hotelSearchPage.openNewAccount();

        singUpPage.filSingUpForm();

        //WebElement textH3 = waitForElementToExist(By.xpath("//h3[@class='RTL']"));
        loggedUserPage.waitForRtl();
        String textH3 = loggedUserPage.rtl();
        Assert.assertTrue(textH3.contains("Hi, "), "Tekst powitalny nie zawiera poprawnego imienia i nazwiska.");
        Assert.assertEquals(textH3, "Hi, ", "jest ok");
    }

    @Test
    public void singUpTest3() {


        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        SingUpPage singUpPage = new SingUpPage(driver);
        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);
        hotelSearchPage.openNewAccount();

        singUpPage.filSingUpForm2();

        //WebElement textH3 = waitForElementToExist(By.xpath("//h3[@class='RTL']"));
        loggedUserPage.waitForRtl();
        String textH3 = loggedUserPage.rtl();
        Assert.assertTrue(textH3.contains("Hi, "), "Tekst powitalny nie zawiera poprawnego imienia i nazwiska.");
        Assert.assertEquals(textH3, "Hi, ", "jest ok");
    }

    @Test
    public void singUpTest4() {


        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        SingUpPage singUpPage = new SingUpPage(driver);
        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);
        hotelSearchPage.openNewAccount();
        String firstName = generateRandomFirstName();
        String lastName = generateRandomLastName();
        String password = generateRandomPassword();
        singUpPage.filSingUpForm3(firstName, lastName, generateRandomPhoneNumber(), generateRandomEmail(), password);

        //WebElement textH3 = waitForElementToExist(By.xpath("//h3[@class='RTL']"));
        loggedUserPage.waitForRtl();
        String textH3 = loggedUserPage.rtl();
        Assert.assertTrue(textH3.contains("Hi, " + firstName + " " + lastName), "Tekst powitalny nie zawiera poprawnego imienia i nazwiska.");
        Assert.assertEquals(textH3, "Hi, " + firstName + " " + lastName, "jest ok");
    }

    @Test
    public void singUpTest5() {


        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        SingUpPage singUpPage = new SingUpPage(driver);
        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);
        User user = new User();

        hotelSearchPage.openNewAccount();
        String firstName = generateRandomFirstName();
        String lastName = generateRandomLastName();
        String password = generateRandomPassword();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(generateRandomPhoneNumber());
        user.setEmail(generateRandomEmail());
        user.setPassword(password);
        singUpPage.filSingUpForm4(user);

        loggedUserPage.waitForRtl();
        String textH3 = loggedUserPage.rtl();
        Assert.assertTrue(textH3.contains("Hi, " + user.getFirstName() + " " + user.getLastName()), "Tekst powitalny nie zawiera poprawnego imienia i nazwiska.");
        Assert.assertEquals(textH3, "Hi, " + user.getFirstName() + " " + user.getLastName(), "jest ok");
    }


    @Test
    public void testRegisterWithoutDate() {

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        SingUpPage singUpPage = new SingUpPage(driver);

        hotelSearchPage.openNewAccount();
        singUpPage.singUpBtnClick();


        singUpPage.waitForResultSignupVisibility();
        // Poczekaj na widoczność elementów błędów
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(d -> !singUpPage.getErrors().isEmpty());  // Czeka aż lista błędów nie będzie pusta

        // Pobierz błędy
        List<String> errors = singUpPage.getErrors();
        System.out.println(errors);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(errors.contains("The Email field is required."));
        softAssert.assertTrue(errors.contains("The Password field is required."));
        softAssert.assertTrue(errors.contains("The Password field is required."));
        softAssert.assertTrue(errors.contains("The First name field is required."));
        softAssert.assertTrue(errors.contains("The Last Name field is required."));
        softAssert.assertAll();
    }

    @Test
    public void emailValidationTest() {
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        SingUpPage singUpPage = new SingUpPage(driver);
        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);
        hotelSearchPage.openNewAccount();

        String firstName = generateRandomFirstName();
        String lastName = generateRandomLastName();

        singUpPage.setFirstname(firstName);
        singUpPage.setLastNames(lastName);
        singUpPage.setPhone(generateRandomPhoneNumber());
        singUpPage.setEmail("errorMail.pl");

        // Wygenerowanie losowego hasła i przypisanie do zmiennej
        String password = generateRandomPassword();
        singUpPage.setPassword(password);
        singUpPage.setConfirmPassword(password);

        // Pobranie wartości haseł
        String passwordValue = singUpPage.getPasswordValue();
        String confirmPasswordValue = singUpPage.getConfirmPasswordValue();
        Assert.assertEquals(passwordValue, confirmPasswordValue, "Passwords do not match!");

        singUpPage.singUpBtnClick();

        singUpPage.waitForResultSignupVisibility();
        // Poczekaj na widoczność elementów błędów
        //  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        //  wait.until(d -> !singUpPage.getErrors().isEmpty());  // Czeka aż lista błędów nie będzie pusta

        // Pobierz błędy
        List<String> errors = singUpPage.getErrors();
        System.out.println(errors);

        for (String errorMessage : errors) {
            Assert.assertTrue(errorMessage != null && !errorMessage.isEmpty(), "Komunikat błędu jest pusty.");
        }
        errors.forEach(message -> System.out.println("Komunikat błędu: " + message));
        errors.forEach(message -> {
            // Przykład: Sprawdzenie, czy komunikat zawiera określony tekst
            Assert.assertTrue(message.contains("The Email field must contain a valid email address."), "Komunikat błędu nie zawiera oczekiwanego tekstu: " + message);
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
