package calculator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CalculatorParametrizedTest {

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{
                {1, 1, 1},
                {2, 1, 2},
                {3, 1, 3},
                {2, 2, 4},
                {5, 1, 5},
                {0, 0, 0}
        });
    }

    @Parameterized.Parameter(0)
    public int a;

    @Parameterized.Parameter(1)
    public int b;

    @Parameterized.Parameter(2)
    public int c;

    @Test
    public void testSum() {
        System.out.println("Parametrs:\na = " + a + "\nb = " + b + "\nc = " + c +"\n");
        Assert.assertEquals(c, new Calculator().multiplication(a, b));
    }
}
