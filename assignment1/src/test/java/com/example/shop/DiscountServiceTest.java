package com.example.shop;

import org.junit.Test;

import static org.junit.Assert.*;

public class DiscountServiceTest {

    private final DiscountService service = new DiscountService();

    @Test
    public void returnsSubtotalWhenCodeIsNull() {
        assertEquals(100.0, service.applyDiscount(100.0, null), 1e-9);
    }

    @Test
    public void returnsSubtotalWhenCodeIsBlank() {
        assertEquals(100.0, service.applyDiscount(100.0, "   "), 1e-9);
    }

    @Test
    public void returnsSubtotalWhenCodeIsEmpty() {
        assertEquals(100.0, service.applyDiscount(100.0, ""), 1e-9);
    }

    @Test
    public void appliesTenPercentDiscountForStudent10() {
        assertEquals(90.0, service.applyDiscount(100.0, "STUDENT10"), 1e-9);
    }

    @Test
    public void student10IsCaseInsensitive() {
        assertEquals(90.0, service.applyDiscount(100.0, "student10"), 1e-9);
    }

    @Test
    public void appliesThirtyPercentDiscountForBlackFriday() {
        assertEquals(70.0, service.applyDiscount(100.0, "BLACKFRIDAY"), 1e-9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsForInvalidDiscountCode() {
        service.applyDiscount(100.0, "INVALID");
    }

    @Test
    public void returnsSubtotalForUnrecognizedCode() {
        assertEquals(100.0, service.applyDiscount(100.0, "FOOBAR"), 1e-9);
    }
}
