package com.stackly.healthcare.service;

import com.stackly.healthcare.common.ApiResponse;
import com.stackly.healthcare.request.AppointmentRequest;
import com.stackly.healthcare.response.AppointmentResponse;

import java.util.List;

public interface AppointmentService {

    ApiResponse<AppointmentResponse> createAppointment(AppointmentRequest request);

    ApiResponse<AppointmentResponse> getAppointmentById(Long appointmentId);

    ApiResponse<List<AppointmentResponse>> getAllAppointments();

    ApiResponse<AppointmentResponse> updateAppointment(Long appointmentId, AppointmentRequest request);

    ApiResponse<String> deleteAppointment(Long appointmentId);

    ApiResponse<List<AppointmentResponse>> getAppointmentsByPatientId(Long patientId);

    ApiResponse<List<AppointmentResponse>> getAppointmentsByDoctorId(Long doctorId);

    ApiResponse<List<AppointmentResponse>> searchAppointments(String keyword);

}