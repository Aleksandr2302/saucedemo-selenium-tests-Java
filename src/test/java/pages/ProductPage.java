package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.support.ui.Select;

import java.util.Random;
import java.util.stream.Collectors;

public class ProductPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private By titlePageName = By.className("app_logo");


    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.loginPage = new LoginPage(driver); // передаём драйвер
    }

    // 🔹 Product information
    public static class Product {
        public String name;
        public String description;
        public String price;
        public WebElement button;

        public Product(String name, String description, String price, WebElement button) {
            this.name = name;
            this.description = description;
            this.price = price;
            this.button = button;
        }

    }

    // Select random product
    public Product getRandomProduct() {
        Random random = new Random();
        List<WebElement> items = driver.findElements(By.cssSelector(".inventory_item"));

        WebElement randomItem = items.get(random.nextInt(items.size()));

        String name = randomItem.findElement(By.cssSelector(".inventory_item_name")).getText();
        String description = randomItem.findElement(By.cssSelector(".inventory_item_desc")).getText();
        String price = randomItem.findElement(By.cssSelector(".inventory_item_price")).getText();
        WebElement button = randomItem.findElement(By.cssSelector("button.btn_inventory"));


        return new Product(name, description, price, button);
    }

    public void addToCartButtonClick(Product product) {
        WebElement productContainer = driver.findElement(
                By.xpath("//div[contains(@class,'inventory_item')][.//div[contains(@class,'inventory_item_name') and normalize-space(text())='" + product.name + "']]")
        );
        WebElement addButton = productContainer.findElement(By.cssSelector("button.btn_inventory"));
        addButton.click();
    }

    public WebElement getProductButton(Product product) {
        return driver.findElement(By.xpath(
                "//div[contains(@class,'inventory_item')][.//div[contains(@class,'inventory_item_name') and normalize-space(text())='" + product.name + "']]//button[contains(@class,'btn_inventory')]"
        ));
    }

    // Check title page
    public boolean titleProductCheck() {
        WebElement title = driver.findElement(titlePageName);
        return title.isDisplayed() && title.getText().equals("Swag Labs");
    }

    // Filer verification
    public void applyFilter(String filterValue) {
        Select sortDropdown = new Select(driver.findElement(By.className("product_sort_container")));
        sortDropdown.selectByValue(filterValue);
    }

    // Getting product name
    public List<String> getAllProductNames() {
        List<WebElement> products = driver.findElements(By.className("inventory_item_name"));
        return products.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    // Getting product price
    public List<Double> getAllProductPrices() {
        List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
        return prices.stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
    }
}