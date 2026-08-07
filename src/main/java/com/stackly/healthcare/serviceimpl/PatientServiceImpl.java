package com.stackly.healthcare.serviceimpl;

import com.stackly.healthcare.common.ApiResponse;
import com.stackly.healthcare.constants.AppConstants;
import com.stackly.healthcare.entity.Patient;
import com.stackly.healthcare.exception.DuplicateResourceException;
import com.stackly.healthcare.exception.ResourceNotFoundException;
import com.stackly.healthcare.mapper.PatientMapper;
import com.stackly.healthcare.repository.AppointmentRepository;
import com.stackly.healthcare.repository.PatientRepository;
import com.stackly.healthcare.request.PatientRequest;
import com.stackly.healthcare.response.PatientResponse;
import com.stackly.healthcare.service.PatientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final AppointmentRepository appointmentRepository;

    @Override
    public ApiResponse<PatientResponse> createPatient(PatientRequest request) {

        if (patientRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(AppConstants.EMAIL_ALREADY_EXISTS);
        }

        if (patientRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new DuplicateResourceException(AppConstants.MOBILE_ALREADY_EXISTS);
        }

        Patient patient = patientMapper.mapToEntity(request);

        Patient savedPatient = patientRepository.save(patient);

        return ApiResponse.<PatientResponse>builder()
                .success(true)
                .message(AppConstants.PATIENT_CREATED)
                .data(patientMapper.mapToResponse(savedPatient))
                .build();
    }

    @Override
    public ApiResponse<PatientResponse> getPatientById(Long patientId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.PATIENT_NOT_FOUND));

        return ApiResponse.<PatientResponse>builder()
                .success(true)
                .message(AppConstants.PATIENT_FOUND)
                .data(patientMapper.mapToResponse(patient))
                .build();
    }

    @Override
    public ApiResponse<List<PatientResponse>> getAllPatients() {

        List<PatientResponse> patients = patientRepository.findAll()
                .stream()
                .map(patientMapper::mapToResponse)
                .collect(Collectors.toList());

        return ApiResponse.<List<PatientResponse>>builder()
                .success(true)
                .message(AppConstants.PATIENT_LIST)
                .data(patients)
                .build();
    }

    @Override
    public ApiResponse<PatientResponse> updatePatient(Long patientId, PatientRequest request) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.PATIENT_NOT_FOUND));

        if (!patient.getEmail().equals(request.getEmail())
                && patientRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(AppConstants.EMAIL_ALREADY_EXISTS);
        }

        if (!patient.getMobileNumber().equals(request.getMobileNumber())
                && patientRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new DuplicateResourceException(AppConstants.MOBILE_ALREADY_EXISTS);
        }

        patientMapper.updateEntity(request, patient);

        Patient updatedPatient = patientRepository.save(patient);

        return ApiResponse.<PatientResponse>builder()
                .success(true)
                .message(AppConstants.PATIENT_UPDATED)
                .data(patientMapper.mapToResponse(updatedPatient))
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<String> deletePatient(Long patientId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AppConstants.PATIENT_NOT_FOUND));

        if (appointmentRepository.existsByPatientPatientId(patientId)) {

            throw new IllegalStateException(
                    "Cannot delete patient because appointments exist."
            );

        }

        patientRepository.delete(patient);

        return ApiResponse.<String>builder()
                .success(true)
                .message(AppConstants.PATIENT_DELETED)
                .data("Patient Deleted Successfully")
                .build();

    }

    @Override
    public ApiResponse<List<PatientResponse>> searchPatients(String keyword) {

        List<PatientResponse> patients = patientRepository.searchPatients(keyword)
                .stream()
                .map(patientMapper::mapToResponse)
                .collect(Collectors.toList());

        return ApiResponse.<List<PatientResponse>>builder()
                .success(true)
                .message(AppConstants.PATIENT_LIST)
                .data(patients)
                .build();

    }

    @Override
    public ApiResponse<Page<PatientResponse>> getPatientsWithPagination(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<PatientResponse> patients =
                patientRepository.findAll(pageable)
                        .map(patientMapper::mapToResponse);

        return ApiResponse.<Page<PatientResponse>>builder()
                .success(true)
                .message(AppConstants.PATIENT_LIST)
                .data(patients)
                .build();

    }
}