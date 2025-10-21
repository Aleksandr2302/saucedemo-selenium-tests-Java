package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import java.util.List;

public class BusketPage {
    private WebDriver driver;

    public BusketPage(WebDriver driver) {
        this.driver = driver;
    }

    public BusketItem getCartItem() {
        String titleName = driver.findElement(By.className("title")).getText();
        String descriptionTitle = driver.findElement(By.className("cart_desc_label")).getText();
        String productName = driver.findElement(By.className("inventory_item_name")).getText();
        String description = driver.findElement(By.className("inventory_item_desc")).getText();
        String price = driver.findElement(By.className("inventory_item_price")).getText();
        String cartCountLabel = driver.findElement(By.className("cart_quantity_label")).getText();
        int cartCount = Integer.parseInt(driver.findElement(By.className("cart_quantity")).getText());
        String continueShoppingText = driver.findElement(By.cssSelector("button.btn_secondary.back.btn_medium")).getText();
        String checkoutText = driver.findElement(By.cssSelector("button.btn_action.btn_medium.checkout_button")).getText();

        return new BusketItem(titleName, productName, descriptionTitle, description, price, cartCountLabel, cartCount, continueShoppingText, checkoutText);
    }

    public class BusketItem {
        public String titleName;
        public String productName;
        public String descriptionTitle;
        public String description;
        public String price;
        public String cartCountLabel;
        public int cartCount;
        public String continueShoppingText;
        public String checkoutText;

        public BusketItem(String titleName, String productName, String descriptionTitle, String description, String price, String cartCountLabel, int cartCount, String continueShoppingText, String checkoutText) {
            this.titleName = titleName;
            this.productName = productName;
            this.descriptionTitle = descriptionTitle;
            this.description = description;
            this.price = price;
            this.cartCountLabel = cartCountLabel;
            this.cartCount = cartCount;
            this.continueShoppingText = continueShoppingText;
            this.checkoutText = checkoutText;
        }
    }

    public int getItemCount() {
        List<WebElement> cartBadge = driver.findElements(By.className("shopping_cart_badge"));
        if (cartBadge.isEmpty()) {
            return 0; // корзина пустая
        }
        return Integer.parseInt(cartBadge.get(0).getText());
    }

    public void clickShoppingCart() {
        WebElement cartLink = driver.findElement(By.className("shopping_cart_link"));
        cartLink.click();
    }

    public void clickCheckoutBtn() {
        WebElement checkoutBtn = driver.findElement(By.className("checkout_button"));
        checkoutBtn.click();
    }

}
