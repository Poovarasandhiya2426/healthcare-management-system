package com.stackly.healthcare.service;

import com.stackly.healthcare.common.ApiResponse;
import com.stackly.healthcare.request.PatientRequest;
import com.stackly.healthcare.response.PatientResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PatientService {

    ApiResponse<PatientResponse> createPatient(PatientRequest request);

    ApiResponse<PatientResponse> getPatientById(Long patientId);

    ApiResponse<List<PatientResponse>> getAllPatients();

    ApiResponse<PatientResponse> updatePatient(Long patientId, PatientRequest request);

    ApiResponse<String> deletePatient(Long patientId);

    ApiResponse<List<PatientResponse>> searchPatients(String keyword);

    ApiResponse<Page<PatientResponse>> getPatientsWithPagination(
            int page,
            int size,
            String sortBy,
            String direction
    );
}