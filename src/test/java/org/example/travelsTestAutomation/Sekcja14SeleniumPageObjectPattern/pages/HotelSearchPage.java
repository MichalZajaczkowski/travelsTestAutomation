package org.example.travelsTestAutomation.Sekcja14SeleniumPageObjectPattern.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BaseTestMethod;
import utils.myParameter;

import java.time.Duration;
import java.util.List;

public class HotelSearchPage extends BaseTestMethod implements myParameter {

    protected WebDriverWait wait;
    private WebDriver driver;

    @FindBy(xpath = "//span[text()='Search by Hotel or City Name']")
    private WebElement searchHotelSpan;

    @FindBy(xpath = "//div[@id='select2-drop']//input")
    private WebElement searchHotelInput;

    @FindBy(name = "checkin")
    private WebElement checkinInput;

    @FindBy(name = "checkout")
    private WebElement checkOutInput;

    @FindBy(id = "travellersInput")
    private WebElement travellersInput;

    @FindBy(id = "adultMinusBtn")
    private WebElement adultMinusBtn;

    @FindBy(id = "adultPlusBtn")
    private WebElement adultPlusBtn;

    @FindBy(id = "childMinusBtn")
    private WebElement childMinusBtn;

    @FindBy(id = "childPlusBtn")
    private WebElement childPlusBtn;

    @FindBy(xpath = "(//button[text()=' Search'])")
    private WebElement searchButton;

    @FindBy(xpath = "//li[@id='li_myaccount']")
    private List<WebElement> myAccountLink;

    @FindBy(xpath = "//a[text()='  Sign Up']")
    private List<WebElement> signUpLink;

    public HotelSearchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.driver = driver;
    }

    public HotelSearchPage setCity(String cityName) {
        wait.until(ExpectedConditions.elementToBeClickable(searchHotelSpan)).click();
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOf(searchHotelInput));
        searchInput.sendKeys(cityName);
        String xpathHotelMatch = String.format("//span[@class='select2-match' and text()='%s']", cityName);
        // Oczekiwanie na pojawienie się wyniku wyszukiwania
        By hotelMatchLocator = By.xpath(xpathHotelMatch);
        wait.until(ExpectedConditions.presenceOfElementLocated(hotelMatchLocator));
        wait.until(ExpectedConditions.elementToBeClickable(hotelMatchLocator)).click();
        return this;
    }

    public HotelSearchPage setDate(String checkIn, String checkOut) {
        wait.until(ExpectedConditions.elementToBeClickable(checkinInput)).sendKeys(checkIn);
        wait.until(ExpectedConditions.elementToBeClickable(checkOutInput)).sendKeys(checkOut);
        return this;
    }

    public HotelSearchPage setTravellers(Integer adultsToChange, Integer childrenToChange) {
        travellersInput.click();
        addTraveler(adultPlusBtn, adultMinusBtn, adultsToChange);// Obsługa dorosłych
        addTraveler(childPlusBtn, childMinusBtn, childrenToChange); // Obsługa dzieci

        return this;
    }

    private void addTraveler(WebElement plusBtn, WebElement minusBtn, Integer numberOfTravelers) {
        if (numberOfTravelers != null && numberOfTravelers != 0) {
            WebElement button = (numberOfTravelers > 0) ? plusBtn : minusBtn;
            for (int i = 0; i < Math.abs(numberOfTravelers); i++) {
                button.click();
            }
        }
    }

    public ResultsHotelPage performSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        return new ResultsHotelPage(driver);
    }

    public SingUpPage openNewAccount() {
        myAccountLink.stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);
        signUpLink.get(1).click();
        return new SingUpPage(driver);
    }
}
