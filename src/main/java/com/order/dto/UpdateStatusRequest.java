package com.order.dto;

import com.order.model.OrderStatus;

import lombok.Data;

@Data
public class UpdateStatusRequest {
    private OrderStatus status;
}
