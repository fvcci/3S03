package com.example.shop;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrderItemTest {

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsWhenQuantityIsZero() {
        new OrderItem("A", 0, 10.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsWhenQuantityIsNegative() {
        new OrderItem("A", -1, 10.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsWhenUnitPriceIsNegative() {
        new OrderItem("A", 1, -5.0);
    }

    @Test
    public void constructorAcceptsZeroUnitPrice() {
        OrderItem item = new OrderItem("Free", 3, 0.0);
        assertEquals(0.0, item.getTotalPrice(), 1e-9);
    }

    @Test
    public void constructorAcceptsValidArguments() {
        OrderItem item = new OrderItem("Widget", 2, 5.0);
        assertEquals(2, item.getQuantity());
    }

    @Test
    public void getTotalPriceReturnsQuantityTimesUnitPrice() {
        OrderItem item = new OrderItem("Widget", 3, 7.5);
        assertEquals(22.5, item.getTotalPrice(), 1e-9);
    }
}
