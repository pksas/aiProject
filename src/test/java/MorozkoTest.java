import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.SelenideConfig;

import java.net.MalformedURLException;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("disable")
public class MorozkoTest {
    @BeforeEach
    public void setUp() throws MalformedURLException {
        SelenideConfig.setup();
        open("https://www.morozko-shop.ru/");
    }

    @AfterEach
    public void tearDown() {
        closeWindow();
    }

    @Test
    public void checkTitle() {
        String title = title();
        assertThat(title).isEqualTo("Купить новогодние товары в Москве в интернет-магазине Morozko-shop.ru");
    }

    @Test
    public void searchForProduct() {
        $("[name='search_words']").setValue("Комбинезон").pressEnter();
        $(".goodname_title").shouldHave(text("Комбинезон"));
        int productCount =
                $$(".goods_item_card_4").size();
        assertThat(productCount).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void searchAndCheckAllProductsTitles() {
        $("[name='search_words']").setValue("Гирлянда").pressEnter();
        List<String> productTitles =
                $$(".goodname_title")
                        .texts();
        for (String title : productTitles) {
            assertThat(title.toLowerCase()).contains("гирлянда");
        }
    }

    @Test
    public void addItemToCart() throws InterruptedException {
        $(".hide-highlight").click();
        $(".dir-cart-container").click();
        $$(".in_basket_label_acceptor").get(0).click();
        $(".buy-button-block").click();
        Thread.sleep(2000);
        $(".bask-et").click();
        $(".basket_item").shouldBe(visible);
    }

    @Test
    public void addSpecificItemToCartViaSearch() throws InterruptedException {
        $("[name='search_words']").setValue("фонтан сэтору").pressEnter();
        $(".goodname_title").shouldHave(text("фонтан сэтору"));
        $(".goods_item_card_4").click();
        $(".buy-button-block").click();
        Thread.sleep(2000);
        $(".bask-et").click();
        $(".basket_item").shouldBe(visible);
    }
}