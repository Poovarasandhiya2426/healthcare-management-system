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
public class PatientResponse {

    private Long patientId;

    private String firstName;

    private String lastName;

    private String gender;

    private Integer age;

    private LocalDate dateOfBirth;

    private String mobileNumber;

    private String email;

    private String bloodGroup;

    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}