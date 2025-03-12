/*
напиши мне автоматизированный тест для https://www.morozko-shop.ru/ используя Selenide, Junit, AssertJ, gradle для запуска на браузер Chrome версии 133. Укажи актуальные версии библиотек.
 */
/*
Добавь тест поиска товара
 */
/*
получаю ошибку Element not found {#search}, поправь локатор
 */
/*
данный элемент тоже не находит: Element not found {.header-search-form__input}
 */
/*
Добавь еще один тест по поиску товара и пусть в новом тесте проверяем, что у всех результатов в заголовке есть текст поиска
 */
/*
сделай проверку включения текста регистронезависимой
 */
/*
давай для проверки наполненности корзины заменим текущую проверку такой:
перейти в корзину и убедиться, что в корзина не пуста
 */
/*
Товар не успевает добавиться в корзину. Можем ли мы добавить небольшое ожидание после клика добавления в корзину?
 */
/*
если сразу после добавления обновить страницу, то не успевает произойти добавление и товар после обновления не добавится?
 */
/*
Давай все же добавим до проверки атрибута value ожидание Thread sleep пару секунд и перезагрузку страницы после этого
 */
/*
На основе тестов searchForProduct и addItemToCart напиши тест поиска и добавления в корзину товара 'Фонтан сэтору'
 */
/*
Давай вынесем конфигурации Selenide в отдельный файл utils
 */
/*
startMaximized подсвечен в ide красным
 */
/*
замени WebDriverManager на ChromeDriver
 */
/*
добавь процедуру закрытия браузера после теста
 */


/*
Исправь ошибку локатора ... - ОЧЕНЬ МНОГО РАЗ ПИСАЛ, СИЛЬНО ГЛЮЧИТ В НАХОЖДЕНИИ ЛОКАТОРОВ((
 */

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
        SelenideConfig.setup(); // Вызов метода настройки Selenide

        // Открываем сайт
        open("https://www.morozko-shop.ru/");
    }

    @AfterEach
    public void tearDown() {
        closeWindow(); // Закрывает текущее окно браузера
    }

    @Test
    public void checkTitle() {

        // Проверяем заголовок страницы
        String title = title();
        assertThat(title).isEqualTo("Купить новогодние товары в Москве в интернет-магазине Morozko-shop.ru");
    }

    @Test
    public void searchForProduct() {

        // Находим элемент строки поиска и вводим текст
        $("[name='search_words']").setValue("Комбинезон").pressEnter();

        // Ждем появления результата поиска
        $(".goodname_title").shouldHave(text("Комбинезон"));

        // Проверяем, что найден хотя бы один товар
        int productCount =

                $$(".goods_item_card_4").size();
        assertThat(productCount).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void searchAndCheckAllProductsTitles() {

        // Находим элемент строки поиска по имени атрибута и вводим текст
        $("[name='search_words']").setValue("Гирлянда").pressEnter();

        // Получаем список всех заголовков товаров
        List<String> productTitles =

                $$(".goodname_title")
                        .texts(); // Получаем тексты всех элементов с классом product-item-title

        // Проверяем, что каждый заголовок содержит текст "Гирлянда" (регистронезависимо)
        for (String title : productTitles) {
            assertThat(title.toLowerCase()).contains("гирлянда");
        }
    }

    @Test
    public void addItemToCart() throws InterruptedException {

        // Переходим на первую доступную категорию акционных товаров и переходим на ее страницу

        $(".hide-highlight").click();

        // Находим продуктовую директорию и переходим в нее

        $(".dir-cart-container").click();

        // Находим первый доступный товар и переходим на его страницу

        $$(".in_basket_label_acceptor").get(0).click();

        // Нажимаем на кнопку "Добавить в корзину"
        $(".buy-button-block").click();

        // Жесткое ожидание на 2 секунды
        Thread.sleep(2000);

        // Переходим в корзину
        $(".bask-et").click();

        // Проверяем, что корзина не пуста
        $(".basket_item").shouldBe(visible);
    }

    @Test
    public void addSpecificItemToCartViaSearch() throws InterruptedException {

        // Находим элемент строки поиска по имени атрибута и вводим текст
        $("[name='search_words']").setValue("фонтан сэтору").pressEnter();

        // Ждем появления результата поиска
        $(".goodname_title").shouldHave(text("фонтан сэтору"));

        // Переходим на страницу товара 'фонтан сэтроу'

        $(".goods_item_card_4").click();

        // Нажимаем на кнопку "Добавить в корзину"
        $(".buy-button-block").click();

        // Жесткое ожидание на 2 секунды
        Thread.sleep(2000);

        // Переходим в корзину
        $(".bask-et").click();

        // Проверяем, что корзина не пуста
        $(".basket_item").shouldBe(visible);
    }
}