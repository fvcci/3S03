package com.example.shop;

import org.junit.Test;

import static org.junit.Assert.*;

public class PricingServiceTest {

    private final PricingService service = new PricingService();

    @Test
    public void calculateSubtotalReturnsZeroForEmptyOrder() {
        Order order = new Order();
        assertEquals(0.0, service.calculateSubtotal(order), 1e-9);
    }

    @Test
    public void calculateSubtotalSumsAllItems() {
        Order order = new Order();
        order.addItem(new OrderItem("A", 2, 10.0));
        order.addItem(new OrderItem("B", 1, 5.0));
        assertEquals(25.0, service.calculateSubtotal(order), 1e-9);
    }

    @Test
    public void calculateSubtotalSingleItem() {
        Order order = new Order();
        order.addItem(new OrderItem("A", 3, 4.0));
        assertEquals(12.0, service.calculateSubtotal(order), 1e-9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateTaxThrowsForNegativeSubtotal() {
        service.calculateTax(-1.0);
    }

    @Test
    public void calculateTaxReturnsZeroForZeroSubtotal() {
        assertEquals(0.0, service.calculateTax(0.0), 1e-9);
    }

    @Test
    public void calculateTaxReturnsTwentyPercentForPositiveSubtotal() {
        assertEquals(20.0, service.calculateTax(100.0), 1e-9);
    }
}
