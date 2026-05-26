package com.lms.modules.payments.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.lms.modules.payments.entity.PaymentOrder;
import com.lms.modules.payments.repository.PaymentRepository;
import com.lms.modules.courses.entity.Course;
import com.lms.modules.courses.repository.CourseRepository;
import com.lms.modules.enrollments.entity.Enrollment;
import com.lms.modules.enrollments.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepo;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Value("${razorpay.key_id}")
    private String keyId;

    @Value("${razorpay.key_secret}")
    private String keySecret;

    public PaymentOrder createRazorpayOrder(String userId, String courseId, double amount) {
        try {
            RazorpayClient razorpay = new RazorpayClient(keyId, keySecret);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", (int) (amount * 100)); // in paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "rcpt_" + UUID.randomUUID().toString().substring(0, 10));
            
            Order razorpayOrder = razorpay.orders.create(orderRequest);

            PaymentOrder order = PaymentOrder.builder()
                    .orderId(razorpayOrder.get("id"))
                    .userId(userId)
                    .courseId(courseId)
                    .amount(amount)
                    .status("PENDING")
                    .build();

            return paymentRepo.save(order);
        } catch (RazorpayException e) {
            log.error("Razorpay order creation failed: {}", e.getMessage());
            throw new RuntimeException("Failed to create payment order");
        }
    }

    public String verifyAndConfirmPayment(String userId, String orderId, String paymentId, String signature) {
        PaymentOrder order = paymentRepo.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment order not found"));

        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("Order ownership verification failed");
        }

        try {
            boolean isValid = Utils.verifySignature(orderId + "|" + paymentId, signature, keySecret);

            if (!isValid) {
                order.setStatus("FAILED");
                paymentRepo.save(order);
                return "Payment signature verification failed";
            }

            order.setPaymentId(paymentId);
            order.setStatus("SUCCESS");
            paymentRepo.save(order);

            // Automatically create course enrollment
            Course course = courseRepository.findById(order.getCourseId()).orElse(null);
            Enrollment enrollment = Enrollment.builder()
                    .userId(order.getUserId())
                    .courseId(order.getCourseId())
                    .courseTitle(course != null ? course.getTitle() : "Course Catalog")
                    .paymentStatus("PAID")
                    .build();
            enrollmentRepository.save(enrollment);

            log.info("Payment SUCCESS and Enrollment created for user: {}, order: {}", userId, orderId);
            return "Payment confirmed and course enrollment completed successfully";
        } catch (Exception e) {
            log.error("Payment verification error: {}", e.getMessage());
            return "Payment verification failed";
        }
    }
}
