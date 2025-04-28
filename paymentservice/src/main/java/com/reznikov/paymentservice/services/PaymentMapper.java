package com.reznikov.paymentservice.services;

import com.reznikov.paymentservice.domain.PaymentEntity;
import com.reznikov.paymentservice.dto.PaymentDTO;
import com.reznikov.paymentservice.utils.Currency;
import com.reznikov.paymentservice.utils.InvalidCurrencyException;
import com.reznikov.paymentservice.utils.InvalidStatusException;
import com.reznikov.paymentservice.utils.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public  PaymentEntity fromDTO(PaymentDTO paymentDTO) {
        if (paymentDTO == null) {
            return null;
        }

        PaymentEntity payment = new PaymentEntity();
        payment.setUserId(paymentDTO.getUserId());
        payment.setTransactionDate(paymentDTO.getTransactionDate());
        payment.setPaymentAmount(paymentDTO.getPaymentAmount());
        if (paymentDTO.getCurrency() != null){
            try {
                payment.setCurrency(Currency.valueOf(paymentDTO.getCurrency().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new InvalidCurrencyException("Invalid currency");
            }
        }
        if (paymentDTO.getStatus() != null){
            try {
                payment.setStatus(PaymentStatus.valueOf(paymentDTO.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new InvalidStatusException("Invalid staus");
            }
        }
        payment.setCurrency(Currency.valueOf(paymentDTO.getCurrency()));
        payment.setStatus(PaymentStatus.valueOf(paymentDTO.getStatus()));
        payment.setEnergyAmount(paymentDTO.getEnergyAmount());
        payment.setEnergyPricePerUnit(paymentDTO.getEnergyPricePerUnit());
        payment.setTaxAmount(paymentDTO.getTaxAmount());
        payment.setFeeAmount(paymentDTO.getFeeAmount());
        payment.setDiscount(paymentDTO.getDiscount());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setReferenceNumber(paymentDTO.getReferenceNumber());
        payment.setInvoiceId(paymentDTO.getInvoiceId());
        payment.setProvider(paymentDTO.getProvider());
        payment.setRemarks(paymentDTO.getRemarks());
        return payment;
    }

    public PaymentDTO toDTO(PaymentEntity payment) {
        if (payment == null) {
            return null;
        }
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setUserId(payment.getUserId());
        dto.setTransactionDate(payment.getTransactionDate());
        dto.setPaymentAmount(payment.getPaymentAmount());
        dto.setCurrency(payment.getCurrency().toString());
        dto.setStatus(payment.getStatus().toString());
        dto.setEnergyAmount(payment.getEnergyAmount());
        dto.setEnergyPricePerUnit(payment.getEnergyPricePerUnit());
        dto.setTaxAmount(payment.getTaxAmount());
        dto.setFeeAmount(payment.getFeeAmount());
        dto.setDiscount(payment.getDiscount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setReferenceNumber(payment.getReferenceNumber());
        dto.setInvoiceId(payment.getInvoiceId());
        dto.setProvider(payment.getProvider());
        dto.setRemarks(payment.getRemarks());
        return dto;
    }
}
