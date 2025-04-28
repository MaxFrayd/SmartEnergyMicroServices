package com.reznikov.paymentservice.services;

import com.reznikov.paymentservice.domain.PaymentEntity;
import com.reznikov.paymentservice.dto.PaymentDTO;
import com.reznikov.paymentservice.repositories.PaymentRepository;
import com.reznikov.paymentservice.utils.InvalidStatusException;
import com.reznikov.paymentservice.utils.PaymentAmountException;
import com.reznikov.paymentservice.utils.PaymentNotFoundException;
import com.reznikov.paymentservice.utils.PaymentStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }


    @Override
    public byte[] getPaymentsAsCsv() {
        // Mock data for demonstration
       List<PaymentEntity> payments = paymentRepository.findAll();

        Field[] fields = PaymentEntity.class.getDeclaredFields();
        String header = Arrays.stream(fields)
                .map(Field::getName)
                .collect(Collectors.joining(","))+ "\n";

        // CSV Body
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String body = payments.stream()
                .map(p -> String.format("%d,%d,%s,%.2f,%s,%s,%.2f,%.2f,%.2f,%.2f,%.2f,%s,%s,%s,%s,%s",
                        p.getId(),
                        p.getUserId(),
                        p.getTransactionDate() != null ? p.getTransactionDate().format(formatter) : "",
                        p.getPaymentAmount() != null ? p.getPaymentAmount() : BigDecimal.ZERO,
                        p.getCurrency(),
                        p.getStatus(),
                        p.getEnergyAmount() != null ? p.getEnergyAmount() : BigDecimal.ZERO,
                        p.getEnergyPricePerUnit() != null ? p.getEnergyPricePerUnit() : BigDecimal.ZERO,
                        p.getTaxAmount() != null ? p.getTaxAmount() : BigDecimal.ZERO,
                        p.getFeeAmount() != null ? p.getFeeAmount() : BigDecimal.ZERO,
                        p.getDiscount() != null ? p.getDiscount() : BigDecimal.ZERO,
                        p.getPaymentMethod(),
                        p.getReferenceNumber(),
                        p.getInvoiceId(),
                        p.getProvider(),
                        p.getRemarks()))
                .collect(Collectors.joining("\n"));

        String csvContent = header + body;
        byte[] csvBytes = csvContent.getBytes(StandardCharsets.UTF_8);


        return csvBytes;
    }

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream().map(paymentMapper::toDTO).collect(Collectors.toList()); // paymentRepository.findAll();
    }
    //@Override
    public PaymentDTO getPayment(Long id) {
        Optional<PaymentEntity> existingPaymentWrapper = paymentRepository.findById(id);

        if(existingPaymentWrapper.isPresent()) {
            return existingPaymentWrapper.map(paymentMapper::toDTO).get();
        }

        throw new PaymentNotFoundException("Payment with id " + id + " not found");
    }

    @Transactional
    public Long createPayment(PaymentDTO paymentDTO) {
        PaymentEntity  paymentEntity = paymentMapper.fromDTO(paymentDTO);
        return paymentRepository.save(paymentEntity).getId();
    }

    @Transactional
    public void updatePayment(Long id, PaymentDTO updatedPayment) {
       Optional<PaymentEntity> existingPaymentWrapper = paymentRepository.findById(id);

       if (existingPaymentWrapper.isPresent()) {
           PaymentEntity existingPayment = existingPaymentWrapper.get();
           // Merge fields from updated payment into existingPayment, without changing the ID.
           if (updatedPayment.getPaymentAmount() != null){
               if (updatedPayment.getPaymentAmount().compareTo(BigDecimal.ZERO) > 0) {
                   existingPayment.setPaymentAmount(updatedPayment.getPaymentAmount());
               }
               throw new PaymentAmountException("Payment amount cannot be negative: "
               + updatedPayment.getPaymentAmount());
           }
           if (updatedPayment.getStatus() != null) {
               PaymentStatus newStatus = PaymentStatus.valueOf(updatedPayment.getStatus().toUpperCase());
               if (newStatus == PaymentStatus.COMPLETED && existingPayment.getStatus() != PaymentStatus.COMPLETED ){
                throw new InvalidStatusException("Payment status cannot be completed: " + newStatus);
               }
               existingPayment.setStatus(PaymentStatus.valueOf(updatedPayment.getStatus()));
           }
           if (updatedPayment.getRemarks() != null) {
               existingPayment.setRemarks(updatedPayment.getRemarks());
           }
           paymentRepository.save(existingPayment);
       }
        throw new PaymentNotFoundException("Payment not found");
    }

    @Override
    public List<PaymentDTO> getPaymentsByUserId(Long userId, LocalDate from, LocalDate to) {
        LocalDateTime fromDateTime = from.atStartOfDay();
        LocalDateTime toDateTime = to.atTime(LocalTime.MAX);

        return paymentRepository.findByUserIdAndTransactionDateBetween(userId, fromDateTime, toDateTime)
                .stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getPaymentsByUserId(Long userId) {
        return  paymentRepository.findAllByUserId(userId)
                .stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePayment(Long id) {
        if(paymentRepository.findById(id).isEmpty()){
            throw new PaymentNotFoundException("Payment with id " + id + " not found");
        }
        paymentRepository.deleteById(id);
    }


}
