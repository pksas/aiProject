/*
Давай вынесем конфигурации Selenide в отдельный файл utils
 */
/*
startMaximized подсвечен в ide красным
 */
/*
замени WebDriverManager на ChromeDriver
 */
package utils;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.WebDriverRunner.setWebDriver;

public class SelenideConfig {
    public static void setup() {
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
    }
}