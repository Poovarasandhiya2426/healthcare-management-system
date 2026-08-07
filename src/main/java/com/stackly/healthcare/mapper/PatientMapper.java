package com.stackly.healthcare.mapper;

import com.stackly.healthcare.entity.Patient;
import com.stackly.healthcare.request.PatientRequest;
import com.stackly.healthcare.response.PatientResponse;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public Patient mapToEntity(PatientRequest request) {

        return Patient.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .gender(request.getGender())
                .age(request.getAge())
                .dateOfBirth(request.getDateOfBirth())
                .mobileNumber(request.getMobileNumber())
                .email(request.getEmail())
                .bloodGroup(request.getBloodGroup())
                .address(request.getAddress())
                .build();
    }

    public PatientResponse mapToResponse(Patient patient) {

        return PatientResponse.builder()
                .patientId(patient.getPatientId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .gender(patient.getGender())
                .age(patient.getAge())
                .dateOfBirth(patient.getDateOfBirth())
                .mobileNumber(patient.getMobileNumber())
                .email(patient.getEmail())
                .bloodGroup(patient.getBloodGroup())
                .address(patient.getAddress())
                .createdAt(patient.getCreatedAt())
                .updatedAt(patient.getUpdatedAt())
                .build();
    }

    public void updateEntity(PatientRequest request, Patient patient) {

        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setGender(request.getGender());
        patient.setAge(request.getAge());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setMobileNumber(request.getMobileNumber());
        patient.setEmail(request.getEmail());
        patient.setBloodGroup(request.getBloodGroup());
        patient.setAddress(request.getAddress());
    }

}