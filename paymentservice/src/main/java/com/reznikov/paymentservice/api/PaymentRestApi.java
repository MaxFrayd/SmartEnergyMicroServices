package com.reznikov.paymentservice.api;

import com.reznikov.paymentservice.dto.PaymentDTO;
import com.reznikov.paymentservice.services.PaymentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/payments")
public class PaymentRestApi {
    private final PaymentService paymentService;

    public PaymentRestApi(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Long> createPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        return ResponseEntity.ok(paymentService.createPayment(paymentDTO));
    }

    @GetMapping("/csv")
    public ResponseEntity<byte[]> downloadPaymentCsv() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"payments.csv\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(paymentService.getPaymentsAsCsv());
    }

    @PutMapping
    public ResponseEntity <Long> updatePayment(@Valid PaymentDTO payment){
        return ResponseEntity.ok(paymentService.createPayment(payment));
    }
    @GetMapping("/all")
    public ResponseEntity<List<PaymentDTO>> getAll(){
        return ResponseEntity.ok(paymentService.getAllPayments());
    }
    @DeleteMapping("/id")
    public ResponseEntity deletePayment(Long id){
        paymentService.deletePayment(id);
       return ResponseEntity.accepted().build();
    }

    public ResponseEntity<Long> updatePaymentById (@PathVariable Long id, @Valid @RequestBody PaymentDTO paymentDTO) {
            paymentService.updatePayment(id, paymentDTO);
            return ResponseEntity.ok().build();

    }

}
