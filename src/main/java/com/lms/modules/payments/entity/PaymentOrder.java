package com.lms.modules.payments.entity;

import com.lms.shared.entity.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("payment_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentOrder extends BaseEntity {
    @Id
    private String id;
    private String orderId;
    private String userId;
    private String courseId;
    private double amount;
    private String status;
    private String paymentId;
}
