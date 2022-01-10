package ru.rgs.tests.junit5task02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RgsInsuranceJUnitFiveTest extends BaseTest {

    @DisplayName("DisplayName RgsJUnit5")
    @ParameterizedTest(name = "RGS DMS Test = {0}")
    @CsvSource({
            "Иванов Иван Иванович, 9871234567, Стерлитамак, qwertyqwerty",
            "Петров Петр Петрович, 9877654321, Огримар, qwertyqwerty",
            "Николев Николай Петрович, 9177454321, Городской, qwertyqwerty"
    })
    @Tag("jUnit5Task02Test")
    public void test(String fIO, String phoneNumber, String city, String email) {

        //Переход на сам сайт
        driver.get("https://www.rgs.ru");

        //Клик на "Компании"
        actionToClick(getExistedWebElement("//a[contains(@href, 'for-companies')]/.."));

        //Клик на "Здоровье"
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h3[contains(text(), 'категории')]")));
        actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).build().perform();
        actionToClick(getExistedWebElement("//a[@href = '/for-companies/zdorove']/.."));

        //Клик на "Добровольное медицинское страхование"
        actionToClick(getExistedWebElement("//a[ contains(@href, 'dobrovolnoe') and @class]/.."));

        //Проверка заголовка "Добровольное медицинское страхование"
        wait.until(ExpectedConditions.textToBe(By.xpath("//div[contains(@class, 'sectionProductHero')]//div[@class = 'text-wrapper']/h1"),
                "Добровольное медицинское страхование"));

        //Клик на "Отправить заявку"
        actionToClick(getExistedWebElement("//div[@class = 'actions']/button/span[text()='Отправить заявку']/.."));

        //Проверка заголовка "Оперативно перезвоним для оформления полиса"
        wait.until(ExpectedConditions.textToBe(By.xpath("//section[contains(@class, 'callback')]//h2"), "Оперативно перезвоним\nдля оформления полиса"));

        //Заполнение формы на ДМС
        //1) заполнение ФИО
        WebElement formFIO = getExistedWebElement("//form//div[@formkey = 'ФИО']//input[@name='userName']");
        formFIO.sendKeys(fIO);
        Assertions.assertEquals(fIO, formFIO.getAttribute("value"));

        //2) заполнение номера телефона
        WebElement formPhoneNumber = getExistedWebElement("//form//div[@formkey = 'Номер телефона']//input[@name='userTel']");
        formPhoneNumber.sendKeys(phoneNumber);
        Assertions.assertEquals(makePhoneNumberValue(phoneNumber), formPhoneNumber.getAttribute("value"));

        //3) заполнение город
        WebElement formCity = getExistedWebElement("//form//div[@formkey = 'Адрес']//input[@class = 'vue-dadata__input']");
        formCity.sendKeys(city);
        Assertions.assertEquals(city, formCity.getAttribute("value"));

        //4) заполнение email
        WebElement formEmail = getExistedWebElement("//form//div[@formkey = 'email']//input[@name='userEmail']");
        formEmail.sendKeys(email);
        Assertions.assertEquals(email, formEmail.getAttribute("value"));

        //Заполнение согласия для персональных данных
        //Вопрос - не отработал elementToBeClickable
        WebElement checkBox = driver.findElement(By.xpath("//form/div[@class = 'row form-body']//input[@type = 'checkbox']"));
        actions.moveToElement(checkBox).click(checkBox).build().perform();

        //Нажатие на кнопку отправить
        actionToClick(getExistedWebElement("//form//button[text() = 'Свяжитесь со мной']"));

        //Проверка на введение неверной почты
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = 'Введите корректный адрес электронной почты']/ancestor::*/form//div[@formkey='email']"))).isEnabled();
    }

    public WebElement getExistedWebElement(String xPath) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPath)));
        return driver.findElement(By.xpath(xPath));
    }

    public void actionToClick(WebElement webElement) {
        actions.moveToElement(webElement).click(webElement).build().perform();
    }

    public String makePhoneNumberValue(String phoneNumber) {
        return "+7 ("+phoneNumber.substring(0, 3)+
                ") "+phoneNumber.substring(3, 6)+
                "-"+phoneNumber.substring(6);
    }
}
