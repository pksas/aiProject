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
на проекте java, gradle, junit5, selenide сть класс конфигурации selenide для запуска автотестов на браузере хром:
... код класса ...
Реализуй возможность запуска на selenoid - "https://user1:1234@selenoid.autotests.cloud/wd/hub",
так что бы вариант запуска выбирался по переменной окружения. Если значение selenoid,
то запуск удаленный, если web - то локальный.
 */
package utils;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.util.Map;

import static com.codeborne.selenide.WebDriverRunner.setWebDriver;

public class SelenideConfig {
    private static final String SELENOID_URL = "https://user1:1234@selenoid.autotests.cloud/wd/hub";

    public static void setup() throws MalformedURLException {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080"; // Установите нужное разрешение
        Configuration.timeout = 30000;

        String testEnvironment = System.getenv("PLATFORM"); // Получаем переменную окружения

        if ("web".equalsIgnoreCase(testEnvironment)) { // Локальный запуск
            Configuration.browser = "chrome";
            Configuration.browserSize = "1920x1080"; // Установите нужное разрешение
            Configuration.timeout = 30000;
            Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";

            // Настройки для автоматического управления драйверами
            System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
            setWebDriver(new ChromeDriver());

            // Дополнительные опции для браузера Chrome
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            Configuration.browserCapabilities = chromeOptions;
        } else if (!"web".equalsIgnoreCase(testEnvironment)) { // Удаленный запуск на Selenoid
            System.out.println("Запуск теста удалённо на Selenoid.");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
            Configuration.browserCapabilities = capabilities;
            Configuration.remote = SELENOID_URL;
        } else {
            throw new IllegalArgumentException("Неверная переменная окружения TEST_ENVIRONMENT. Допустимые значения: 'web' или 'selenoid'.");
        }
    }
}