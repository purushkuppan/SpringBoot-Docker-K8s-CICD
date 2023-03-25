package com.saiamul.ApiGateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

    @GetMapping("orderServiceFallBack")
    public String orderServiceFallBack(){
        return "Order service is down please try again after some time!!!";
    }

    @GetMapping("paymentServiceFallBack")
    public String paymentServiceFallBack(){
        return "Payment service is down please try again after some time!!!";
    }

    @GetMapping("productServiceFallBack")
    public String productServiceFallBack(){
        return "Product service is down please try again after some time!!!";
    }

}
