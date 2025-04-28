package com.reznikov.paymentservice.repositories;

import com.reznikov.paymentservice.domain.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    List<PaymentEntity> findByUserIdAndTransactionDateBetween(Long userId, LocalDateTime from, LocalDateTime to);
    List<PaymentEntity> findAllByUserId(Long userId);


}

