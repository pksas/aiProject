/*
Есть тесты:
public class MorozkoTest {
... код класса тестов
}
Перепиши эти тесты с использованием патерна PageObject
 */
/*
Предложи структуру классов и внеси изменения в код
 */
/*
вынеси во всех pageObject локаторы вверх, а методы вниз
 */

package pages;

import static com.codeborne.selenide.Selenide.$;

public class CartPage {

    // Локаторы
    private static final String BASKET_ITEM_LOCATOR = ".basket_item";
    private static final String CART_LINK_LOCATOR = ".bask-et";

    // Методы
    public void goToCart() {
        $(CART_LINK_LOCATOR).click();
    }

    public boolean isCartNotEmpty() {
        return $(BASKET_ITEM_LOCATOR).isDisplayed();
    }
}