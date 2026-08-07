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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;
    private final PrescriptionMapper prescriptionMapper;

    @Override
    public ApiResponse<PrescriptionResponse> createPrescription(PrescriptionRequest request) {

        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment Not Found"));

        Prescription prescription = prescriptionMapper.mapToEntity(request, appointment);

        Prescription savedPrescription = prescriptionRepository.save(prescription);

        return ApiResponse.<PrescriptionResponse>builder()
                .success(true)
                .message("Prescription Created Successfully")
                .data(prescriptionMapper.mapToResponse(savedPrescription))
                .build();
    }

    @Override
    public ApiResponse<PrescriptionResponse> getPrescriptionById(Long prescriptionId) {

        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription Not Found"));

        return ApiResponse.<PrescriptionResponse>builder()
                .success(true)
                .message("Prescription Retrieved Successfully")
                .data(prescriptionMapper.mapToResponse(prescription))
                .build();
    }

    @Override
    public ApiResponse<List<PrescriptionResponse>> getAllPrescriptions() {

        List<PrescriptionResponse> prescriptions = prescriptionRepository.findAll()
                .stream()
                .map(prescriptionMapper::mapToResponse)
                .collect(Collectors.toList());

        return ApiResponse.<List<PrescriptionResponse>>builder()
                .success(true)
                .message("Prescriptions Retrieved Successfully")
                .data(prescriptions)
                .build();
    }

    @Override
    public ApiResponse<PrescriptionResponse> updatePrescription(Long prescriptionId,
                                                                PrescriptionRequest request) {

        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription Not Found"));

        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment Not Found"));

        prescriptionMapper.updateEntity(prescription, request, appointment);

        Prescription updatedPrescription = prescriptionRepository.save(prescription);

        return ApiResponse.<PrescriptionResponse>builder()
                .success(true)
                .message("Prescription Updated Successfully")
                .data(prescriptionMapper.mapToResponse(updatedPrescription))
                .build();
    }

    @Override
    public ApiResponse<String> deletePrescription(Long prescriptionId) {

        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription Not Found"));

        prescriptionRepository.delete(prescription);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Prescription Deleted Successfully")
                .data("Prescription Deleted Successfully")
                .build();
    }

    @Override
    public ApiResponse<List<PrescriptionResponse>> getPrescriptionsByAppointmentId(Long appointmentId) {

        List<PrescriptionResponse> prescriptions = prescriptionRepository
                .findByAppointmentAppointmentId(appointmentId)
                .stream()
                .map(prescriptionMapper::mapToResponse)
                .collect(Collectors.toList());

        return ApiResponse.<List<PrescriptionResponse>>builder()
                .success(true)
                .message("Prescriptions Retrieved Successfully")
                .data(prescriptions)
                .build();
    }
}