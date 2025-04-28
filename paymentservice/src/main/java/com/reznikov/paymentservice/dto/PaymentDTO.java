package com.reznikov.paymentservice.dto;

import com.reznikov.paymentservice.utils.Currency;
import com.reznikov.paymentservice.utils.PaymentStatus;
import com.reznikov.paymentservice.utils.ValidEnum;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor

@Data
public class PaymentDTO {
    @NotNull
    private Long id;
    private Long userId;
    private LocalDateTime transactionDate;
    @NotNull(message = "payment is required")
    @DecimalMin(value = "0.00", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must have up to 10 integer digits and 2 decimal places")
    private BigDecimal paymentAmount;
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Username contains invalid characters")
    @ValidEnum(enumClass = Currency.class, message = "Invalid currency provided.")
    private String currency;
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Username contains invalid characters")
    @ValidEnum(enumClass = PaymentStatus.class, message = "Invalid status provided.")
    private String status;
    private BigDecimal energyAmount;
    private BigDecimal energyPricePerUnit;
    private BigDecimal taxAmount;
    private BigDecimal feeAmount;
    private BigDecimal discount;
    @NotBlank @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Username contains invalid characters")
    private String paymentMethod;
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Username contains invalid characters")
    private String referenceNumber;
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Username contains invalid characters")
    private String invoiceId;
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Username contains invalid characters")
    private String provider;
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Username contains invalid characters")
    private String remarks;
}

