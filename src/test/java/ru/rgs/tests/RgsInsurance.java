package ru.rgs.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RgsInsurance {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    String fIO = "Фамилиев Имя Отчествович";
    String phoneNumber = "+79871234567";
    String city = "Скип города";
    String email = "qwertyqwerty";

    @Before
    public void before() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        actions = new Actions(driver);
        driver.manage().window().maximize();
        //Явные и неявные ожидания:
        //Неявные - general, глобальные ожидания - задаются один раз
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS); // проверка на загрузку страницы в течении 10 секунд.
        //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); // начинает ожидать пока появится элемент, где вызываются методы findElement или findElements

        //Явные ожидания - "задаются индивидуально". Нужно создать объект ожидания
        //Существуют классы Wait, FluentWait
        wait = new WebDriverWait(driver, 30, 2000); //опрос браузера с интервалом 2 секунды в течении 10 секунд.

        driver.get("https://www.rgs.ru");
    }

    @Test
    public void test() {
        //к "Компаниям"
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'for-companies')]/..")));
        WebElement buttonToCompanies = driver.findElement(By.xpath("//a[contains(@href, 'for-companies')]/.."));
        buttonToCompanies.click();

        //к "Здоровью"
        //actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).build().perform();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h3[contains(text(), 'категории')]")));
        WebElement buttonToHealth = driver.findElement(By.xpath("//a[@href = '/for-companies/zdorove']/.."));
        actions.moveToElement(buttonToHealth).click(buttonToHealth).build().perform();
        if (buttonToHealth.isDisplayed()) {
            actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).moveToElement(buttonToHealth).click(buttonToHealth).build().perform();
        }


        //к "Добровольному медицинскому страхованию"
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[ contains(@href, 'dobrovolnoe') and @class]/..")));
        WebElement buttonToInsurance = driver.findElement(By.xpath("//a[ contains(@href, 'dobrovolnoe') and @class]/.."));
        actions.moveToElement(buttonToInsurance).click(buttonToInsurance).build().perform();

        //Проверка заголовка "Добровольное медицинское страхование"
        wait.until(ExpectedConditions.textToBe(By.xpath("//h1[contains(text(), 'Добро')]"), "Добровольное медицинское страхование"));

        //Нажатие на "Отправить заявку"
        WebElement buttonToApply = driver.findElement(By.xpath("//div[@class='actions']/button[contains(@class, 'action-item')]"));
        wait.until(ExpectedConditions.elementToBeClickable(buttonToApply));
        actions.moveToElement(buttonToApply).click(buttonToApply).build().perform();

        //Проверка заголовка "Оперативно перезвоним"
        wait.until(ExpectedConditions.textToBe(By.xpath("//div/h2[contains(text(),'Оперативно')]"), "Оперативно перезвоним\nдля оформления полиса"));

        //Заполнение формы;
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//form/div[@class = 'row form-body']/div[contains(@class, 'form')]/..")));
        List<WebElement> formList = driver.findElements(By.xpath("//form/div[@class = 'row form-body']/div[contains(@class, 'form')]//input"));

        formList.get(0).sendKeys(fIO);
        formList.get(1).sendKeys(phoneNumber);
        formList.get(3).sendKeys(city);//поменял местами
        formList.get(2).sendKeys(email);

        //Заполнение согласия для персональных данных
        WebElement checkBox = driver.findElement(By.xpath("//form/div[@class = 'row form-body']//input[@type = 'checkbox']"));
        actions.moveToElement(checkBox).click(checkBox).build().perform();

        //Проверка полей
        Assert.assertEquals("Данные о ФИО не были введены в поле", formList.get(0).getAttribute("value"), fIO);
        Assert.assertEquals("Данные о номере телефона не были введены в поле", formList.get(1).getAttribute("value"), "+7 (798) 712-3456");
        Assert.assertEquals("Данные об адресе не были введены в поле", formList.get(2).getAttribute("value"), email);
        Assert.assertEquals("Данные о email не были введены в поле", formList.get(3).getAttribute("value"), city);

        //Нажатие на кнопку отправить
        WebElement buttonForm = driver.findElement(By.xpath("//form//button[text() = 'Свяжитесь со мной']"));
        wait.until(ExpectedConditions.elementToBeClickable(buttonForm));
        actions.moveToElement(buttonForm).click(buttonForm).build().perform();

        //Проверка на введение неверной почты
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class and contains(text(),'корректный адрес электронной почты')]")));

        Assert.assertTrue("Отсутствует надпись о неправильном вводе email",
                driver.findElement(By.xpath("//span[@class and contains(text(),'корректный адрес электронной почты')]")).getText().
                        equals("Введите корректный адрес электронной почты"));
    }

    @After
    public void after() {
        driver.quit();
    }
}
