package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import pages.CheckoutPage;
import pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.ProductPage;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import org.openqa.selenium.chrome.ChromeOptions;

public class ProductPageTest {

    private static WebDriver driver;  // 🔹 теперь static

    private static void loginAsStandardUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("standard_user", "secret_sauce");
    }

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        String dir = System.getProperty("java.io.tmpdir") + "/chrome-" + System.currentTimeMillis();
        options.addArguments("user-data-dir=" + dir);

        // Run without GUI for  CI
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        loginAsStandardUser();
    }

    @Test
    public void titlePageCheck() {
        ProductPage productPage = new ProductPage(driver);
        assertTrue(productPage.titleProductCheck());
    }

    @Test
    public void randomProductElementsCheck() {
        ProductPage productPage = new ProductPage(driver);
        ProductPage.Product randomProduct = productPage.getRandomProduct();

        System.out.println("Название: " + randomProduct.name);
        System.out.println("Описание: " + randomProduct.description);
        System.out.println("Цена: " + randomProduct.price);

        assertTrue(randomProduct.name.length() > 0);
        assertTrue(randomProduct.description.length() > 0);
        assertTrue(randomProduct.price.startsWith("$"));
    }

    @Test
    public void addToCartButtonCheck() {
        ProductPage productPage = new ProductPage(driver);
        ProductPage.Product randomProduct = productPage.getRandomProduct();

        System.out.println("Название: " + randomProduct.name);
        System.out.println("Описание: " + randomProduct.description);
        System.out.println("Цена: " + randomProduct.price);
        WebElement addButton = productPage.getProductButton(randomProduct);

        // Product name,price,description verification
        assertTrue(randomProduct.name.length() > 0);
        assertTrue(randomProduct.description.length() > 0);
        assertTrue(randomProduct.price.startsWith("$"));
        assertEquals("Add to cart", addButton.getText());

    }

    @Test
    public void removedButtonCheck() {
        ProductPage productPage = new ProductPage(driver);
        ProductPage.Product randomProduct = productPage.getRandomProduct();

        System.out.println("Название: " + randomProduct.name);
        System.out.println("Описание: " + randomProduct.description);
        System.out.println("Цена: " + randomProduct.price);

        productPage.addToCartButtonClick(randomProduct);
        WebElement removeButton = productPage.getProductButton(randomProduct);
        // Product name,price,description verification
        assertTrue(randomProduct.name.length() > 0);
        assertTrue(randomProduct.description.length() > 0);
        assertTrue(randomProduct.price.startsWith("$"));
        assertEquals("Remove", removeButton.getText());

    }

    @Test
    public void fromRemovedToAddButtonCheck() {
        ProductPage productPage = new ProductPage(driver);
        ProductPage.Product randomProduct = productPage.getRandomProduct();
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        System.out.println("Название: " + randomProduct.name);
        System.out.println("Описание: " + randomProduct.description);
        System.out.println("Цена: " + randomProduct.price);

        productPage.addToCartButtonClick(randomProduct);
        WebElement removeButton = productPage.getProductButton(randomProduct);
        // Product name,price,description verification
        assertTrue(randomProduct.name.length() > 0);
        assertTrue(randomProduct.name.length() > 0);
        assertTrue(randomProduct.description.length() > 0);
        assertTrue(randomProduct.price.startsWith("$"));
        assertEquals("Remove", removeButton.getText());

        productPage.addToCartButtonClick(randomProduct);
        WebElement addButton = productPage.getProductButton(randomProduct);

        assertEquals("Add to cart", addButton.getText());
    }

    @Test
    public void testAllFilters() {
        ProductPage page = new ProductPage(driver);

        // 1️⃣ Name A-Z
        page.applyFilter("az");
        List<String> names = page.getAllProductNames();
        List<String> sortedNames = new ArrayList<>(names);
        Collections.sort(sortedNames); // сортируем по возрастанию
        assertEquals(sortedNames, names);

        // 2️⃣ Name Z-A
        page.applyFilter("za");
        names = page.getAllProductNames();
        sortedNames = new ArrayList<>(names);
        Collections.sort(sortedNames, Collections.reverseOrder()); // сортировка по убыванию
        assertEquals(sortedNames, names);

        // 3️⃣ Price low to high
        page.applyFilter("lohi");
        List<Double> prices = page.getAllProductPrices();
        List<Double> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices); // цены по возрастанию
        assertEquals(sortedPrices, prices);

        // 4️⃣ Price high to low
        page.applyFilter("hilo");
        prices = page.getAllProductPrices();
        sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices, Collections.reverseOrder()); // цены по убыванию
        assertEquals(sortedPrices, prices);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}