package calculator;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.*;
import org.junit.function.ThrowingRunnable;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.Parameterized;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

//Примеры Unit тестов
//TestRunner - если есть @Test, значит этот класс автоматом стал TestRunner

//@RunWith(BlockJUnit4ClassRunner.class) // Runner по умолчанию
//@RunWith(Parameterized.class) - один сценарий по разным данным
//@RunWith(Cucumber.class)
public class CalculatorTest {
    Calculator calculator = new Calculator();

    /**
     * Правило: Ошибка не ожидается во всех тест кейсах
     * Правило можно отдельно настроить в каждом тест-кейсе
     */
    @Rule
    public ExpectedException thrownRule = ExpectedException.none();

    /**
     * Распространяется на все тест-кейсы, имеет больший приоритет над ограничением по времени в @Test(timeout = 5000)
     */
    @Rule
    public TestRule testRule = new Timeout(1000, TimeUnit.MILLISECONDS);

    @BeforeClass()
    public static void beforeAll() { System.out.println("BeforeAll");}

    @Before()
    public void beforeEach() { System.out.println("\nBeforeEach");}

    /**
     * Ограничение на время выполнение тест-кейса
     */
    @Test(timeout = 5000)
    public void testSum5005() {
        Assert.assertEquals(6, calculator.sum(3,3));
        Assert.assertNotEquals(7, calculator.sum(3,3));
        try {
            Thread.sleep(5005);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Assert.assertFalse() - противоположность assertTrue
        //Assert.assertArrayEquals(); - Сначала нужно отсортировать массив
        //Assert.assertSame(); - сравниваются ссылки
    }

    @Test(timeout = 5000)
    public void testSum1005() {
        Assert.assertEquals(6, calculator.sum(3,3));
        Assert.assertNotEquals(7, calculator.sum(3,3));
        try {
            Thread.sleep(1005);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Assert.assertFalse() - противоположность assertTrue
        //Assert.assertArrayEquals(); - Сначала нужно отсортировать массив
        //Assert.assertSame(); - сравниваются ссылки
    }

    @Test
    public void testDivisionOne() {
        Assert.assertEquals(1, calculator.division(3,3));
    }

    @Test
    public void testDivisionOne1035() {
        try {
            Thread.sleep(1035);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(1, calculator.division(3,3));
    }

    //Перехват исключений: тест кейс пройдёт
    @Test(expected = ArithmeticException.class)
    public void testDivisionTwo() {
        System.out.println("Перехват исключений: тест кейс пройдёт");
        calculator.division(3, 0);
    }

    //Перехват исключений: тест кейс не пройдёт
    @Test
    public void testDivisionThree() {
        System.out.println("Перехват исключений: тест кейс не пройдёт");
        calculator.division(3, 0);
    }

    /**
     * Предпочтительный способ
     */
    @Test
    public void testDivisionFour() {
        System.out.println("Перехват исключений: сделали правило");
        thrownRule.expect(ArithmeticException.class);
        thrownRule.expectMessage("делили на нуль");
        calculator.division(3, 0);
    }

    @Test
    @Ignore("Это игнор метода testMultiplication - сюда писать причину игнора")
    public void testMultiplication() {
        Assert.assertEquals(9, calculator.multiplication(3,3));
    }

    /**
     * Assert.assertThrows - перехват ошибок через этот метод
     */

    @Test
    public void testDivisionFive() {

        //ThrowingRunnable tr = () -> calculator.division(5, 0);

        ArithmeticException ex = Assert.assertThrows(ArithmeticException.class, () -> calculator.division(5, 0));

    }

    @Test
    public void testAssertion() {
//        Assert.assertEquals("Сообщение", 6, calculator.multiplication(2, 3));
//        Assert.assertTrue("Message1", "I love Java!". contains("Java"));
//        Assert.assertTrue("Message3", "I love Java!". startsWith("Java!"));
//        Assert.assertTrue("Message2", "I love Java!". endsWith("Java."));

//        Assert.assertThat("Message4", "I love Java!",
//                CoreMatchers.allOf(CoreMatchers.containsString("Java"),
//                        CoreMatchers.endsWith("."),
//                        CoreMatchers.startsWith("Java!"),
//                        CoreMatchers.startsWith("Java")
//                ));

//        MatcherAssert.assertThat("Message4", "I love Java!",
//                CoreMatchers.allOf(CoreMatchers.containsString("Java"),
//                        CoreMatchers.endsWith("."),
//                        CoreMatchers.startsWith("Java!"),
//                        CoreMatchers.startsWith("Java")
//                ));

        Assert.fail("Сами фэйлим тест, если что-то не получилось");

        /**
         *  Hamscrest - наиболее важные Matchers в Hamscrest
         *  allOf - проверка всех условий сразу (параллельно)
         *  anyOf - хотя бы одно условие совпало
         *  not - если matcher не совпадает
         *  equalTo - равенство тестовому объекту с использованием equals
         *  is - обёртка для equalTo, для улучшения читаемости
         *  hasEntry, hasKey, HasValue - проверяет, что map содержит строку, ключ, значение
         *  hasItem, hasItems - коллекция содержит заданный элемент, элементы
         *  hasItemInArray - элемент присутствует в массиве
         *  equalToIgnoringCase
         *  equalToIgnoringWhiteSpace - равенство строки без учёта пробелов
         *  containsString, endsWith, startWith - строка содержит значение, строка заканчивается или начинается с заданного значения
         */
    }

    @After()
    public void AfterEach() { System.out.println("AfterEach");}

    @AfterClass()
    public static void afterAll() { System.out.println("\nAfterAll");}
}
