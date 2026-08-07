package com.stackly.healthcare.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRequest {

    @NotBlank(message = "Doctor Name is required")
    @Size(min = 3, max = 100, message = "Doctor Name must be between 3 and 100 characters")
    private String doctorName;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "Mobile Number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Mobile Number")
    private String mobileNumber;

    @NotNull(message = "Experience is required")
    @Min(value = 0, message = "Experience cannot be negative")
    @Max(value = 50, message = "Experience cannot exceed 50 years")
    private Integer experience;

    @NotNull(message = "Consultation Fee is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Consultation Fee must be greater than 0")
    private Double consultationFee;

    @NotBlank(message = "Qualification is required")
    private String qualification;

    @NotBlank(message = "Hospital Name is required")
    private String hospitalName;

}