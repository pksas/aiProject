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

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.*;

public class HomePage {

    private final String url = "https://www.morozko-shop.ru/";

    // Локаторы
    private static final String SEARCH_INPUT_LOCATOR = "[name='search_words']";
    private static final String HIDE_HIGHLIGHT_LOCATOR = ".hide-highlight";
    private static final String DIR_CART_CONTAINER_LOCATOR = ".dir-cart-container";
    private static final String IN_BASKET_LABEL_ACCEPTOR_LOCATOR = ".in_basket_label_acceptor";

    // Методы
    public HomePage open() {
        Selenide.open(url);
        return this;
    }

    public String getTitle() {
        return title();
    }

    public SearchResultsPage searchFor(String query) {
        $(SEARCH_INPUT_LOCATOR).setValue(query).pressEnter();
        return new SearchResultsPage();
    }

    public ProductDetailsPage navigateToFirstCategory() {
        $(HIDE_HIGHLIGHT_LOCATOR).click();
        $(DIR_CART_CONTAINER_LOCATOR).click();
        $(IN_BASKET_LABEL_ACCEPTOR_LOCATOR).click();

        return new ProductDetailsPage();
    }
}