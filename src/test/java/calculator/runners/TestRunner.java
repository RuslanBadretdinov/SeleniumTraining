package calculator.runners;

import calculator.CalculatorTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import ru.ibs.tests.SberBankDemoTest;
import ru.rgs.tests.RgsInsuranceTest;

/**
 * Устаревший запуск всех тестов
 *
 * CustomRunner подробнее, когда будет Cucumber
 */
public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(
                CalculatorTest.class,
                RgsInsuranceTest.class,
                SberBankDemoTest.class
        );

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}
