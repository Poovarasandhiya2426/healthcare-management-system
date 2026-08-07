package com.stackly.healthcare.service;

import com.stackly.healthcare.common.ApiResponse;
import com.stackly.healthcare.request.MedicalRecordRequest;
import com.stackly.healthcare.response.MedicalRecordResponse;

import java.util.List;

public interface MedicalRecordService {

    ApiResponse<MedicalRecordResponse> createMedicalRecord(MedicalRecordRequest request);

    ApiResponse<MedicalRecordResponse> getMedicalRecordById(Long recordId);

    ApiResponse<List<MedicalRecordResponse>> getAllMedicalRecords();

    ApiResponse<MedicalRecordResponse> updateMedicalRecord(Long recordId,
                                                           MedicalRecordRequest request);

    ApiResponse<String> deleteMedicalRecord(Long recordId);

    ApiResponse<List<MedicalRecordResponse>> getMedicalRecordsByPatientId(Long patientId);

}