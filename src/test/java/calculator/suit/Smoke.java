package calculator.suit;

import calculator.CalculatorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.ibs.tests.SberBankDemoTest;
import ru.rgs.tests.RgsInsuranceTest;

/**
 * Запуск пачки тестов через Suite
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CalculatorTest.class, RgsInsuranceTest.class, SberBankDemoTest.class})
public class Smoke {

}
