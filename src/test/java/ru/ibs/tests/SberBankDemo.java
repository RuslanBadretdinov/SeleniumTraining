package ru.ibs.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class SberBankDemo {
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void before() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        //Явные и неявные ожидания:
        //Неявные - general, глобальные ожидания - задаются один раз
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS); // проверка на загрузку страницы в течении 10 секунд.
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS); // срабатывает при вызове асинхронного скрипта, ожидает,
        // при выполнении JS скриптов на стороне браузера, значит, что драйвер превращается в JS executor.
        // ограничивают, чтобы асинхронный скрипт бесконечно не выполнялся
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // начинает ожидать пока появится элемент, где вызываются методы findElement или findElement

        //Явные ожидания - "задаются индивидуально". Нужно создать объект ожидания
        //Существуют классы Wait, FluentWait
        wait = new WebDriverWait(driver, 10, 2000); //опрос браузера с интервалом 2 секунды в течении 10 секунд.

        driver.get("https://www.sberbank.ru/ru/person");

        //Расшифровка методов WebDriver
        //driver.navigate().to("https://www.sberbank.ru/ru/person"); - сохраняется история в текущей сессии
        //driver.navigate().back(); - вернуться назад, так как есть история
        //driver.navigate().refresh; - обновить

        //driver.manage(); управление дравйвером. F12 - инструмент разработчика - Управление Cookies

        //driver.manage().window(). - управление окном браузера
        //maximize() - расширить на всё окно
        //fullscreen() - раскрытие окна
        //getPosition() - вернуть конкретную позицию браузера
        //getSize() - вернуть размер окна
        //setPosition() - установить позицию браузера
        //setSize() - установить размер браузера

        //driver.manage().timeouts(). - "ожидания прогрузки страницы"

        //driver.findElement() - поиск Web элемента
        //driver.findElements() - возврат всех Web элементов по найденному пути
        //driver.quit() - закрытие браузера "схлопывание", обрыв сессии
        //driver.close() - закрытие вкладки
        //driver.getTitle() - получение текста из вкладочки
        //driver.getCurrentUrl() - возврат URL, который забит
        //driver.getWindowHandle()- возврат id активного окна, который открыт
        //driver.getWindowHandles() - возврат листа с id всех окон, которые открыты в данном браузере - не в отдельном окне
        //driver.switchTo(). - переход на другую страницу
        //driver.switchTo().window() - переход в нужное окно
        //driver.switchTo().activeElement - переход в активный элемент ?
        //driver.switchTo().alert() - всплывающее окно
        //driver.switchTo().frame() - переход на страницу в странице - всегда нужно переключаться между страницей и вложенной страницей, так как видны элементы только в пределах одной страницы, одного тела???
        //driver.switchTo().parentFrame() - вызов родительского фрейма (страницы)
        //driver.getPageSource() - возвращает html страницы в виде строки

        driver.manage().window().maximize();

        //Локаторы - WebElement
        //webElement.click(); -
        //webElement.submit(); - отправка события "enter" после заполнения формы
        //webElement.sendKeys(); - заполнить поле, либо заполнить webElement
        //webElement.clear(); - очистить
        //webElement.getTagName(); - вернуть имя тега;
        //webElement.getAttribute(); - вернуть значение атрибута
        //webElement.isSelected(); - проверка радио баттон и чек боксы, если они выбраны, то возвращает true
        //webElement.isEnabled(); - проверка на то, что виден/включен ли элемент на странице
        //webElement.getText(); - возвращает текст, если он есть
        //webElement.findElement(By by); - поиск внутри элемента элемент;
        //webElement.isDisplayed() - не всегда работает, так как возможно перекрытие, проверяет виден ли элемент на экране
        //webElement.getLocation() - возврат позиции элемента
        //webElement.getSize() - возврат размера элемента
        //webElement.getRect() - возврат области, которую занимает элемент
        //webElement.getCssValue() - возврат значений свойств элемента, которые нужно задать
    }

    @Test
    public void test() {
        WebElement baseMenu = driver.findElement(By.xpath("//a[text()='Страхование' and @role]")); //Определение WebElement по локаторам (( xpath - x html path ))
        baseMenu.click();
        //baseMenu.findElement(By.xpath(".//..")); - для поиска от элемента нужно перед xPath ввести "."

        WebElement parentBaseMenu = baseMenu.findElement(By.xpath("./.."));

        //явное ожидание (более гибкая проверка)
        wait.until(/*лямбда выражения*/ExpectedConditions.attributeContains(parentBaseMenu, "class", "opened"));

        //строгая обычная проверка
        Assert.assertTrue("Клик на базовое меню не был совершён", parentBaseMenu.getAttribute("class").contains("opened"));

        //((JavascriptExecutor) driver).executeAsyncScript("argument[0].click()"/*JS code - вклинивается в строку браузера и выполняет её*/, baseMenu); // при вызове асинхронного скрипта срабатывает setScriptTimeout,
        // так как асинхронный скрипт работает параллельно, иначе может бесконечно зависнуть

        WebElement subMenu = driver.findElement(By.xpath("//a[text()='Путешествия' and contains(@href, 'travel')]"));
        subMenu.click();

        WebElement titleTravelPage = driver.findElement(By.xpath("//h1[@data-test-id='PageTeaserDict_header']"));
        //Было раньше
        //assert titleTravelPage.isDisplayed(): "Страница страхования не загрузилась (виден и загрузился)";
        Assert.assertTrue("Страница страхования не загрузилась", titleTravelPage.isDisplayed());

        String errorMessage = "\nТекст заголовка страницы не совпал:\n" +
                "Ожидали: Страхование путешественников\n" +
                "Фактическое: "+titleTravelPage.getText();
        //Assert.assertEquals(errorMessage, "Страхование путешественников", titleTravelPage.getText());
        Assert.assertTrue(errorMessage, "Страхование путешественников".equals(titleTravelPage.getText()));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void after() {
        driver.quit();
    }
}
