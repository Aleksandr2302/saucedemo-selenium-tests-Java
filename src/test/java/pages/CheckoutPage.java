package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import java.util.List;


public class CheckoutPage {
    private WebDriver driver;

    public CheckoutPage(WebDriver driver) {

        this.driver = driver;
    }

    // Getting title text
    public String getHeaderText() {
        WebElement header = driver.findElement(By.cssSelector("span[data-test='title']"));
        return header.getText();
    }

    public WebElement getCancelButton() {
        return driver.findElement(By.cssSelector(".cart_cancel_link"));
    }


    // 🔹 Getting placeholder
    public String getInputPlaceholder(String fieldDataTest) {
        return driver.findElement(By.cssSelector("input[data-test='" + fieldDataTest + "']")).getAttribute("placeholder");
    }

    // 🔹 Getting webelement with input
    public void fillInputField(String fieldDataTest, String value) {
        WebElement input = driver.findElement(By.cssSelector("input[data-test='" + fieldDataTest + "']"));
        String placeholder = input.getAttribute("placeholder");
        System.out.println("Placeholder: " + placeholder);

        input.clear();          // clean
        input.sendKeys(value);  // input text
    }

    public String getErrorText() {
        WebElement header = driver.findElement(By.cssSelector("[data-test='error']"));
        return header.getText();
    }

    public void clickContinueBtn() {
        WebElement continueBtn = driver.findElement(By.className("submit-button"));
        continueBtn.click();
    }

    public void finishBtn() {
        WebElement finishBtn = driver.findElement(By.id("finish"));
        finishBtn.click();
    }

    public void backHomeBtn() {
        WebElement backHomeBtn = driver.findElement(By.id("back-to-products"));
        backHomeBtn.click();
    }

    public void clearAllInputs() {
        List<WebElement> inputs = driver.findElements(By.cssSelector(".checkout_info input"));

        for (WebElement input : inputs) {
            String value = input.getAttribute("value");
            if (value != null && !value.isEmpty()) {
                // удаляем символ за символом
                for (int i = 0; i < value.length(); i++) {
                    input.sendKeys("\b");
                }
            }
        }
    }

    public WebElement getElementByDataTest(String dataTest) {
        return driver.findElement(By.cssSelector("[data-test='" + dataTest + "']"));
    }

}
