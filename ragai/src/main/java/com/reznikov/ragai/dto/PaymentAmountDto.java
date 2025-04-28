package com.reznikov.ragai.dto;

    public class PaymentAmountDto {
        private Long id;
        private Long totalPaymentAmount;
        private String currency;

        public PaymentAmountDto() {
        }

        public PaymentAmountDto(Long id, Long totalPaymentAmount, String currency) {
            this.id = id;
            this.totalPaymentAmount = totalPaymentAmount;
            this.currency = currency;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getTotalPaymentAmount() {
            return totalPaymentAmount;
        }

        public void setTotalPaymentAmount(Long totalPaymentAmount) {
            this.totalPaymentAmount = totalPaymentAmount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        @Override
        public String toString() {
            return "PaymentDataDto{" +
                    "id=" + id +
                    ", totalPaymentAmount=" + totalPaymentAmount +
                    ", currency='" + currency + '\'' +
                    '}';
        }
    }

