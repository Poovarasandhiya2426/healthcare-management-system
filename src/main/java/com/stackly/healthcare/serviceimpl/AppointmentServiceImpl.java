package com.stackly.healthcare.serviceimpl;

import com.stackly.healthcare.common.ApiResponse;
import com.stackly.healthcare.constants.AppConstants;
import com.stackly.healthcare.entity.Appointment;
import com.stackly.healthcare.entity.Doctor;
import com.stackly.healthcare.entity.Patient;
import com.stackly.healthcare.exception.ResourceNotFoundException;
import com.stackly.healthcare.mapper.AppointmentMapper;
import com.stackly.healthcare.repository.AppointmentRepository;
import com.stackly.healthcare.repository.DoctorRepository;
import com.stackly.healthcare.repository.PatientRepository;
import com.stackly.healthcare.request.AppointmentRequest;
import com.stackly.healthcare.response.AppointmentResponse;
import com.stackly.healthcare.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public ApiResponse<AppointmentResponse> createAppointment(
            AppointmentRequest request) {

        log.info(
                "Creating appointment for patient ID: {} and doctor ID: {}",
                request.getPatientId(),
                request.getDoctorId()
        );

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> {

                    log.warn(
                            "Appointment creation failed. Patient not found with ID: {}",
                            request.getPatientId()
                    );

                    return new ResourceNotFoundException(
                            AppConstants.PATIENT_NOT_FOUND
                    );
                });

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> {

                    log.warn(
                            "Appointment creation failed. Doctor not found with ID: {}",
                            request.getDoctorId()
                    );

                    return new ResourceNotFoundException(
                            AppConstants.DOCTOR_NOT_FOUND
                    );
                });

        Appointment appointment =
                appointmentMapper.mapToEntity(request, patient, doctor);

        Appointment savedAppointment =
                appointmentRepository.save(appointment);

        log.info(
                "Appointment created successfully with ID: {}",
                savedAppointment.getAppointmentId()
        );

        return ApiResponse.<AppointmentResponse>builder()
                .success(true)
                .message(AppConstants.APPOINTMENT_CREATED)
                .data(appointmentMapper.mapToResponse(savedAppointment))
                .build();
    }

    @Override
    public ApiResponse<AppointmentResponse> getAppointmentById(
            Long appointmentId) {

        log.info("Fetching appointment with ID: {}", appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> {

                    log.warn(
                            "Appointment not found with ID: {}",
                            appointmentId
                    );

                    return new ResourceNotFoundException(
                            AppConstants.APPOINTMENT_NOT_FOUND
                    );
                });

        log.info(
                "Appointment found successfully with ID: {}",
                appointmentId
        );

        return ApiResponse.<AppointmentResponse>builder()
                .success(true)
                .message(AppConstants.APPOINTMENT_FOUND)
                .data(appointmentMapper.mapToResponse(appointment))
                .build();
    }

    @Override
    public ApiResponse<List<AppointmentResponse>> getAllAppointments() {

        log.info("Fetching all appointments");

        List<AppointmentResponse> appointments =
                appointmentRepository.findAll()
                        .stream()
                        .map(appointmentMapper::mapToResponse)
                        .collect(Collectors.toList());

        log.info(
                "Successfully fetched {} appointments",
                appointments.size()
        );

        return ApiResponse.<List<AppointmentResponse>>builder()
                .success(true)
                .message(AppConstants.APPOINTMENT_LIST)
                .data(appointments)
                .build();
    }

    @Override
    public ApiResponse<AppointmentResponse> updateAppointment(
            Long appointmentId,
            AppointmentRequest request) {

        log.info("Updating appointment with ID: {}", appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> {

                    log.warn(
                            "Appointment update failed. Appointment not found with ID: {}",
                            appointmentId
                    );

                    return new ResourceNotFoundException(
                            AppConstants.APPOINTMENT_NOT_FOUND
                    );
                });

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> {

                    log.warn(
                            "Appointment update failed. Patient not found with ID: {}",
                            request.getPatientId()
                    );

                    return new ResourceNotFoundException(
                            AppConstants.PATIENT_NOT_FOUND
                    );
                });

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> {

                    log.warn(
                            "Appointment update failed. Doctor not found with ID: {}",
                            request.getDoctorId()
                    );

                    return new ResourceNotFoundException(
                            AppConstants.DOCTOR_NOT_FOUND
                    );
                });

        appointmentMapper.updateEntity(
                appointment,
                request,
                patient,
                doctor
        );

        Appointment updatedAppointment =
                appointmentRepository.save(appointment);

        log.info(
                "Appointment updated successfully with ID: {}",
                updatedAppointment.getAppointmentId()
        );

        return ApiResponse.<AppointmentResponse>builder()
                .success(true)
                .message(AppConstants.APPOINTMENT_UPDATED)
                .data(appointmentMapper.mapToResponse(updatedAppointment))
                .build();
    }

    @Override
    public ApiResponse<String> deleteAppointment(Long appointmentId) {

        log.info("Deleting appointment with ID: {}", appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> {

                    log.warn(
                            "Appointment deletion failed. Appointment not found with ID: {}",
                            appointmentId
                    );

                    return new ResourceNotFoundException(
                            AppConstants.APPOINTMENT_NOT_FOUND
                    );
                });

        appointmentRepository.delete(appointment);

        log.info(
                "Appointment deleted successfully with ID: {}",
                appointmentId
        );

        return ApiResponse.<String>builder()
                .success(true)
                .message(AppConstants.APPOINTMENT_DELETED)
                .data(AppConstants.APPOINTMENT_DELETED)
                .build();
    }

    @Override
    public ApiResponse<List<AppointmentResponse>> getAppointmentsByPatientId(
            Long patientId) {

        log.info(
                "Fetching appointments for patient ID: {}",
                patientId
        );

        List<AppointmentResponse> appointments =
                appointmentRepository
                        .findByPatientPatientId(patientId)
                        .stream()
                        .map(appointmentMapper::mapToResponse)
                        .collect(Collectors.toList());

        log.info(
                "Found {} appointments for patient ID: {}",
                appointments.size(),
                patientId
        );

        return ApiResponse.<List<AppointmentResponse>>builder()
                .success(true)
                .message(AppConstants.APPOINTMENT_LIST)
                .data(appointments)
                .build();
    }

    @Override
    public ApiResponse<List<AppointmentResponse>> getAppointmentsByDoctorId(
            Long doctorId) {

        log.info(
                "Fetching appointments for doctor ID: {}",
                doctorId
        );

        List<AppointmentResponse> appointments =
                appointmentRepository
                        .findByDoctorDoctorId(doctorId)
                        .stream()
                        .map(appointmentMapper::mapToResponse)
                        .collect(Collectors.toList());

        log.info(
                "Found {} appointments for doctor ID: {}",
                appointments.size(),
                doctorId
        );

        return ApiResponse.<List<AppointmentResponse>>builder()
                .success(true)
                .message(AppConstants.APPOINTMENT_LIST)
                .data(appointments)
                .build();
    }

    @Override
    public ApiResponse<List<AppointmentResponse>> searchAppointments(
            String keyword) {

        log.info(
                "Searching appointments with keyword: {}",
                keyword
        );

        List<AppointmentResponse> appointments =
                appointmentRepository.searchAppointments(keyword)
                        .stream()
                        .map(appointmentMapper::mapToResponse)
                        .collect(Collectors.toList());

        log.info(
                "Appointment search completed. {} appointments found",
                appointments.size()
        );

        return ApiResponse.<List<AppointmentResponse>>builder()
                .success(true)
                .message(AppConstants.APPOINTMENT_LIST)
                .data(appointments)
                .build();
    }
}