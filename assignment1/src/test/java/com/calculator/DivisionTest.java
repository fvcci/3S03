package com.calculator;

import org.junit.Test;
import static org.junit.Assert.*;

public class DivisionTest {

    private final Calculator calc = new Calculator();

    @Test
    public void testDivideBasic() {
        assertEquals(2.0, calc.divide(10, 5), 0.0001);
    }

    @Test
    public void testDivideWithNegative() {
        assertEquals(-3.0, calc.divide(9, -3), 0.0001);
    }

    @Test(expected = ArithmeticException.class)
    public void testShouldThrowExceptionWhenDividingByZero() {
        calc.divide(5, 0);
    }

    @Test(expected = ArithmeticException.class)
    public void testShouldThrowExceptionWhenDividingZeroByZero() {
        calc.divide(0, 0);
    }

    @Test
    public void testShouldHandleRepeatingDecimalsWithPrecision() {
        assertEquals(3.3333, calc.divide(10, 3), 0.0001);
    }

    @Test
    public void testShouldReturnZeroWhenDividendIsZero() {
        assertEquals(0.0, calc.divide(0, 67), 0.0001);
    }
}