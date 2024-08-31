package org.example.travelsTestAutomation.Sekcja14SeleniumPageObjectPattern.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.BaseTestMethod;
import utils.myParameter;

public class HotelDetailsPage extends BaseTestMethod implements myParameter {

    @FindBy(css = "div.mob-trip-info-head .ellipsis.ttu span")
    private WebElement hotelDetails;

    public HotelDetailsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String chooseHotelDetails() {
        return hotelDetails.getText();
    }
}
