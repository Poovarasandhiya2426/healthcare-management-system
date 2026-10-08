package com.stackly.healthcare.serviceimpl;

import com.stackly.healthcare.common.ApiResponse;
import com.stackly.healthcare.constants.AppConstants;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    @Override
    public ApiResponse<MedicalRecordResponse> createMedicalRecord(
            MedicalRecordRequest request) {

        log.info(
                "Creating medical record for patient ID: {}",
                request.getPatientId()
        );

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> {

                    log.warn(
                            "Medical record creation failed. Patient not found with ID: {}",
                            request.getPatientId()
                    );

                    return new ResourceNotFoundException(
                            AppConstants.PATIENT_NOT_FOUND
                    );
                });

        MedicalRecord medicalRecord =
                medicalRecordMapper.mapToEntity(request, patient);

        MedicalRecord savedMedicalRecord =
                medicalRecordRepository.save(medicalRecord);

        log.info(
                "Medical record created successfully with ID: {}",
                savedMedicalRecord.getRecordId()
        );

        return ApiResponse.<MedicalRecordResponse>builder()
                .success(true)
                .message(AppConstants.MEDICAL_RECORD_CREATED)
                .data(medicalRecordMapper.mapToResponse(savedMedicalRecord))
                .build();
    }

    @Override
    public ApiResponse<MedicalRecordResponse> getMedicalRecordById(
            Long recordId) {

        log.info(
                "Fetching medical record with ID: {}",
                recordId
        );

        MedicalRecord medicalRecord =
                medicalRecordRepository.findById(recordId)
                        .orElseThrow(() -> {

                            log.warn(
                                    "Medical record not found with ID: {}",
                                    recordId
                            );

                            return new ResourceNotFoundException(
                                    AppConstants.MEDICAL_RECORD_NOT_FOUND
                            );
                        });

        log.info(
                "Medical record found successfully with ID: {}",
                recordId
        );

        return ApiResponse.<MedicalRecordResponse>builder()
                .success(true)
                .message(AppConstants.MEDICAL_RECORD_FOUND)
                .data(medicalRecordMapper.mapToResponse(medicalRecord))
                .build();
    }

    @Override
    public ApiResponse<List<MedicalRecordResponse>> getAllMedicalRecords() {

        log.info("Fetching all medical records");

        List<MedicalRecordResponse> medicalRecords =
                medicalRecordRepository.findAll()
                        .stream()
                        .map(medicalRecordMapper::mapToResponse)
                        .collect(Collectors.toList());

        log.info(
                "Successfully fetched {} medical records",
                medicalRecords.size()
        );

        return ApiResponse.<List<MedicalRecordResponse>>builder()
                .success(true)
                .message(AppConstants.MEDICAL_RECORD_LIST)
                .data(medicalRecords)
                .build();
    }

    @Override
    public ApiResponse<MedicalRecordResponse> updateMedicalRecord(
            Long recordId,
            MedicalRecordRequest request) {

        log.info(
                "Updating medical record with ID: {}",
                recordId
        );

        MedicalRecord medicalRecord =
                medicalRecordRepository.findById(recordId)
                        .orElseThrow(() -> {

                            log.warn(
                                    "Medical record update failed. Record not found with ID: {}",
                                    recordId
                            );

                            return new ResourceNotFoundException(
                                    AppConstants.MEDICAL_RECORD_NOT_FOUND
                            );
                        });

        Patient patient =
                patientRepository.findById(request.getPatientId())
                        .orElseThrow(() -> {

                            log.warn(
                                    "Medical record update failed. Patient not found with ID: {}",
                                    request.getPatientId()
                            );

                            return new ResourceNotFoundException(
                                    AppConstants.PATIENT_NOT_FOUND
                            );
                        });

        medicalRecordMapper.updateEntity(
                medicalRecord,
                request,
                patient
        );

        MedicalRecord updatedMedicalRecord =
                medicalRecordRepository.save(medicalRecord);

        log.info(
                "Medical record updated successfully with ID: {}",
                updatedMedicalRecord.getRecordId()
        );

        return ApiResponse.<MedicalRecordResponse>builder()
                .success(true)
                .message(AppConstants.MEDICAL_RECORD_UPDATED)
                .data(medicalRecordMapper.mapToResponse(updatedMedicalRecord))
                .build();
    }

    @Override
    public ApiResponse<String> deleteMedicalRecord(Long recordId) {

        log.info(
                "Deleting medical record with ID: {}",
                recordId
        );

        MedicalRecord medicalRecord =
                medicalRecordRepository.findById(recordId)
                        .orElseThrow(() -> {

                            log.warn(
                                    "Medical record deletion failed. Record not found with ID: {}",
                                    recordId
                            );

                            return new ResourceNotFoundException(
                                    AppConstants.MEDICAL_RECORD_NOT_FOUND
                            );
                        });

        medicalRecordRepository.delete(medicalRecord);

        log.info(
                "Medical record deleted successfully with ID: {}",
                recordId
        );

        return ApiResponse.<String>builder()
                .success(true)
                .message(AppConstants.MEDICAL_RECORD_DELETED)
                .data(AppConstants.MEDICAL_RECORD_DELETED)
                .build();
    }

    @Override
    public ApiResponse<List<MedicalRecordResponse>> getMedicalRecordsByPatientId(
            Long patientId) {

        log.info(
                "Fetching medical records for patient ID: {}",
                patientId
        );

        List<MedicalRecordResponse> medicalRecords =
                medicalRecordRepository
                        .findByPatientPatientId(patientId)
                        .stream()
                        .map(medicalRecordMapper::mapToResponse)
                        .collect(Collectors.toList());

        log.info(
                "Found {} medical records for patient ID: {}",
                medicalRecords.size(),
                patientId
        );

        return ApiResponse.<List<MedicalRecordResponse>>builder()
                .success(true)
                .message(AppConstants.MEDICAL_RECORD_LIST)
                .data(medicalRecords)
                .build();
    }
}