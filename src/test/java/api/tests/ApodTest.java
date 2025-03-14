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

    private static final String key = "DEMO_KEY";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api.nasa.gov";

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
                    .param("api_key", key)
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
                    .param("start_date", "2023-01-01")
                    .param("end_date", "2023-01-07")
                    .param("api_key", key)
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