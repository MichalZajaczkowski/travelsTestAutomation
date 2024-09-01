package org.example.travelsTestAutomation.Sekcja14SeleniumPageObjectPattern.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoggedUserPage {

    protected WebDriverWait wait;

    public LoggedUserPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @FindBy(xpath="//h3[@class='RTL']")
    public WebElement rtl;

    public String rtl() {
        return rtl.getText();
    }

    public void waitForRtl() {
        wait.until(ExpectedConditions.visibilityOf(rtl));
    }
}
