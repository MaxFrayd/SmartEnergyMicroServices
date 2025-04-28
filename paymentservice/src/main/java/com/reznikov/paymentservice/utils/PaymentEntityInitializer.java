package com.reznikov.paymentservice.utils;
import com.reznikov.paymentservice.domain.PaymentEntity;
import com.reznikov.paymentservice.repositories.PaymentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class PaymentEntityInitializer {

//    @Bean
//    CommandLineRunner initDatabase(PaymentRepository repository) {
//        return args -> {
//            repository.save(createPayment(1L, "PAID", "CREDIT_CARD", "REF12345", "INV001", "Stripe", "First payment"));
//            repository.save(createPayment(2L, "PENDING", "BANK_TRANSFER", "REF67890", "INV002", "PayPal", "Second payment"));
//            repository.save(createPayment(3L, "FAILED", "CASH", "REF54321", null, "Bank", "Third payment attempt"));
//        };
//    }
//
//    private PaymentEntity createPayment(Long userId, String status, String paymentMethod, String referenceNumber,
//                                        String invoiceId, String provider, String remarks) {
//        PaymentEntity payment = new PaymentEntity();
//        payment.setUserId(userId);
//        payment.setTransactionDate(LocalDateTime.now());
//        payment.setPaymentAmount(BigDecimal.valueOf(100.00));
//        payment.setCurrency(Currency.EURO);
//        payment.setStatus(PaymentStatus.COMPLETED);
//        payment.setEnergyAmount(BigDecimal.valueOf(10.0000));
//        payment.setEnergyPricePerUnit(BigDecimal.valueOf(10.0000));
//        payment.setTaxAmount(BigDecimal.valueOf(19.00));
//        payment.setFeeAmount(BigDecimal.valueOf(2.00));
//        payment.setDiscount(BigDecimal.valueOf(5.00));
//        payment.setPaymentMethod(paymentMethod);
//        payment.setReferenceNumber(referenceNumber);
//        payment.setInvoiceId(invoiceId);
//        payment.setProvider(provider);
//        payment.setRemarks(remarks);
//        return payment;
//    }
@Bean
CommandLineRunner initDatabase(PaymentRepository repository) {
    return args -> {
        // Generate 20 sample records
        for (int i = 1; i <= 20; i++) {
            // Choose different status/paymentMethod based on loop iteration
            PaymentStatus status;
            if (i % 3 == 0) {
                status = PaymentStatus.FAILED;
            } else if (i % 2 == 0) {
                status = PaymentStatus.PENDING;
            } else {
                status = PaymentStatus.COMPLETED;
            }

            String paymentMethod;
            switch (i % 3) {
                case 0:
                    paymentMethod = "BANK_TRANSFER";
                    break;
                case 1:
                    paymentMethod = "CREDIT_CARD";
                    break;
                default:
                    paymentMethod = "CASH";
                    break;
            }

            // Build referenceNumber, invoiceId, provider, remarks
            String referenceNumber = "REF" + String.format("%05d", i);
            String invoiceId = (i % 4 == 0) ? null : ("INV" + String.format("%03d", i));
            String provider = (i % 2 == 0) ? "ProviderX" : "ProviderY";
            String remarks = "Payment entry #" + i;

            // Persist the newly created PaymentEntity
            repository.save(createPayment(
                    (long) i,
                    status,
                    paymentMethod,
                    referenceNumber,
                    invoiceId,
                    provider,
                    remarks
            ));
        }
    };
}

    private PaymentEntity createPayment(Long userId,
                                        PaymentStatus status,
                                        String paymentMethod,
                                        String referenceNumber,
                                        String invoiceId,
                                        String provider,
                                        String remarks) {
        PaymentEntity payment = new PaymentEntity();
        payment.setUserId(userId);
        payment.setTransactionDate(LocalDateTime.now());

        // Demo amounts; adjust as needed
        payment.setPaymentAmount(BigDecimal.valueOf(100.00));
        payment.setCurrency(Currency.EURO);
        payment.setStatus(status);

        payment.setEnergyAmount(BigDecimal.valueOf(10.0000));
        payment.setEnergyPricePerUnit(BigDecimal.valueOf(5.0000));

        payment.setTaxAmount(BigDecimal.valueOf(19.00));
        payment.setFeeAmount(BigDecimal.valueOf(2.00));
        payment.setDiscount(BigDecimal.valueOf(5.00));

        payment.setPaymentMethod(paymentMethod);
        payment.setReferenceNumber(referenceNumber);
        payment.setInvoiceId(invoiceId);
        payment.setProvider(provider);
        payment.setRemarks(remarks);

        return payment;
    }
}