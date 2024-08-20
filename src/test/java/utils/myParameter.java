package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

public interface myParameter {
    String URL_KURS_SELENIUM_DEMO = "http://www.kurs-selenium.pl/demo/";

    default void highLightElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red;');", element);
    }

    default void highLightElement(WebDriver driver, List<WebElement> elements) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        List<String> colors = Arrays.asList("blue", "red", "green", "yellow", "purple");
        int colorIndex = 0;

        for (WebElement element : elements) {
            String color = colors.get(colorIndex % colors.size());
            js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid " + color + ";');", element);
            colorIndex++;
        }
    }

    default void discoverHidden(WebDriver driver, WebElement cssClass) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].removeAttribute('hidden')", cssClass);
    }
}
