package com.lms.modules.payments.controller;

import com.lms.modules.payments.entity.PaymentOrder;
import com.lms.modules.payments.service.PaymentService;
import com.lms.shared.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Endpoints for managing payment workflows")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-order")
    @Operation(summary = "Create Razorpay Order")
    public ResponseEntity<ApiResponse<PaymentOrder>> createOrder(
            Authentication auth,
            @RequestParam String courseId,
            @RequestParam double amount) {
        
        PaymentOrder order = paymentService.createRazorpayOrder(auth.getName(), courseId, amount);
        return ResponseEntity.ok(ApiResponse.success(order, "Razorpay order created successfully"));
    }

    @PostMapping("/verify")
    @Operation(summary = "Verify Razorpay Payment Signature")
    public ResponseEntity<ApiResponse<String>> verifyPayment(
            Authentication auth,
            @RequestParam String orderId,
            @RequestParam String paymentId,
            @RequestParam String signature) {
        
        String result = paymentService.verifyAndConfirmPayment(auth.getName(), orderId, paymentId, signature);
        if (result.contains("successfully")) {
            return ResponseEntity.ok(ApiResponse.success(result, "Payment confirmed"));
        } else {
            return ResponseEntity.badRequest().body(ApiResponse.error(result));
        }
    }
}
