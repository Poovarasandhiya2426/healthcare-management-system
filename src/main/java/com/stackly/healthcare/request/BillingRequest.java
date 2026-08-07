package com.stackly.healthcare.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BillingRequest {

    @NotNull(message = "Patient Id is required")
    private Long patientId;

    @NotNull(message = "Consultation Fee is required")
    @Positive(message = "Consultation Fee must be greater than 0")
    private Double consultationFee;

    @NotNull(message = "Medicine Charge is required")
    @Positive(message = "Medicine Charge must be greater than 0")
    private Double medicineCharge;

    @NotNull(message = "Lab Charge is required")
    @Positive(message = "Lab Charge must be greater than 0")
    private Double labCharge;

    @NotNull(message = "Other Charge is required")
    @Positive(message = "Other Charge must be greater than 0")
    private Double otherCharge;

    @NotBlank(message = "Payment Status is required")
    private String paymentStatus;

    @NotBlank(message = "Payment Method is required")
    private String paymentMethod;

    @NotNull(message = "Bill Date is required")
    private LocalDate billDate;

}