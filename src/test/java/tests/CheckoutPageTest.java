package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.BusketPage;
import pages.LoginPage;
import pages.CheckoutPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.ProductPage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CheckoutPageTest {
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
    public void CheckoutBtnCheck() {
        ProductPage productPage = new ProductPage(driver);
        ProductPage.Product randomProduct = productPage.getRandomProduct();

        // Product name,price,description verification
        productPage.addToCartButtonClick(randomProduct);
        BusketPage busketPage = new BusketPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        busketPage.clickShoppingCart();
        busketPage.clickCheckoutBtn();

        assertEquals("Checkout: Your Information", checkoutPage.getHeaderText());
    }

    @Test
    public void CheckoutPlaceHolderCheck() {
        ProductPage productPage = new ProductPage(driver);
        ProductPage.Product randomProduct = productPage.getRandomProduct();

        // Product name,price,description verification
        productPage.addToCartButtonClick(randomProduct);
        BusketPage busketPage = new BusketPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        busketPage.clickShoppingCart();
        busketPage.clickCheckoutBtn();

        assertEquals("Checkout: Your Information", checkoutPage.getHeaderText());

        String firstNamePlaceHolder = checkoutPage.getInputPlaceholder("firstName");
        String lastNamePlaceHolder = checkoutPage.getInputPlaceholder("lastName");
        String zipCodePlaceHolder = checkoutPage.getInputPlaceholder("postalCode");

        // Проверяем, что placeholder соответствует ожиданиям
        assertEquals("First Name", firstNamePlaceHolder);
        assertEquals("Last Name", lastNamePlaceHolder);
        assertEquals("Zip/Postal Code", zipCodePlaceHolder);
    }

    @Test
    public void CheckoutAllElementsCheck() {
        ProductPage productPage = new ProductPage(driver);
        ProductPage.Product randomProduct = productPage.getRandomProduct();

        // Product name,price,description verification
        productPage.addToCartButtonClick(randomProduct);
        BusketPage busketPage = new BusketPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        busketPage.clickShoppingCart();
        busketPage.clickCheckoutBtn();

        assertEquals("Checkout: Your Information", checkoutPage.getHeaderText());

        String firstNamePlaceHolder = checkoutPage.getInputPlaceholder("firstName");
        String lastNamePlaceHolder = checkoutPage.getInputPlaceholder("lastName");
        String zipCodePlaceHolder = checkoutPage.getInputPlaceholder("postalCode");
        WebElement cancelBtn = checkoutPage.getCancelButton();

        assertEquals("First Name", firstNamePlaceHolder);
        assertEquals("Last Name", lastNamePlaceHolder);
        assertEquals("Zip/Postal Code", zipCodePlaceHolder);

        assertTrue(cancelBtn.isDisplayed());
    }

    @Test
    public void CancelButtonCheck() {
        ProductPage productPage = new ProductPage(driver);
        ProductPage.Product randomProduct = productPage.getRandomProduct();

        // Product name,price,description verification
        productPage.addToCartButtonClick(randomProduct);
        BusketPage busketPage = new BusketPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        busketPage.clickShoppingCart();
        busketPage.clickCheckoutBtn();

        assertEquals("Checkout: Your Information", checkoutPage.getHeaderText());

        WebElement cancelBtn = checkoutPage.getCancelButton();
        assertTrue(cancelBtn.isDisplayed());
        cancelBtn.click();

        assertEquals("Your Cart", checkoutPage.getHeaderText());
    }

    @Test
    public void sendFormWithEmptyFields() {
        ProductPage productPage = new ProductPage(driver);
        ProductPage.Product randomProduct = productPage.getRandomProduct();

        // Product name,price,description verification
        productPage.addToCartButtonClick(randomProduct);
        BusketPage busketPage = new BusketPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        busketPage.clickShoppingCart();
        busketPage.clickCheckoutBtn();

        assertEquals("Checkout: Your Information", checkoutPage.getHeaderText());

        checkoutPage.clickContinueBtn();

        assertEquals("Error: First Name is required", checkoutPage.getErrorText());
    }

    @Test
    public void sendFormWithoutOneField() {
        ProductPage productPage = new ProductPage(driver);
        ProductPage.Product randomProduct = productPage.getRandomProduct();

        // Product name,price,description verification
        productPage.addToCartButtonClick(randomProduct);
        BusketPage busketPage = new BusketPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        busketPage.clickShoppingCart();
        busketPage.clickCheckoutBtn();

        assertEquals("Checkout: Your Information", checkoutPage.getHeaderText());

        //Test#1 - FirstName field - empty
        checkoutPage.fillInputField("lastName", "Test1");
        checkoutPage.fillInputField("postalCode", "Test1");
        checkoutPage.clickContinueBtn();

        assertEquals("Error: First Name is required", checkoutPage.getErrorText());

        checkoutPage.clearAllInputs();

        //Test#2 - LastName field - empty
        checkoutPage.fillInputField("firstName", "Test2");
        checkoutPage.fillInputField("postalCode", "Test2");
        checkoutPage.clickContinueBtn();

        assertEquals("Error: Last Name is required", checkoutPage.getErrorText());
        checkoutPage.clearAllInputs();


        //Test#3 - Zip Code field - empty
        checkoutPage.fillInputField("firstName", "Test3");
        checkoutPage.fillInputField("lastName", "Test3");
        checkoutPage.clickContinueBtn();

        assertEquals("Error: Postal Code is required", checkoutPage.getErrorText());
    }

    @Test
    public void checkoutOverviewPageCheck() {
        ProductPage productPage = new ProductPage(driver);
        ProductPage.Product randomProduct = productPage.getRandomProduct();
        System.out.println("Random product selected: " + randomProduct.name);

        // Product name,price,description verification
        productPage.addToCartButtonClick(randomProduct);
        System.out.println("Random product added: " + randomProduct.name);
        BusketPage busketPage = new BusketPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        busketPage.clickShoppingCart();
        busketPage.clickCheckoutBtn();

        assertEquals("Checkout: Your Information", checkoutPage.getHeaderText());

        checkoutPage.fillInputField("firstName", "Test1");
        checkoutPage.fillInputField("lastName", "Test1");
        checkoutPage.fillInputField("postalCode", "Test1");
        checkoutPage.clickContinueBtn();

        // Title check
        assertEquals("Checkout: Overview", checkoutPage.getElementByDataTest("title").getText());
        // QTY label check
        assertEquals("QTY", checkoutPage.getElementByDataTest("cart-quantity-label").getText());
        // QTY label check
        assertEquals("Description", checkoutPage.getElementByDataTest("cart-desc-label").getText());
        // Item quantity check
        assertEquals("1", checkoutPage.getElementByDataTest("item-quantity").getText());
        // Product name check
        assertEquals(randomProduct.name, checkoutPage.getElementByDataTest("inventory-item-name").getText());
        // Payment info label
        assertEquals("Payment Information:", checkoutPage.getElementByDataTest("payment-info-label").getText());
        // Payment info value
        assertTrue(checkoutPage.getElementByDataTest("payment-info-value").getText().matches("^SauceCard #\\d+$"));
        // Shipping info label
        assertEquals("Shipping Information:", checkoutPage.getElementByDataTest("shipping-info-label").getText());
        // Shipping info value
        assertEquals("Free Pony Express Delivery!", checkoutPage.getElementByDataTest("shipping-info-value").getText());
        // Price Total label
        assertEquals("Price Total", checkoutPage.getElementByDataTest("total-info-label").getText());
        // Item Total value
        assertTrue(checkoutPage.getElementByDataTest("subtotal-label").getText().matches("^Item total: \\$\\d+(\\.\\d{2})?$"));
        // Tax label
        assertTrue(checkoutPage.getElementByDataTest("tax-label").getText().matches("^Tax: \\$\\d+(\\.\\d{2})?$"));
        // Total label
        assertTrue(checkoutPage.getElementByDataTest("total-label").getText().matches("^Total: \\$\\d+(\\.\\d{2})?$"));
    }

    @Test
    public void finishPageCheck() {
        ProductPage productPage = new ProductPage(driver);
        ProductPage.Product randomProduct = productPage.getRandomProduct();
        System.out.println("Random product selected: " + randomProduct.name);

        // Product name,price,description verification
        productPage.addToCartButtonClick(randomProduct);
        System.out.println("Random product added: " + randomProduct.name);
        BusketPage busketPage = new BusketPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        busketPage.clickShoppingCart();
        busketPage.clickCheckoutBtn();

        assertEquals("Checkout: Your Information", checkoutPage.getHeaderText());

        checkoutPage.fillInputField("firstName", "Test1");
        checkoutPage.fillInputField("lastName", "Test1");
        checkoutPage.fillInputField("postalCode", "Test1");
        checkoutPage.clickContinueBtn();
        checkoutPage.finishBtn();

        // Title check
        assertEquals("Checkout: Complete!", checkoutPage.getElementByDataTest("secondary-header").getText());
        // Checkout complete title check
        assertEquals("Thank you for your order!", checkoutPage.getElementByDataTest("complete-header").getText());
        // Checkout complete text check
        assertEquals("Your order has been dispatched, and will arrive just as fast as the pony can get there!", checkoutPage.getElementByDataTest("complete-text").getText());
        // Back home button check
        assertEquals("Back Home", checkoutPage.getElementByDataTest("back-to-products").getText());
    }

    @Test
    public void backHomeButtonCheck() {
        ProductPage productPage = new ProductPage(driver);
        ProductPage.Product randomProduct = productPage.getRandomProduct();
        System.out.println("Random product selected: " + randomProduct.name);

        // Product name,price,description verification
        productPage.addToCartButtonClick(randomProduct);
        System.out.println("Random product added: " + randomProduct.name);
        BusketPage busketPage = new BusketPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        busketPage.clickShoppingCart();
        busketPage.clickCheckoutBtn();

        assertEquals("Checkout: Your Information", checkoutPage.getHeaderText());

        checkoutPage.fillInputField("firstName", "Test1");
        checkoutPage.fillInputField("lastName", "Test1");
        checkoutPage.fillInputField("postalCode", "Test1");
        checkoutPage.clickContinueBtn();
        checkoutPage.finishBtn();

        // Title checkout check
        assertEquals("Checkout: Complete!", checkoutPage.getElementByDataTest("secondary-header").getText());

        checkoutPage.backHomeBtn();

        // Title products check
        assertEquals("Products", checkoutPage.getElementByDataTest("title").getText());

    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
