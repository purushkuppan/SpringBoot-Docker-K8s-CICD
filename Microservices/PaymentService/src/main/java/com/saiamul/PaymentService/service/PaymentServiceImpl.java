package com.saiamul.PaymentService.service;

import com.saiamul.PaymentService.entity.TransactionDetails;
import com.saiamul.PaymentService.model.PaymentMode;
import com.saiamul.PaymentService.model.PaymentRequest;
import com.saiamul.PaymentService.model.PaymentResponse;
import com.saiamul.PaymentService.repository.TransactionDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Override
    public Long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording paymentDetails: {}", paymentRequest);
        TransactionDetails transactionDetails = TransactionDetails.builder()
                .amount(paymentRequest.getAmount())
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .orderId(paymentRequest.getOrderId())
                .referenceNumber((paymentRequest.getReferenceNumber()))
                .build();

        transactionDetailsRepository.save(transactionDetails);
        log.info("Transaction completed with Id: {}", transactionDetails.getId());
        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailByOrderId(Long orderId) {
        log.info("Get Payment detail by order Id: {}", orderId);
        TransactionDetails transactionDetails = transactionDetailsRepository.findByOrderId(orderId);
        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paymentId(transactionDetails.getId())
                .paymentDate(transactionDetails.getPaymentDate())
                .amount(transactionDetails.getAmount())
                .orderId(transactionDetails.getOrderId())
                .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                .status(transactionDetails.getPaymentStatus())
                .build();
        log.info("Payment detail for the orderId is: {}", paymentResponse);
        return paymentResponse;
    }
}
