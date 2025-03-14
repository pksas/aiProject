package pages;

import static com.codeborne.selenide.Selenide.$;

public class CartPage {

    private static final String BASKET_ITEM_LOCATOR = ".basket_item";
    private static final String CART_LINK_LOCATOR = ".bask-et";

    public void goToCart() {
        $(CART_LINK_LOCATOR).click();
    }

    public boolean isCartNotEmpty() {
        return $(BASKET_ITEM_LOCATOR).isDisplayed();
    }
}