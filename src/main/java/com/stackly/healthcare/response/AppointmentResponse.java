package com.stackly.healthcare.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {

    private Long appointmentId;

    private Long patientId;

    private String patientName;

    private Long doctorId;

    private String doctorName;

    private String specialization;

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    private String reason;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}