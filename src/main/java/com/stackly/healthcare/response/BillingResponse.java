package com.stackly.healthcare.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingResponse {

    private Long billId;

    private Long patientId;

    private String patientName;

    private Double consultationFee;

    private Double medicineCharge;

    private Double labCharge;

    private Double otherCharge;

    private Double totalAmount;

    private String paymentStatus;

    private String paymentMethod;

    private LocalDate billDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}