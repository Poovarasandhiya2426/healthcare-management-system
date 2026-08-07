package com.stackly.healthcare.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponse {

    private Long doctorId;

    private String doctorName;

    private String specialization;

    private String email;

    private String mobileNumber;

    private Integer experience;

    private Double consultationFee;

    private String qualification;

    private String hospitalName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}