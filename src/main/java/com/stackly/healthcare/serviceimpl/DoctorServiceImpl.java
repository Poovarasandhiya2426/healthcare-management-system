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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public ApiResponse<DoctorResponse> createDoctor(DoctorRequest request) {

        if (doctorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(AppConstants.EMAIL_ALREADY_EXISTS);
        }

        if (doctorRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new DuplicateResourceException(AppConstants.MOBILE_ALREADY_EXISTS);
        }

        Doctor doctor = doctorMapper.mapToEntity(request);

        Doctor savedDoctor = doctorRepository.save(doctor);

        return ApiResponse.<DoctorResponse>builder()
                .success(true)
                .message(AppConstants.DOCTOR_CREATED)
                .data(doctorMapper.mapToResponse(savedDoctor))
                .build();
    }

    @Override
    public ApiResponse<DoctorResponse> getDoctorById(Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.DOCTOR_NOT_FOUND));

        return ApiResponse.<DoctorResponse>builder()
                .success(true)
                .message(AppConstants.DOCTOR_FOUND)
                .data(doctorMapper.mapToResponse(doctor))
                .build();
    }

    @Override
    public ApiResponse<List<DoctorResponse>> getAllDoctors() {

        List<DoctorResponse> doctors = doctorRepository.findAll()
                .stream()
                .map(doctorMapper::mapToResponse)
                .collect(Collectors.toList());

        return ApiResponse.<List<DoctorResponse>>builder()
                .success(true)
                .message(AppConstants.DOCTOR_LIST)
                .data(doctors)
                .build();
    }

    @Override
    public ApiResponse<DoctorResponse> updateDoctor(Long doctorId, DoctorRequest request) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.DOCTOR_NOT_FOUND));

        if (!doctor.getEmail().equals(request.getEmail())
                && doctorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(AppConstants.EMAIL_ALREADY_EXISTS);
        }

        if (!doctor.getMobileNumber().equals(request.getMobileNumber())
                && doctorRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new DuplicateResourceException(AppConstants.MOBILE_ALREADY_EXISTS);
        }

        doctorMapper.updateEntity(request, doctor);

        Doctor updatedDoctor = doctorRepository.save(doctor);

        return ApiResponse.<DoctorResponse>builder()
                .success(true)
                .message(AppConstants.DOCTOR_UPDATED)
                .data(doctorMapper.mapToResponse(updatedDoctor))
                .build();
    }

    @Override
    public ApiResponse<String> deleteDoctor(Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.DOCTOR_NOT_FOUND));

        doctorRepository.delete(doctor);

        return ApiResponse.<String>builder()
                .success(true)
                .message(AppConstants.DOCTOR_DELETED)
                .data("Doctor Deleted Successfully")
                .build();
    }

    @Override
    public ApiResponse<List<DoctorResponse>> searchDoctors(String keyword) {

        List<DoctorResponse> doctors =
                doctorRepository.searchDoctors(keyword)
                        .stream()
                        .map(doctorMapper::mapToResponse)
                        .collect(Collectors.toList());

        return ApiResponse.<List<DoctorResponse>>builder()
                .success(true)
                .message(AppConstants.DOCTOR_LIST)
                .data(doctors)
                .build();

    }
}