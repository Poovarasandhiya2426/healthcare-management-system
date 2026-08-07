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
public class MedicalRecordResponse {

    private Long recordId;

    private Long patientId;

    private String patientName;

    private String diagnosis;

    private String treatment;

    private String allergies;

    private String medicalHistory;

    private LocalDate recordDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}