/*
пример сайта с openApi без регестрации
 */
/*
напиши пару тестов RestAssured и Lombok, по автоматизации тестирования
https://api.nasa.gov/
 */
/*
RestAssured config вывод логов в консоль
 */
/*
добавь тест с запросом /neo/rest/v1/feed
 */
/*
как отображать тела запросов и ответов в allure report?
 */
/*
а можно ли с помощью allure restassured lib сделать?
 */
/*
Отлично! Давай теперь сделаем аннотации и переведем на step следующие api тесты:
... код api тестов ...
 */

package api.tests;

import api.models.ApodResponse;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Feature("My Project API NASA")
@Tag("apiTest")
public class ApodTest {

    private static final String key = "aBCxdXKfvWwUkBAHE066xvDMYw0WjaQcIj3Y0jZ4";

    @BeforeAll
    public static void setup() {
        // Настройка базовой части URL для всех последующих запросов
        RestAssured.baseURI = "https://api.nasa.gov";

        // Настройка фильтров для логирования запросов и ответов
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

    }

    @Test
    @DisplayName("Проверка успешного ответа от API (Status Code 200)")
    @Story("Проверка статуса ответа")
    @Tag("api-test")
    public void testSuccessfulResponse() {
        RestAssured.basePath = "/planetary/apod";
        step("Отправляем GET-запрос на endpoint /planetary/apod", () -> {
            given()
                    .filter(new AllureRestAssured())
                    .param("api_key", key) // Используйте ваш ключ API или DEMO_KEY для тестов
                    .when()
                    .get()
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .and()
                    .contentType(ContentType.JSON);
        });
    }

    @Test
    @DisplayName("Проверка обязательных полей в ответе")
    @Story("Проверка структуры ответа")
    @Tag("api-test")
    public void testFieldsInResponse() {
        RestAssured.basePath = "/planetary/apod";
        step("Отправляем GET-запрос на endpoint /planetary/apod", () -> {
            given()
                    .filter(new AllureRestAssured())
                    .param("api_key", key)
                    .when()
                    .get()
                    .then()
                    .assertThat()
//                    .body("copyright", notNullValue())
                    .body("media_type", equalToIgnoringCase("image"))
                    .body("hdurl", startsWith("https"));
        });
    }

    @Test
    @DisplayName("Тестирование десериализации ответа (API)")
    @Story("Десериализация JSON в объект")
    @Tag("api-test")
    public void testDeserializeIntoPOJO() {
        step("Отправляем GET-запрос на endpoint /planetary/apod", () -> {
            ApodResponse response = given()
                    .filter(new AllureRestAssured())
                    .param("api_key", key)
                    .when()
                    .get()
                    .as(ApodResponse.class);

            assertNotNull(response.getDate());
            assertEquals("image", response.getMedia_type().toLowerCase());
            assertTrue(response.getHdurl().startsWith("https"));
        });
    }

    @Test
    @DisplayName("Проверка успешности получения списка объектов NEO")
    @Story("Проверка списка NEO")
    @Tag("api-test")
    public void testNeoFeedResponse() {
        RestAssured.basePath = "/neo/rest/v1/feed";
        step("Отправляем GET-запрос на endpoint /neo/rest/v1/feed", () -> {
            given()
                    .filter(new AllureRestAssured())
                    .param("start_date", "2023-01-01") // Указываем начальную дату для выборки NEOs
                    .param("end_date", "2023-01-07")   // Указываем конечную дату
                    .param("api_key", key)      // Используйте ваш ключ API или DEMO_KEY для тестов
                    .when()
                    .get()
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .and()
                    .contentType(ContentType.JSON)
                    .and()
                    .body("element_count", greaterThanOrEqualTo(1)) // Проверяем, что в ответе хотя бы один элемент
                    .body("near_earth_objects.'2023-01-01'.size()", greaterThan(0)); // Проверяем, что есть объекты на указанную дату
        });
    }
}