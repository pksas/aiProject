package pages;

import static com.codeborne.selenide.Selenide.$;

public class ProductDetailsPage {

    private static final String BUY_BUTTON_BLOCK_LOCATOR = ".buy-button-block";

    public CartPage addToCart() {
        $(BUY_BUTTON_BLOCK_LOCATOR).click();
        return new CartPage();
    }
}