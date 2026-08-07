package com.stackly.healthcare.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicalRecordRequest {

    @NotNull(message = "Patient Id is required")
    private Long patientId;

    @NotBlank(message = "Diagnosis is required")
    private String diagnosis;

    @NotBlank(message = "Treatment is required")
    private String treatment;

    @NotBlank(message = "Allergies are required")
    private String allergies;

    @NotBlank(message = "Medical History is required")
    private String medicalHistory;

    @NotNull(message = "Record Date is required")
    private LocalDate recordDate;

}