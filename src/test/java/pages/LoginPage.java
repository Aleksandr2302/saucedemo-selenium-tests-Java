package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.openqa.selenium.WebElement;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By usernameField = By.id("user-name");  // селектор поля логина
    private By passwordField = By.id("password");  // селектор поля пароля
    private By loginButton = By.id("login-button");       // селектор кнопки логина
    private By productsTitle = By.cssSelector("span[data-test='title']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // ждём до 5 сек
    }

    // Method for logging(username)
    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    // Method for logging(password)
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    // Method for logging(click button)
    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    // Overall method for logging
    public void loginAs(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public boolean isProductsTitleVisible() {
        WebElement title = driver.findElement(productsTitle);
        return title.isDisplayed() && title.getText().equals("Products");
    }
}
