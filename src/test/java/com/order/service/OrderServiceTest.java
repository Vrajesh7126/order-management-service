package com.order.service;

import com.order.dto.CreateOrderRequest;
import com.order.model.Order;
import com.order.model.OrderStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {
    private final OrderService orderService = new OrderService();

    @Test
    public void testCreateOrder() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerName("Vrajesh");
        request.setAmount(5000.0);

        Order order = orderService.createOrder(request);

        assertNotNull(order);
        assertNotNull(order.getOrderId());
        assertEquals("Vrajesh", order.getCustomerName());
        assertEquals(5000.0, order.getAmount());
        assertEquals(OrderStatus.NEW, order.getStatus());
    }

    @Test
    public void testGetOrderById() {

        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerName("Rahul");
        request.setAmount(3000.0);

        Order createdOrder = orderService.createOrder(request);

        Order fetchedOrder = orderService.getOrderById(createdOrder.getOrderId());

        assertNotNull(fetchedOrder);
        assertEquals(createdOrder.getOrderId(), fetchedOrder.getOrderId());
        assertEquals("Rahul", fetchedOrder.getCustomerName());
    }

    @Test
    public void testUpdateOrderStatus() {

        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerName("Amit");
        request.setAmount(7000.0);

        Order createdOrder = orderService.createOrder(request);

        Order updatedOrder = orderService.updateOrderStatus(
                createdOrder.getOrderId(),
                OrderStatus.PROCESSING);

        assertNotNull(updatedOrder);
        assertEquals(OrderStatus.PROCESSING, updatedOrder.getStatus());
    }
}
