package com.example.shop;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrderTest {

    @Test
    public void newOrderHasCreatedStatus() {
        Order order = new Order();
        assertEquals(OrderStatus.CREATED, order.getStatus());
    }

    @Test
    public void addItemSucceedsWhenStatusIsCreated() {
        Order order = new Order();
        order.addItem(new OrderItem("A", 1, 10.0));
        assertEquals(1, order.getItems().size());
    }

    @Test(expected = IllegalStateException.class)
    public void addItemThrowsWhenStatusIsPaid() {
        Order order = new Order();
        order.setStatus(OrderStatus.PAID);
        order.addItem(new OrderItem("A", 1, 10.0));
    }

    @Test(expected = IllegalStateException.class)
    public void addItemThrowsWhenStatusIsCancelled() {
        Order order = new Order();
        order.setStatus(OrderStatus.CANCELLED);
        order.addItem(new OrderItem("A", 1, 10.0));
    }

    @Test
    public void getItemsReturnsAllAddedItems() {
        Order order = new Order();
        order.addItem(new OrderItem("A", 1, 10.0));
        order.addItem(new OrderItem("B", 2, 20.0));
        assertEquals(2, order.getItems().size());
    }

    @Test
    public void setStatusUpdatesStatus() {
        Order order = new Order();
        order.setStatus(OrderStatus.PAID);
        assertEquals(OrderStatus.PAID, order.getStatus());
    }
}
