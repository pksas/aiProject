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

public class ProductDetailsPage {

    // Локаторы
    private static final String BUY_BUTTON_BLOCK_LOCATOR = ".buy-button-block";

    // Методы
    public CartPage addToCart() {
        $(BUY_BUTTON_BLOCK_LOCATOR).click();
        return new CartPage();
    }
}