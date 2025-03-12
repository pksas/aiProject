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
Ты тестировщик. Задача:
написать набор тест кейсов для https://www.morozko-shop.ru/
Тест кейсы написать в формате ручных тестов из кода java для AllureTestOps. Пример:
 @Test
 @DisplayName("Form data can be edited (Manual)")
 @Story("Manual form operations")
 @Tag("manual")
 void editFormDataManualTest() {
 step("Open page /text-box");
 step("Edit form data");
 step("Check results");
 }
 */

/*
Перепиши уже готовые автоматизированные тесты (добавь аннотации DisplayName, Story, Tag и шаги Allure step). Вот эти тесты:
...
код тестов:
...
делай по такому примеру:
    @Test
    @DisplayName("Error message is displayed on incomplete form submission")
    @Story("Text box form operations")
    @Tag("automated")
    void incompleteFormSubmissionTest() {
        step("Open page /text-box", () -> {
            open("/text-box");
 */

package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import pages.CartPage;
import pages.HomePage;
import pages.ProductDetailsPage;
import pages.SearchResultsPage;
import utils.SelenideConfig;

import java.util.List;

import static com.codeborne.selenide.Selenide.closeWindow;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Feature("My Project UI Morozko")
@Tag("uiTest")
public class MorozkoTest {

    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private ProductDetailsPage productDetailsPage;
    private CartPage cartPage;

    @BeforeEach
    public void setUp() {
        SelenideConfig.setup();
        step("Открытие главной страницы", () -> {
            homePage = new HomePage().open();
        });
    }

    @AfterEach
    public void tearDown() {
        closeWindow();
    }

    @Test
    @DisplayName("Проверка заголовка главной страницы (Автоматизированный)")
    @Story("Заголовок страницы")
    @Tag("automated")
    public void checkTitle() {
        // Проверка заголовка главной страницы
        step("Проверка заголовка страницы", () -> {
            assertThat(homePage.getTitle())
                    .isEqualTo("Купить новогодние товары в Москве в интернет-магазине Morozko-shop.ru");
        });
    }

    @Test
    @DisplayName("Поиск продукта и проверка результатов (Автоматизированный)")
    @Story("Функционал поиска")
    @Tag("automated")
    public void searchForProduct() {
        // Поиск продукта и проверка результатов
        step("Поиск товара 'Комбинезон'", () -> {
            searchResultsPage = homePage.searchFor("Комбинезон");
        });

        step("Проверка наличия товара 'Комбинезон' среди результатов", () -> {
            assertThat(searchResultsPage.isProductFoundByName("Комбинезон")).isTrue();
        });


        // Проверка наличия хотя бы одного товара
        step("Проверка количества найденных товаров", () -> {
            int productCount = searchResultsPage.getProductTitles().size();
            assertThat(productCount).isGreaterThanOrEqualTo(1);
        });
    }

    @Test
    @DisplayName("Поиск гирлянд и проверка всех заголовков (Автоматизированный)")
    @Story("Функционал поиска")
    @Tag("automated")
    public void searchAndCheckAllProductsTitles() {
        // Поиск гирлянд и проверка всех заголовков
        step("Поиск товара 'Гирлянда'", () -> {
            searchResultsPage = homePage.searchFor("Гирлянда");
        });

        step("Проверка заголовков всех найденных товаров", () -> {
            List<String> productTitles = searchResultsPage.getProductTitles();

            // Проверка каждого заголовка на наличие текста "Гирлянда"
            for (String title : productTitles) {
                assertThat(title.toLowerCase()).contains("гирлянда");
            }
        });

    }

    @Test
    @DisplayName("Добавление товара в корзину (Автоматизированный)")
    @Story("Корзина покупок")
    @Tag("automated")
    public void addItemToCart() throws InterruptedException {
        // Переход на первую категорию и добавление товара в корзину
        step("Выбор первой категории товаров", () -> {
            productDetailsPage = homePage.navigateToFirstCategory();
        });

        step("Добавление первого товара в корзину", () -> {
            cartPage = productDetailsPage.addToCart();
        });

        // Ожидание загрузки страницы корзины
        Thread.sleep(2000); // Жесткое ожидание, лучше заменить на waitUntilVisible()

        // Переход в корзину и проверка её непустоты
        step("Переход в корзину и проверка, что она не пустая", () -> {
            cartPage.goToCart();
            assertThat(cartPage.isCartNotEmpty()).isTrue();
        });
    }

    @Test
    @DisplayName("Добавление конкретного товара в корзину через поиск (Автоматизированный)")
    @Story("Корзина покупок")
    @Tag("automated")
    public void addSpecificItemToCartViaSearch() throws InterruptedException {
        // Поиск конкретного товара и добавление его в корзину
        step("Поиск товара 'Фонтан Сэтору'", () -> {
            searchResultsPage = homePage.searchFor("фонтан сэтору");
        });

        step("Проверка наличия товара 'Фонтан Сэтору' среди результатов", () -> {
            assertThat(searchResultsPage.isProductFoundByName("фонтан сэтору")).isTrue();
        });

        step("Добавление товара в корзину", () -> {
            productDetailsPage = searchResultsPage.selectFirstProduct();
            cartPage = productDetailsPage.addToCart();
        });

        // Ожидание загрузки страницы корзины
        Thread.sleep(2000); // Лучше использовать ожидания через условия видимости элементов

        // Переход в корзину и проверка её непустоты
        step("Переход в корзину и проверка, что она не пустая", () -> {
            cartPage.goToCart();
            assertThat(cartPage.isCartNotEmpty()).isTrue();
        });
    }

    @Test
    @DisplayName("Registration of a new user (Manual)")
    @Story("User registration")
    @Tag("manual")
    void registerNewUserManualTest() {
        step("Open the main page");
        step("Click on 'Регистрация' button");
        step("Fill in all required fields for registration");
        step("Submit the registration form");
        step("Verify that user is successfully registered");
    }

    @Test
    @DisplayName("Login with existing account (Manual)")
    @Story("User login")
    @Tag("manual")
    void loginExistingAccountManualTest() {
        step("Open the main page");
        step("Click on 'Войти' button");
        step("Enter valid credentials into the login form");
        step("Submit the login form");
        step("Verify that user is logged in successfully");
    }

    @Test
    @DisplayName("Add product to cart (Manual)")
    @Story("Shopping cart functionality")
    @Tag("manual")
    void addProductToCartManualTest() {
        step("Open the main page");
        step("Navigate to any product category");
        step("Select a product from the list");
        step("Click on 'Добавить в корзину' button");
        step("Verify that the product has been added to the shopping cart");
    }

    @Test
    @DisplayName("Place an order (Manual)")
    @Story("Order placement process")
    @Tag("manual")
    void placeOrderManualTest() {
        step("Open the main page");
        step("Log in if not already logged in");
        step("Add at least one product to the shopping cart");
        step("Proceed to checkout");
        step("Fill in shipping details and payment information");
        step("Confirm the order");
        step("Verify that the order has been placed successfully");
    }

    @Test
    @DisplayName("Search for products (Manual)")
    @Story("Search functionality")
    @Tag("manual")
    void searchForProductsManualTest() {
        step("Open the main page");
        step("Type a keyword related to a product in the search bar");
        step("Press Enter or click on the search icon");
        step("Verify that relevant search results are displayed");
    }

    @Test
    @DisplayName("View contact us page (Manual)")
    @Story("Contact us page")
    @Tag("manual")
    void viewContactUsPageManualTest() {
        step("Open the main page");
        step("Click on 'Контакты' link in the footer");
        step("Verify that the contact us page loads correctly");
        step("Verify that contact information such as address, phone number, email is present");
    }
}