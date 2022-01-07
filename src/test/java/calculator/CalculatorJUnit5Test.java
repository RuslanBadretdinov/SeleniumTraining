package calculator;

import calculator.suit.myanatation.MyAnatationTestOnWindows;
import jdk.jfr.Enabled;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.Duration;
import java.util.stream.Stream;

public class CalculatorJUnit5Test {

    @BeforeAll
    static void beforeAll() { System.out.println("@BeforeAll");}

    @AfterAll
    static void afterAll() { System.out.println("@AfterAll");}

    @BeforeEach
    void before() { System.out.println("BeforeEach");}

    @AfterEach
    void after() { System.out.println("AfterEach");}

    @RepeatedTest(name = RepeatedTest.LONG_DISPLAY_NAME, value = 1)
    @DisplayName("QWERTYname")
    void testTest() {
        System.out.println("1");
        //Assertions.assertTrue(false, "Проверка сообщения");

        Assertions.assertTimeout(Duration.ofSeconds(1), ()->{
            Thread.sleep(2000);
            System.out.println("Done");
        });

        Assertions.assertAll("numbers",
                () -> Assertions.assertTrue(false, "1"),
                () -> Assertions.assertTrue(false, "2"),
                () -> Assertions.assertTrue(true, "3"),
                () -> Assertions.assertTrue(true, "4")
                );

    }

    @Test
    @Disabled
    void testOne() {
        System.out.println("Как ignore");
    }

    @Test
    @Tag("tag")
    @DisabledOnJre(JRE.JAVA_8)
    @EnabledOnOs(OS.WINDOWS)
    void onlyOnJava8() {
        Assumptions.assumeTrue(false, "testAssume"); //Динамический Disable
    }

    @MyAnatationTestOnWindows
    void myAnatationTest() {

    }

    @Test
    @Tag("Tag")
    void testTwo() {

    }

    @ParameterizedTest
    @ValueSource(ints = {1 , 2 , 3})
    void testValueSource(int i) {
        System.out.println(i);
    }

    @ParameterizedTest
    @MethodSource("methodSource")
    void testMethodSource(String s) {
        System.out.println(s);
    }
    static Stream<String> methodSource() {
        return Stream.of("A", "B", "C");
    }

    @ParameterizedTest
    @MethodSource("msMP")
    void testMethodSource(int i, String s) {
        System.out.println(s+i);
    }
    static Stream<Arguments> msMP() {
        return Stream.of(
                Arguments.of(1, "A"),
                Arguments.of(2, "B"),
                Arguments.of(7, "asd")
        );
    }

    //Через CsvSource
    @ParameterizedTest
    @CsvSource({"1,A", "2,B", "3,Q"})
    void testCsvSource(int i, String s) {
        System.out.println(s+i);
    }

    @ParameterizedTest(name = "Testik data = {0}, {1}")
    @CsvSource(delimiter='|', value = {"1|A", "2|B", "3|Q"})
    void testCsvSourceTwo(int i, String s) {
        System.out.println(s+i);
    }

    //Данные в файле
    @ParameterizedTest(name = "Testik data = {0}, {1}")
    @CsvFileSource(resources = "path.csv", numLinesToSkip = 1)
    void testCsvSourceThree(int i, String s) {
        System.out.println(s+i);
    }
}
