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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final AppointmentRepository appointmentRepository;

    @Override
    public ApiResponse<PatientResponse> createPatient(PatientRequest request) {

        log.info("Creating patient with email: {}", request.getEmail());

        if (patientRepository.existsByEmail(request.getEmail())) {

            log.warn("Patient creation failed. Email already exists: {}",
                    request.getEmail());

            throw new DuplicateResourceException(
                    AppConstants.EMAIL_ALREADY_EXISTS
            );
        }

        if (patientRepository.existsByMobileNumber(request.getMobileNumber())) {

            log.warn("Patient creation failed. Mobile number already exists: {}",
                    request.getMobileNumber());

            throw new DuplicateResourceException(
                    AppConstants.MOBILE_ALREADY_EXISTS
            );
        }

        Patient patient = patientMapper.mapToEntity(request);

        Patient savedPatient = patientRepository.save(patient);

        log.info("Patient created successfully with ID: {}",
                savedPatient.getPatientId());

        return ApiResponse.<PatientResponse>builder()
                .success(true)
                .message(AppConstants.PATIENT_CREATED)
                .data(patientMapper.mapToResponse(savedPatient))
                .build();
    }

    @Override
    public ApiResponse<PatientResponse> getPatientById(Long patientId) {

        log.info("Fetching patient with ID: {}", patientId);

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> {

                    log.warn("Patient not found with ID: {}", patientId);

                    return new ResourceNotFoundException(
                            AppConstants.PATIENT_NOT_FOUND
                    );
                });

        log.info("Patient found successfully with ID: {}", patientId);

        return ApiResponse.<PatientResponse>builder()
                .success(true)
                .message(AppConstants.PATIENT_FOUND)
                .data(patientMapper.mapToResponse(patient))
                .build();
    }

    @Override
    public ApiResponse<List<PatientResponse>> getAllPatients() {

        log.info("Fetching all patients");

        List<PatientResponse> patients = patientRepository.findAll()
                .stream()
                .map(patientMapper::mapToResponse)
                .collect(Collectors.toList());

        log.info("Successfully fetched {} patients", patients.size());

        return ApiResponse.<List<PatientResponse>>builder()
                .success(true)
                .message(AppConstants.PATIENT_LIST)
                .data(patients)
                .build();
    }

    @Override
    public ApiResponse<PatientResponse> updatePatient(
            Long patientId,
            PatientRequest request) {

        log.info("Updating patient with ID: {}", patientId);

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> {

                    log.warn("Patient update failed. Patient not found with ID: {}",
                            patientId);

                    return new ResourceNotFoundException(
                            AppConstants.PATIENT_NOT_FOUND
                    );
                });

        if (!patient.getEmail().equals(request.getEmail())
                && patientRepository.existsByEmail(request.getEmail())) {

            log.warn("Patient update failed. Email already exists: {}",
                    request.getEmail());

            throw new DuplicateResourceException(
                    AppConstants.EMAIL_ALREADY_EXISTS
            );
        }

        if (!patient.getMobileNumber().equals(request.getMobileNumber())
                && patientRepository.existsByMobileNumber(request.getMobileNumber())) {

            log.warn("Patient update failed. Mobile number already exists: {}",
                    request.getMobileNumber());

            throw new DuplicateResourceException(
                    AppConstants.MOBILE_ALREADY_EXISTS
            );
        }

        patientMapper.updateEntity(request, patient);

        Patient updatedPatient = patientRepository.save(patient);

        log.info("Patient updated successfully with ID: {}",
                updatedPatient.getPatientId());

        return ApiResponse.<PatientResponse>builder()
                .success(true)
                .message(AppConstants.PATIENT_UPDATED)
                .data(patientMapper.mapToResponse(updatedPatient))
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<String> deletePatient(Long patientId) {

        log.info("Deleting patient with ID: {}", patientId);

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> {

                    log.warn("Patient deletion failed. Patient not found with ID: {}",
                            patientId);

                    return new ResourceNotFoundException(
                            AppConstants.PATIENT_NOT_FOUND
                    );
                });

        if (appointmentRepository.existsByPatientPatientId(patientId)) {

            log.warn(
                    "Patient deletion blocked. Appointments exist for patient ID: {}",
                    patientId
            );

            throw new IllegalStateException(
                    "Cannot delete patient because appointments exist."
            );
        }

        patientRepository.delete(patient);

        log.info("Patient deleted successfully with ID: {}", patientId);

        return ApiResponse.<String>builder()
                .success(true)
                .message(AppConstants.PATIENT_DELETED)
                .data("Patient Deleted Successfully")
                .build();
    }

    @Override
    public ApiResponse<List<PatientResponse>> searchPatients(String keyword) {

        log.info("Searching patients with keyword: {}", keyword);

        List<PatientResponse> patients = patientRepository.searchPatients(keyword)
                .stream()
                .map(patientMapper::mapToResponse)
                .collect(Collectors.toList());

        log.info("Patient search completed. {} patients found",
                patients.size());

        return ApiResponse.<List<PatientResponse>>builder()
                .success(true)
                .message(AppConstants.PATIENT_LIST)
                .data(patients)
                .build();
    }

    @Override
    public ApiResponse<Page<PatientResponse>> getPatientsWithPagination(
            int page,
            int size,
            String sortBy,
            String direction) {

        log.info(
                "Fetching patients with pagination. page={}, size={}, sortBy={}, direction={}",
                page,
                size,
                sortBy,
                direction
        );

        if (page < 0) {

            log.warn("Invalid page number: {}", page);

            throw new IllegalArgumentException(
                    "Page number cannot be negative"
            );
        }

        if (size <= 0) {

            log.warn("Invalid page size: {}", size);

            throw new IllegalArgumentException(
                    "Page size must be greater than zero"
            );
        }

        Pageable pageable;

        if (sortBy != null && !sortBy.isBlank()) {

            Sort sort = direction != null
                    && direction.equalsIgnoreCase("desc")
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();

            pageable = PageRequest.of(page, size, sort);

        } else {

            pageable = PageRequest.of(page, size);
        }

        Page<Patient> patientPage =
                patientRepository.findAll(pageable);

        if (patientPage.getTotalElements() > 0
                && page >= patientPage.getTotalPages()) {

            log.warn(
                    "Requested page {} does not exist. Total pages available: {}",
                    page,
                    patientPage.getTotalPages()
            );

            throw new ResourceNotFoundException(
                    "Page " + page +
                            " does not exist. Total pages available: " +
                            patientPage.getTotalPages()
            );
        }

        Page<PatientResponse> patients =
                patientPage.map(patientMapper::mapToResponse);

        log.info(
                "Pagination completed successfully. Page={}, Records={}",
                page,
                patients.getNumberOfElements()
        );

        return ApiResponse.<Page<PatientResponse>>builder()
                .success(true)
                .message(AppConstants.PATIENT_LIST)
                .data(patients)
                .build();
    }
}