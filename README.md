<h1>Проект автоматизации тестирования UI:
<a target="_blank" href="https://www.morozko-shop.ru/"> morozko-shop.ru </a>
и API:
<a target="_blank" href="https://api.nasa.gov/"> api.nasa.gov </a> 
</h1>

<p align="center">
<img height="100" src="images/logo/morozko.png"  > <img height="100" src="images/logo/nasa.png">
</p>

## Содержание
+ [Описание](#Описание)
+ [Технологии и инструменты](#Технологии-и-инструменты)
+ [Реализованные проверки](#Реализованные-проверки)
+ [Запуск тестов](#Запуск-тестов)
    + [Допустимые комбинации](#Допустимые-комбинации)
    + [Локальный запуск тестов](#Локальный-запуск-тестов)
    + [Удаленный запуск тестов](#Удаленный-запуск-тестов)
+ [Cборка тестов в Jenkins](#Cборка-тестов-в-Jenkins)
+ [Интеграция с Allure Report](#интеграция-с-allure-report)
    + [Диаграммы прохождения тестов](#Диаграммы-прохождения-тестов)
    + [Развернутый результат прохождения тестов](#Развернутый-результат-прохождения-тестов)
+ [Интеграция с Allure TestOps](#Интеграция-с-Allure-TestOps)
+ [Интеграция с Jira](#Интеграция-с-Jira)
+ [Уведомления в Telegram с использованием бота](#Уведомления-в-Telegram-с-использованием-бота)
+ [Пример выполнения теста в Selenoid](#Пример-выполнения-теста-в-Selenoid)


## Описание
morozko-shop.ru: Интернет-магазин морозко-shop.ru предлагает широкий ассортимент климатического оборудования, включая кондиционеры, обогреватели и системы вентиляции от ведущих производителей.

api.nasa.gov: API NASA — это платформа, предоставляющая доступ к различным данным и изображениям космических исследований через удобные API-интерфейсы для разработчиков. <br/>

**Особенности проекта**:
- `Page Object` шаблон проектирования
- Возможность запуска тестов: локально, удалённо, по тегам
- Использование `Lombok` для моделей в API тестах
- Возможность запуска тестов напрямую из Allure TestOps
- Интеграция с Jira
- По итогу прохождения автотестов генерируется Allure отчет. Содержание отчета:
    - Шаги теста
    - Скриншот страницы на последнем шаге
    - Исходный код страницы в браузере
    - Логи консоли браузера
    - Видео выполнения автотеста

## Технологии и инструменты

<div align="center">
<a href="https://www.jetbrains.com/idea/"><img alt="InteliJ IDEA" height="50" src="images/logo/Idea.svg" width="50"/></a>
<a href="https://github.com/"><img alt="GitHub" height="50" src="images/logo/GitHub.svg" width="50"/></a>  
<a href="https://www.java.com/"><img alt="Java" height="50" src="images/logo/Java.svg" width="50"/></a>
<a href="https://gradle.org/"><img alt="Gradle" height="50" src="images/logo/Gradle.svg" width="50"/></a>  
<a href="https://junit.org/junit5/"><img alt="JUnit 5" height="50" src="images/logo/Junit5.svg" width="50"/></a>
<a href="https://selenide.org/"><img alt="Selenide" height="50" src="images/logo/Selenide.svg" width="50"/></a>
<a href="https://aerokube.com/selenoid/"><img alt="Selenoid" height="50" src="images/logo/Selenoid.svg" width="50"/></a>
<a href="https://rest-assured.io/"><img alt="RestAssured" height="50" src="images/logo/RestAssured.svg" width="50"/></a>
<a href="https://www.jenkins.io/"><img alt="Jenkins" height="50" src="images/logo/Jenkins.svg" width="50"/></a>
<a href="https://github.com/allure-framework/"><img alt="Allure Report" height="50" src="images/logo/Allure.svg" width="50"/></a>
<a href="https://qameta.io/"><img alt="Allure TestOps" height="50" src="images/logo/Allure_TO.svg" width="50"/></a>
<a href="https://www.atlassian.com/software/jira"><img alt="Jira" height="50" src="images/logo/Jira.svg" width="50"/></a>  
</div>

## Реализованные проверки
### Web
- [x] Проверка заголовка домашней страницы
- [x] Рендеринг на странице особого элемента, выбранного в фильтре
- [x] Рендеринг на странице особого элемента при поиске его в строке поиска
- [x] Проверка текста всплывающей подсказки на кнопке идентификатора пользователя
- [x] Проверка заголовков страниц, открываемых при нажатии на ссылки выпадающего меню "Инвентарь" (@ParameterizedTest)
- [x] Быстрое добавление привычки в список
- [x] Переадресация на страницу регистрации при нажатии кнопки "Get Started" домашней страницы
- [x] Неудачная регистрация без заполнения всех полей

### Api
- [x] Выполнение успешного запроса на авторизацию
- [x] Выполнение неудачного запроса на вход с пустым паролем
- [x] Выполнение неудачного запроса на вход с пустым телом
- [x] Запрос текущего списка тегов
- [x] Выполнение запроса на удаление тега

### Ручные проверки:
- [x] Быстрое добавление ежедневного дела в список
- [x] Быстрое добавление награды в список

## Запуск тестов
> [!NOTE]
> Убедитесь, что у вас установлены Java, Gradle, IntelliJ IDEA и в качестве браузера используется Chrome
>

### Допустимые комбинации

```mermaid 
flowchart LR
    A(./gradle) --> B(clean)
    B --> C{Выбрать задачу}
    C --> D[runAciveAutoTests]
    C --> E[web]
    C --> F[api]
    D --> G[PLATFORM=web]
    D --> H[PLATFORM=selenoid]
```

### Локальный запуск тестов
#### Запуск всех тестов

Для запуска следует открыть IntelliJ IDEA и выполнить в терминале:
```
PLATFORM=web ./gradle clean runAciveAutoTests
```

#### WEB

локально
```
PLATFORM=web ./gradle clean web
```

#### API
```
./gradle clean api
```

### Удаленный запуск тестов
Тесты можно запустить из терминала IntelliJ IDEA, а выполнены они будут в удаленно запущенном браузере в Docker-контейнере Selenoid:

```
PLATFORM=selenoid ./gradle clean runAciveAutoTests
```

## Cборка тестов в <b><a target="_blank" href="https://jenkins.autotests.cloud/job/C22-VadimSolonin-habitica-project/">Jenkins</a></b>

>Для запуска сборки необходимо перейти в раздел `Build with Parameters` и нажать кнопку `Build`

<img src="images/screenshots/jenkins-project.png">

## Интеграция с <b><a target="_blank" href="https://jenkins.autotests.cloud/job/C22-VadimSolonin-habitica-project/13/allure/">Allure report</a></b>
#### Диаграммы прохождения тестов
`ALLURE REPORT` - отображает дату и время теста, общее количество запущенных тестов, а также диаграмму с процентом и количеством успешных, упавших и сломавшихся в процессе выполнения тестов <br/>
`TREND` - отображает тенденцию выполнения тестов для всех запусков <br/>
`SUITES` - отображает распределение тестов по сьютам <br/>
`CATEGORIES` - отображает распределение неудачных тестов по типам дефектов

<img src="images/screenshots/allure-main-report.png">

#### Развернутый результат прохождения тестов:
1. Общий список автотестов
2. Содержание автотеста
3. Вложения
   <img src="images/screenshots/allure-suites.png">


## Интеграция с <b><a target="_blank" href="https://allure.autotests.cloud/project/3876/dashboards">Allure TestOps</a></b>

>Диаграммы прохождения тестов
>
<img src="images/screenshots/allure-testops-dashboards.png">

## Интеграция с <b><a target="_blank" href="https://jira.autotests.cloud/browse/HOMEWORK-1005">Jira</a></b>

>В Jira создана задача
>
<img src="images/screenshots/jira-integration.png">

>В разделе `Allure:Test Cases` отображаются интегрированные автоматизированные и ручные тесты
<img src="images/screenshots/jira-with-allure-test-cases.png">

## Пример выполнения теста в Selenoid

> К каждому UI-тесту в отчете прилагается видео
<p align="center">
  <img src="images/video/ui-test.gif">
</p>