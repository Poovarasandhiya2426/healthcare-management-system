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
public class PrescriptionResponse {

    private Long prescriptionId;

    private Long appointmentId;

    private Long patientId;

    private String patientName;

    private Long doctorId;

    private String doctorName;

    private String medicineName;

    private String dosage;

    private String frequency;

    private Integer durationInDays;

    private String instructions;

    private LocalDate prescriptionDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}