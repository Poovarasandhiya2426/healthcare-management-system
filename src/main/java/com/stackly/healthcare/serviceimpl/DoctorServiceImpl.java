package com.stackly.healthcare.serviceimpl;

import com.stackly.healthcare.common.ApiResponse;
import com.stackly.healthcare.constants.AppConstants;
import com.stackly.healthcare.entity.Doctor;
import com.stackly.healthcare.exception.DuplicateResourceException;
import com.stackly.healthcare.exception.ResourceNotFoundException;
import com.stackly.healthcare.mapper.DoctorMapper;
import com.stackly.healthcare.repository.DoctorRepository;
import com.stackly.healthcare.request.DoctorRequest;
import com.stackly.healthcare.response.DoctorResponse;
import com.stackly.healthcare.service.DoctorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    @Transactional
    public ApiResponse<DoctorResponse> createDoctor(DoctorRequest request) {

        log.info("Creating doctor with email: {}", request.getEmail());

        if (doctorRepository.existsByEmail(request.getEmail())) {

            log.warn("Doctor creation failed. Email already exists: {}",
                    request.getEmail());

            throw new DuplicateResourceException(
                    AppConstants.EMAIL_ALREADY_EXISTS
            );
        }

        if (doctorRepository.existsByMobileNumber(request.getMobileNumber())) {

            log.warn("Doctor creation failed. Mobile number already exists: {}",
                    request.getMobileNumber());

            throw new DuplicateResourceException(
                    AppConstants.MOBILE_ALREADY_EXISTS
            );
        }

        Doctor doctor = doctorMapper.mapToEntity(request);

        Doctor savedDoctor = doctorRepository.save(doctor);

        log.info("Doctor created successfully with ID: {}",
                savedDoctor.getDoctorId());

        return ApiResponse.<DoctorResponse>builder()
                .success(true)
                .message(AppConstants.DOCTOR_CREATED)
                .data(doctorMapper.mapToResponse(savedDoctor))
                .build();
    }

    @Override
    public ApiResponse<DoctorResponse> getDoctorById(Long doctorId) {

        log.info("Fetching doctor with ID: {}", doctorId);

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> {

                    log.warn("Doctor not found with ID: {}", doctorId);

                    return new ResourceNotFoundException(
                            AppConstants.DOCTOR_NOT_FOUND
                    );
                });

        log.info("Doctor found successfully with ID: {}", doctorId);

        return ApiResponse.<DoctorResponse>builder()
                .success(true)
                .message(AppConstants.DOCTOR_FOUND)
                .data(doctorMapper.mapToResponse(doctor))
                .build();
    }

    @Override
    public ApiResponse<List<DoctorResponse>> getAllDoctors() {

        log.info("Fetching all doctors");

        List<DoctorResponse> doctors = doctorRepository.findAll()
                .stream()
                .map(doctorMapper::mapToResponse)
                .collect(Collectors.toList());

        log.info("Successfully fetched {} doctors", doctors.size());

        return ApiResponse.<List<DoctorResponse>>builder()
                .success(true)
                .message(AppConstants.DOCTOR_LIST)
                .data(doctors)
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<DoctorResponse> updateDoctor(
            Long doctorId,
            DoctorRequest request) {

        log.info("Updating doctor with ID: {}", doctorId);

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> {

                    log.warn("Doctor update failed. Doctor not found with ID: {}",
                            doctorId);

                    return new ResourceNotFoundException(
                            AppConstants.DOCTOR_NOT_FOUND
                    );
                });

        if (!doctor.getEmail().equals(request.getEmail())
                && doctorRepository.existsByEmail(request.getEmail())) {

            log.warn("Doctor update failed. Email already exists: {}",
                    request.getEmail());

            throw new DuplicateResourceException(
                    AppConstants.EMAIL_ALREADY_EXISTS
            );
        }

        if (!doctor.getMobileNumber().equals(request.getMobileNumber())
                && doctorRepository.existsByMobileNumber(request.getMobileNumber())) {

            log.warn("Doctor update failed. Mobile number already exists: {}",
                    request.getMobileNumber());

            throw new DuplicateResourceException(
                    AppConstants.MOBILE_ALREADY_EXISTS
            );
        }

        doctorMapper.updateEntity(request, doctor);

        Doctor updatedDoctor = doctorRepository.save(doctor);

        log.info("Doctor updated successfully with ID: {}",
                updatedDoctor.getDoctorId());

        return ApiResponse.<DoctorResponse>builder()
                .success(true)
                .message(AppConstants.DOCTOR_UPDATED)
                .data(doctorMapper.mapToResponse(updatedDoctor))
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<String> deleteDoctor(Long doctorId) {

        log.info("Deleting doctor with ID: {}", doctorId);

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> {

                    log.warn("Doctor deletion failed. Doctor not found with ID: {}",
                            doctorId);

                    return new ResourceNotFoundException(
                            AppConstants.DOCTOR_NOT_FOUND
                    );
                });

        doctorRepository.delete(doctor);

        log.info("Doctor deleted successfully with ID: {}", doctorId);

        return ApiResponse.<String>builder()
                .success(true)
                .message(AppConstants.DOCTOR_DELETED)
                .data("Doctor Deleted Successfully")
                .build();
    }

    @Override
    public ApiResponse<List<DoctorResponse>> searchDoctors(String keyword) {

        log.info("Searching doctors with keyword: {}", keyword);

        List<DoctorResponse> doctors =
                doctorRepository.searchDoctors(keyword)
                        .stream()
                        .map(doctorMapper::mapToResponse)
                        .collect(Collectors.toList());

        log.info("Doctor search completed. {} doctors found",
                doctors.size());

        return ApiResponse.<List<DoctorResponse>>builder()
                .success(true)
                .message(AppConstants.DOCTOR_LIST)
                .data(doctors)
                .build();
    }
}