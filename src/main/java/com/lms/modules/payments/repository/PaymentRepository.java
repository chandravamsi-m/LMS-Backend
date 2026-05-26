package com.lms.modules.payments.repository;

import com.lms.modules.payments.entity.PaymentOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface PaymentRepository extends MongoRepository<PaymentOrder, String> {
    Optional<PaymentOrder> findByOrderId(String orderId);
}
