package com.saiamul.OrderService.service;

import com.saiamul.OrderService.entity.Order;
import com.saiamul.OrderService.model.OrderRequest;
import com.saiamul.OrderService.model.OrderResponse;

public interface OrderService {

    Long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderById(Long orderId);
}
