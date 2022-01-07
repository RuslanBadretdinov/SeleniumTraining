package calculator.suit;

import calculator.CalculatorJUnit5Test;
import calculator.CalculatorTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(JUnitPlatform.class)

//Выбираем пакет с тестовыми классами
@SelectPackages("test/java/calculator")

//Выбираем конкретные классы объединяя их как бы в сьюты
@Suite.SuiteClasses({
        CalculatorJUnit5Test.class
})

public class JUnit5SuitTest {
}
