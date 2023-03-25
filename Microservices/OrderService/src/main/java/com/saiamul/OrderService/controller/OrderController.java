package com.saiamul.OrderService.controller;

import com.saiamul.OrderService.entity.Order;
import com.saiamul.OrderService.model.OrderRequest;
import com.saiamul.OrderService.model.OrderResponse;
import com.saiamul.OrderService.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasAuthority('Customer')")
    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){

        Long orderId = orderService.placeOrder(orderRequest);

        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('Admin') || hasAuthority('Customer')")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId){

        OrderResponse orderResponse = orderService.getOrderById(orderId);


        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }


}
