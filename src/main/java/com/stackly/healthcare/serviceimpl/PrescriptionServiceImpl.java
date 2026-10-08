package com.stackly.healthcare.serviceimpl;

import com.stackly.healthcare.common.ApiResponse;
import com.stackly.healthcare.constants.AppConstants;
import com.stackly.healthcare.entity.Appointment;
import com.stackly.healthcare.entity.Prescription;
import com.stackly.healthcare.exception.ResourceNotFoundException;
import com.stackly.healthcare.mapper.PrescriptionMapper;
import com.stackly.healthcare.repository.AppointmentRepository;
import com.stackly.healthcare.repository.PrescriptionRepository;
import com.stackly.healthcare.request.PrescriptionRequest;
import com.stackly.healthcare.response.PrescriptionResponse;
import com.stackly.healthcare.service.PrescriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;
    private final PrescriptionMapper prescriptionMapper;

    @Override
    @Transactional
    public ApiResponse<PrescriptionResponse> createPrescription(
            PrescriptionRequest request) {

        log.info(
                "Creating prescription for appointment ID: {}",
                request.getAppointmentId()
        );

        Appointment appointment =
                appointmentRepository.findById(request.getAppointmentId())
                        .orElseThrow(() -> {

                            log.warn(
                                    "Prescription creation failed. Appointment not found with ID: {}",
                                    request.getAppointmentId()
                            );

                            return new ResourceNotFoundException(
                                    AppConstants.APPOINTMENT_NOT_FOUND
                            );
                        });

        Prescription prescription =
                prescriptionMapper.mapToEntity(request, appointment);

        Prescription savedPrescription =
                prescriptionRepository.save(prescription);

        log.info(
                "Prescription created successfully with ID: {}",
                savedPrescription.getPrescriptionId()
        );

        return ApiResponse.<PrescriptionResponse>builder()
                .success(true)
                .message(AppConstants.PRESCRIPTION_CREATED)
                .data(prescriptionMapper.mapToResponse(savedPrescription))
                .build();
    }

    @Override
    public ApiResponse<PrescriptionResponse> getPrescriptionById(
            Long prescriptionId) {

        log.info(
                "Fetching prescription with ID: {}",
                prescriptionId
        );

        Prescription prescription =
                prescriptionRepository.findById(prescriptionId)
                        .orElseThrow(() -> {

                            log.warn(
                                    "Prescription not found with ID: {}",
                                    prescriptionId
                            );

                            return new ResourceNotFoundException(
                                    AppConstants.PRESCRIPTION_NOT_FOUND
                            );
                        });

        log.info(
                "Prescription found successfully with ID: {}",
                prescriptionId
        );

        return ApiResponse.<PrescriptionResponse>builder()
                .success(true)
                .message(AppConstants.PRESCRIPTION_FOUND)
                .data(prescriptionMapper.mapToResponse(prescription))
                .build();
    }

    @Override
    public ApiResponse<List<PrescriptionResponse>> getAllPrescriptions() {

        log.info("Fetching all prescriptions");

        List<PrescriptionResponse> prescriptions =
                prescriptionRepository.findAll()
                        .stream()
                        .map(prescriptionMapper::mapToResponse)
                        .collect(Collectors.toList());

        log.info(
                "Successfully fetched {} prescriptions",
                prescriptions.size()
        );

        return ApiResponse.<List<PrescriptionResponse>>builder()
                .success(true)
                .message(AppConstants.PRESCRIPTION_LIST)
                .data(prescriptions)
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<PrescriptionResponse> updatePrescription(
            Long prescriptionId,
            PrescriptionRequest request) {

        log.info(
                "Updating prescription with ID: {}",
                prescriptionId
        );

        Prescription prescription =
                prescriptionRepository.findById(prescriptionId)
                        .orElseThrow(() -> {

                            log.warn(
                                    "Prescription update failed. Prescription not found with ID: {}",
                                    prescriptionId
                            );

                            return new ResourceNotFoundException(
                                    AppConstants.PRESCRIPTION_NOT_FOUND
                            );
                        });

        Appointment appointment =
                appointmentRepository.findById(request.getAppointmentId())
                        .orElseThrow(() -> {

                            log.warn(
                                    "Prescription update failed. Appointment not found with ID: {}",
                                    request.getAppointmentId()
                            );

                            return new ResourceNotFoundException(
                                    AppConstants.APPOINTMENT_NOT_FOUND
                            );
                        });

        prescriptionMapper.updateEntity(
                prescription,
                request,
                appointment
        );

        Prescription updatedPrescription =
                prescriptionRepository.save(prescription);

        log.info(
                "Prescription updated successfully with ID: {}",
                updatedPrescription.getPrescriptionId()
        );

        return ApiResponse.<PrescriptionResponse>builder()
                .success(true)
                .message(AppConstants.PRESCRIPTION_UPDATED)
                .data(prescriptionMapper.mapToResponse(updatedPrescription))
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<String> deletePrescription(Long prescriptionId) {

        log.info(
                "Deleting prescription with ID: {}",
                prescriptionId
        );

        Prescription prescription =
                prescriptionRepository.findById(prescriptionId)
                        .orElseThrow(() -> {

                            log.warn(
                                    "Prescription deletion failed. Prescription not found with ID: {}",
                                    prescriptionId
                            );

                            return new ResourceNotFoundException(
                                    AppConstants.PRESCRIPTION_NOT_FOUND
                            );
                        });

        prescriptionRepository.delete(prescription);

        log.info(
                "Prescription deleted successfully with ID: {}",
                prescriptionId
        );

        return ApiResponse.<String>builder()
                .success(true)
                .message(AppConstants.PRESCRIPTION_DELETED)
                .data(AppConstants.PRESCRIPTION_DELETED)
                .build();
    }

    @Override
    public ApiResponse<List<PrescriptionResponse>> getPrescriptionsByAppointmentId(
            Long appointmentId) {

        log.info(
                "Fetching prescriptions for appointment ID: {}",
                appointmentId
        );

        List<PrescriptionResponse> prescriptions =
                prescriptionRepository
                        .findByAppointmentAppointmentId(appointmentId)
                        .stream()
                        .map(prescriptionMapper::mapToResponse)
                        .collect(Collectors.toList());

        log.info(
                "Found {} prescriptions for appointment ID: {}",
                prescriptions.size(),
                appointmentId
        );

        return ApiResponse.<List<PrescriptionResponse>>builder()
                .success(true)
                .message(AppConstants.PRESCRIPTION_LIST)
                .data(prescriptions)
                .build();
    }
}