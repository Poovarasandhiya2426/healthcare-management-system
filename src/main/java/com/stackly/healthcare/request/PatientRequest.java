package com.stackly.healthcare.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequest {

    @NotBlank(message = "First Name is required")
    @Size(min = 3, max = 50, message = "First Name must be between 3 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Size(min = 1, max = 50, message = "Last Name must be between 1 and 50 characters")
    private String lastName;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Age must be greater than 0")
    @Max(value = 120, message = "Age must be less than or equal to 120")
    private Integer age;

    @NotNull(message = "Date Of Birth is required")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Mobile Number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Mobile Number")
    private String mobileNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "Blood Group is required")
    private String bloodGroup;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address should not exceed 255 characters")
    private String address;

}