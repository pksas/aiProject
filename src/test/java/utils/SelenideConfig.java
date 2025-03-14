package utils;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.util.Map;

import static com.codeborne.selenide.WebDriverRunner.setWebDriver;

public class SelenideConfig {

    private static String testEnvironment = System.getenv("PLATFORM");

    private static final String SELENOID_URL = "https://user1:1234@selenoid.autotests.cloud/wd/hub";

    public static void setup() throws MalformedURLException {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 30000;

        if ("web".equalsIgnoreCase(testEnvironment)) {
            Configuration.browser = "chrome";
            Configuration.browserSize = "1920x1080";
            Configuration.timeout = 30000;
            Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";

            System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
            setWebDriver(new ChromeDriver());

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            Configuration.browserCapabilities = chromeOptions;
        } else if ("selenoid".equalsIgnoreCase(testEnvironment)) {
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