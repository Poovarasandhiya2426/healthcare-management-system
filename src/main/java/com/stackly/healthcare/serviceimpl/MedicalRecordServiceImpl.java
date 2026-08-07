package com.stackly.healthcare.serviceimpl;

import com.stackly.healthcare.common.ApiResponse;
import com.stackly.healthcare.entity.MedicalRecord;
import com.stackly.healthcare.entity.Patient;
import com.stackly.healthcare.exception.ResourceNotFoundException;
import com.stackly.healthcare.mapper.MedicalRecordMapper;
import com.stackly.healthcare.repository.MedicalRecordRepository;
import com.stackly.healthcare.repository.PatientRepository;
import com.stackly.healthcare.request.MedicalRecordRequest;
import com.stackly.healthcare.response.MedicalRecordResponse;
import com.stackly.healthcare.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    @Override
    public ApiResponse<MedicalRecordResponse> createMedicalRecord(MedicalRecordRequest request) {

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient Not Found"));

        MedicalRecord medicalRecord = medicalRecordMapper.mapToEntity(request, patient);

        MedicalRecord savedMedicalRecord = medicalRecordRepository.save(medicalRecord);

        return ApiResponse.<MedicalRecordResponse>builder()
                .success(true)
                .message("Medical Record Created Successfully")
                .data(medicalRecordMapper.mapToResponse(savedMedicalRecord))
                .build();
    }

    @Override
    public ApiResponse<MedicalRecordResponse> getMedicalRecordById(Long recordId) {

        MedicalRecord medicalRecord = medicalRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical Record Not Found"));

        return ApiResponse.<MedicalRecordResponse>builder()
                .success(true)
                .message("Medical Record Retrieved Successfully")
                .data(medicalRecordMapper.mapToResponse(medicalRecord))
                .build();
    }

    @Override
    public ApiResponse<List<MedicalRecordResponse>> getAllMedicalRecords() {

        List<MedicalRecordResponse> medicalRecords = medicalRecordRepository.findAll()
                .stream()
                .map(medicalRecordMapper::mapToResponse)
                .collect(Collectors.toList());

        return ApiResponse.<List<MedicalRecordResponse>>builder()
                .success(true)
                .message("Medical Records Retrieved Successfully")
                .data(medicalRecords)
                .build();
    }

    @Override
    public ApiResponse<MedicalRecordResponse> updateMedicalRecord(Long recordId,
                                                                  MedicalRecordRequest request) {

        MedicalRecord medicalRecord = medicalRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical Record Not Found"));

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient Not Found"));

        medicalRecordMapper.updateEntity(medicalRecord, request, patient);

        MedicalRecord updatedMedicalRecord = medicalRecordRepository.save(medicalRecord);

        return ApiResponse.<MedicalRecordResponse>builder()
                .success(true)
                .message("Medical Record Updated Successfully")
                .data(medicalRecordMapper.mapToResponse(updatedMedicalRecord))
                .build();
    }

    @Override
    public ApiResponse<String> deleteMedicalRecord(Long recordId) {

        MedicalRecord medicalRecord = medicalRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical Record Not Found"));

        medicalRecordRepository.delete(medicalRecord);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Medical Record Deleted Successfully")
                .data("Medical Record Deleted Successfully")
                .build();
    }

    @Override
    public ApiResponse<List<MedicalRecordResponse>> getMedicalRecordsByPatientId(Long patientId) {

        List<MedicalRecordResponse> medicalRecords = medicalRecordRepository
                .findByPatientPatientId(patientId)
                .stream()
                .map(medicalRecordMapper::mapToResponse)
                .collect(Collectors.toList());

        return ApiResponse.<List<MedicalRecordResponse>>builder()
                .success(true)
                .message("Medical Records Retrieved Successfully")
                .data(medicalRecords)
                .build();
    }
}