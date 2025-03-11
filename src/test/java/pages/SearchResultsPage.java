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

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$;

public class SearchResultsPage {

    // Локаторы
    private static final String PRODUCT_TITLE_LOCATOR = ".goodname_title";
    private static final String GOODS_ITEM_CARD_LOCATOR = ".goods_item_card_4";

    // Методы
    public boolean isProductFoundByName(String name) {
        return

                $$(PRODUCT_TITLE_LOCATOR).find(text(name)).exists();
    }

    public List<String> getProductTitles() {
        return

                $$(PRODUCT_TITLE_LOCATOR).texts();
    }

    public ProductDetailsPage selectFirstProduct() {

        $$(GOODS_ITEM_CARD_LOCATOR).first().click();
        return new ProductDetailsPage();
    }
}