package org.example.travelsTestAutomation.Sekcja14SeleniumPageObjectPattern.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.BaseTestMethod;
import utils.myParameter;

import java.util.List;

public class ResultsHotelPage extends BaseTestMethod implements myParameter {

    @FindBy(xpath = "//h4[contains(@class,'list_title')]//b")
    private List<WebElement> hotelList;

    @FindBy(xpath = "//h2[@class='text-center']")
    public WebElement resultHeading;

    public ResultsHotelPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public List<String> getHotelList() {
        return hotelList
                .stream()
                .map(webElement -> webElement.getAttribute("textContent"))
                .toList();
    }

    public List<WebElement> getHotelList1() {
        return hotelList;
    }

    public String getHeadingText() {
        return resultHeading.getText();
    }
}
