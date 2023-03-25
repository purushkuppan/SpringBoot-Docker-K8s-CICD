package com.saiamul.PaymentService.service;

import com.saiamul.PaymentService.model.PaymentRequest;
import com.saiamul.PaymentService.model.PaymentResponse;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailByOrderId(Long orderId);
}
