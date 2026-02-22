package com.example.shop;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrderServiceTest {

    private final OrderService service = new OrderService();

    @Test
    public void processOrderCancelsAndReturnsZeroForInvalidPayment() {
        Order order = new Order();
        order.addItem(new OrderItem("A", 1, 50.0));

        double total = service.processOrder(order, null, "crypto");

        assertEquals(0.0, total, 1e-9);
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    public void processOrderCancelsForNullPaymentMethod() {
        Order order = new Order();
        order.addItem(new OrderItem("A", 1, 50.0));

        double total = service.processOrder(order, null, null);

        assertEquals(0.0, total, 1e-9);
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    public void processOrderAppliesNoDiscountAndPays() {
        Order order = new Order();
        order.addItem(new OrderItem("A", 2, 50.0));

        double total = service.processOrder(order, null, "card");

        assertEquals(120.0, total, 1e-9);
        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    @Test
    public void processOrderAppliesStudentDiscountAndPays() {
        Order order = new Order();
        order.addItem(new OrderItem("A", 2, 50.0));

        double total = service.processOrder(order, "STUDENT10", "paypal");

        assertEquals(108.0, total, 1e-9);
        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    @Test
    public void processOrderAppliesBlackFridayDiscountAndPays() {
        Order order = new Order();
        order.addItem(new OrderItem("A", 2, 50.0));

        double total = service.processOrder(order, "BLACKFRIDAY", "card");

        assertEquals(84.0, total, 1e-9);
        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    @Test
    public void processOrderWithEmptyOrderAndValidPayment() {
        Order order = new Order();

        double total = service.processOrder(order, null, "card");

        assertEquals(0.0, total, 1e-9);
        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void processOrderThrowsForInvalidDiscountCode() {
        Order order = new Order();
        order.addItem(new OrderItem("A", 1, 50.0));

        service.processOrder(order, "INVALID", "card");
    }
}
