package com.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.order.dto.CreateOrderRequest;
import com.order.exception.OrderNotFoundException;
import com.order.model.Order;
import com.order.model.OrderStatus;

@Service
public class OrderService {
    private final Map<String, Order> orderStore = new HashMap<>();

    /* Creates a new order */
    public Order createOrder(CreateOrderRequest request) {
        if (request.getCustomerName() == null || request.getCustomerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name is required");
        }

        if (request.getAmount() == null || request.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, request.getCustomerName(), request.getAmount(), OrderStatus.NEW);

        orderStore.put(orderId, order);

        return order;
    }

    /* Retrieves an order by its ID */
    public Order getOrderById(String orderId) {
        Order order = orderStore.get(orderId);

        if (order == null) {
            throw new OrderNotFoundException("Order not found with id: " + orderId);
        }

        return order;
    }

    /* Get All Orders */
    public List<Order> getAllOrders() {
        return new ArrayList<>(orderStore.values());
    }

    /* Updates the status of an existing order */
    public Order updateOrderStatus(String orderId, OrderStatus newStatus) {
        Order order = getOrderById(orderId);

        OrderStatus currentStatus = order.getStatus();

        // Valid transitions only
        if (currentStatus == OrderStatus.NEW && newStatus == OrderStatus.PROCESSING) {
            order.setStatus(newStatus);
        } else if (currentStatus == OrderStatus.PROCESSING && newStatus == OrderStatus.COMPLETED) {
            order.setStatus(newStatus);
        } else {
            throw new IllegalArgumentException(
                    "Invalid status transition from " + currentStatus + " to " + newStatus);
        }

        return order;
    }
}
