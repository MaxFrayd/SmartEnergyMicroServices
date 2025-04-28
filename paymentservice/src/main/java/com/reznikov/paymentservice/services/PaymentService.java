package com.reznikov.paymentservice.services;

import com.reznikov.paymentservice.dto.PaymentDTO;
import java.time.LocalDate;
import java.util.List;

public abstract interface PaymentService {

    public  void updatePayment(Long id, PaymentDTO updatedPayment);
    public  Long createPayment(PaymentDTO paymentDTO);
    public PaymentDTO getPayment(Long id);
    public List<PaymentDTO> getAllPayments();
    public void deletePayment(Long id);
    public List<PaymentDTO> getPaymentsByUserId(Long userId);
    public List<PaymentDTO> getPaymentsByUserId(Long userId, LocalDate from, LocalDate to);
    public byte[] getPaymentsAsCsv();
}
