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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public ApiResponse<AppointmentResponse> createAppointment(AppointmentRequest request) {

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.PATIENT_NOT_FOUND));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.DOCTOR_NOT_FOUND));

        Appointment appointment = appointmentMapper.mapToEntity(request, patient, doctor);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return ApiResponse.<AppointmentResponse>builder()
                .success(true)
                .message("Appointment Created Successfully")
                .data(appointmentMapper.mapToResponse(savedAppointment))
                .build();
    }

    @Override
    public ApiResponse<AppointmentResponse> getAppointmentById(Long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment Not Found"));

        return ApiResponse.<AppointmentResponse>builder()
                .success(true)
                .message("Appointment Retrieved Successfully")
                .data(appointmentMapper.mapToResponse(appointment))
                .build();
    }

    @Override
    public ApiResponse<List<AppointmentResponse>> getAllAppointments() {

        List<AppointmentResponse> appointments = appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::mapToResponse)
                .collect(Collectors.toList());

        return ApiResponse.<List<AppointmentResponse>>builder()
                .success(true)
                .message("Appointments Retrieved Successfully")
                .data(appointments)
                .build();
    }

    @Override
    public ApiResponse<AppointmentResponse> updateAppointment(Long appointmentId, AppointmentRequest request) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment Not Found"));

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.PATIENT_NOT_FOUND));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.DOCTOR_NOT_FOUND));

        appointmentMapper.updateEntity(appointment, request, patient, doctor);

        Appointment updatedAppointment = appointmentRepository.save(appointment);

        return ApiResponse.<AppointmentResponse>builder()
                .success(true)
                .message("Appointment Updated Successfully")
                .data(appointmentMapper.mapToResponse(updatedAppointment))
                .build();
    }

    @Override
    public ApiResponse<String> deleteAppointment(Long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment Not Found"));

        appointmentRepository.delete(appointment);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Appointment Deleted Successfully")
                .data("Appointment Deleted Successfully")
                .build();
    }

    @Override
    public ApiResponse<List<AppointmentResponse>> getAppointmentsByPatientId(Long patientId) {

        List<AppointmentResponse> appointments = appointmentRepository
                .findByPatientPatientId(patientId)
                .stream()
                .map(appointmentMapper::mapToResponse)
                .collect(Collectors.toList());

        return ApiResponse.<List<AppointmentResponse>>builder()
                .success(true)
                .message("Patient Appointments Retrieved Successfully")
                .data(appointments)
                .build();
    }

    @Override
    public ApiResponse<List<AppointmentResponse>> getAppointmentsByDoctorId(Long doctorId) {

        List<AppointmentResponse> appointments = appointmentRepository
                .findByDoctorDoctorId(doctorId)
                .stream()
                .map(appointmentMapper::mapToResponse)
                .collect(Collectors.toList());

        return ApiResponse.<List<AppointmentResponse>>builder()
                .success(true)
                .message("Doctor Appointments Retrieved Successfully")
                .data(appointments)
                .build();
    }

    @Override
    public ApiResponse<List<AppointmentResponse>> searchAppointments(String keyword) {

        List<AppointmentResponse> appointments =
                appointmentRepository.searchAppointments(keyword)
                        .stream()
                        .map(appointmentMapper::mapToResponse)
                        .collect(Collectors.toList());

        return ApiResponse.<List<AppointmentResponse>>builder()
                .success(true)
                .message(AppConstants.APPOINTMENT_LIST)
                .data(appointments)
                .build();

    }
}