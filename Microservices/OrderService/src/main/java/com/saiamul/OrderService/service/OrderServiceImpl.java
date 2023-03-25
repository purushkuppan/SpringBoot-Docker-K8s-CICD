package com.saiamul.OrderService.service;

import com.saiamul.OrderService.entity.Order;
import com.saiamul.OrderService.exception.CustomException;
import com.saiamul.OrderService.external.client.PaymentServiceClient;
import com.saiamul.OrderService.external.client.ProductServiceClient;
import com.saiamul.OrderService.external.requestmodel.PaymentRequest;
import com.saiamul.OrderService.model.OrderRequest;
import com.saiamul.OrderService.model.OrderResponse;
import com.saiamul.OrderService.model.PaymentResponse;
import com.saiamul.OrderService.model.ProductResponse;
import com.saiamul.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductServiceClient productServiceClient;

    @Autowired
    private PaymentServiceClient paymentServiceClient;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Long placeOrder(OrderRequest orderRequest) {
        log.info("Order details start created in service");

        log.info("Calling product service to reduce product quantity");

        productServiceClient.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        log.info("Product reduced from product service and now order is about to create");

        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .quantity(orderRequest.getQuantity())
                .orderDate(Instant.now())
                .productId(orderRequest.getProductId())
                .build();
        orderRepository.save(order);
        log.info("Order Created for Order Id {}" , order.getId());

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .amount(orderRequest.getTotalAmount())
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .build();

        String orderStatus = null;
        try {
            paymentServiceClient.doPayment(paymentRequest);
            log.info("Payment Done successfully. Changing the order status to Placed ");
            orderStatus = "PLACED";
        } catch (Exception e){
            log.error("Error Occurred in payment. Changing the Order Status as Payment Failed");
            productServiceClient.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity()*-1);
            orderStatus = "PAYMENT_FAILED";
        }
        order.setOrderStatus(orderStatus);

        orderRepository.save(order);

        log.info("Calling Payment service to complete the payment");
        return order.getId();
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order =  orderRepository.findById(orderId)
                .orElseThrow( () -> new CustomException("Order not Found for Id:"+orderId, "ORDER_NOT_FOUND", 404));
        log.info("Invoking product information for product id: {} ", order.getProductId());
        ProductResponse productResponse =
                restTemplate.getForObject("http://PRODUCT-SERVICE/product/"+order.getProductId(),
                        ProductResponse.class);
        log.info("Product information for product id: {} ", productResponse);

        log.info("Invoking Payment detail for Order Id: {}", order.getId());

        PaymentResponse paymentResponse =
                restTemplate.getForObject("http://PAYMENT-SERVICE/payment/"+order.getId(), PaymentResponse.class);

        log.info("Payment information for Order id: {} ", paymentResponse);
        OrderResponse orderResponse = OrderResponse.builder()
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .productDetail(productResponse)
                .paymentResponse(paymentResponse)
                .build();

        return orderResponse;
    }
}
