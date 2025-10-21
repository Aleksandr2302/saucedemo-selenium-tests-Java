package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import pages.BusketPage;
import pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.ProductPage;
import org.openqa.selenium.chrome.ChromeOptions;

public class BusketPageTest {
    private static WebDriver driver;
    private static void loginAsStandardUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("standard_user", "secret_sauce");
    }

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        loginAsStandardUser();
    }

    @Test
    public void BusketCountCheck() {
        ProductPage productPage = new ProductPage(driver);
        ProductPage.Product randomProduct = productPage.getRandomProduct();

        System.out.println("Название: " + randomProduct.name);
        System.out.println("Описание: " + randomProduct.description);
        System.out.println("Цена: " + randomProduct.price);

        // Product name,price,description verification
        productPage.addToCartButtonClick(randomProduct);
        BusketPage busketPage = new BusketPage(driver);
        assertEquals(1, busketPage.getItemCount());
    }

    @Test
    public void BusketPageElementsCheck() {
        ProductPage productPage = new ProductPage(driver);
        ProductPage.Product randomProduct = productPage.getRandomProduct();

        System.out.println("Название: " + randomProduct.name);
        System.out.println("Описание: " + randomProduct.description);
        System.out.println("Цена: " + randomProduct.price);

        // Product name,price,description verification
        productPage.addToCartButtonClick(randomProduct);
        BusketPage busketPage = new BusketPage(driver);
        assertEquals(1, busketPage.getItemCount());

        busketPage.clickShoppingCart();
        BusketPage.BusketItem item = busketPage.getCartItem();


        assertEquals("Your Cart", item.titleName);
        assertTrue(item.productName.length() > 0);
        assertEquals("Description", item.descriptionTitle);
        assertTrue(item.description.length() > 0);
        assertTrue(item.price.length() > 0);
        assertEquals("QTY", item.cartCountLabel);
        assertEquals(1, item.cartCount);
        assertTrue(item.price.startsWith("$"));
        assertEquals("Continue Shopping", item.continueShoppingText);
        assertEquals("Checkout", item.checkoutText);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
