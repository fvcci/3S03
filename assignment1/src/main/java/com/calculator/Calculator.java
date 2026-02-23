package com.calculator;

public class Calculator {

    public double divide(double dividend, double divisor) throws ArithmeticException {
        if (dividend == 0.0 && divisor == 0.0) {
            throw new ArithmeticException("0/0 is undefined");
        }
        if (divisor == 0.0) {
            throw new ArithmeticException("Diving by zero is undefined");
        }
        return dividend / divisor;
    }
}