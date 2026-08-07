package com.stackly.healthcare.controller;

import com.stackly.healthcare.common.ApiResponse;
import com.stackly.healthcare.request.AppointmentRequest;
import com.stackly.healthcare.response.AppointmentResponse;
import com.stackly.healthcare.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponse>> createAppointment(
            @Valid @RequestBody AppointmentRequest request) {

        return new ResponseEntity<>(
                appointmentService.createAppointment(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> getAppointmentById(
            @PathVariable Long appointmentId) {

        return ResponseEntity.ok(
                appointmentService.getAppointmentById(appointmentId)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAllAppointments() {

        return ResponseEntity.ok(
                appointmentService.getAllAppointments()
        );
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> updateAppointment(
            @PathVariable Long appointmentId,
            @Valid @RequestBody AppointmentRequest request) {

        return ResponseEntity.ok(
                appointmentService.updateAppointment(appointmentId, request)
        );
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse<String>> deleteAppointment(
            @PathVariable Long appointmentId) {

        return ResponseEntity.ok(
                appointmentService.deleteAppointment(appointmentId)
        );
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointmentsByPatientId(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                appointmentService.getAppointmentsByPatientId(patientId)
        );
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointmentsByDoctorId(
            @PathVariable Long doctorId) {

        return ResponseEntity.ok(
                appointmentService.getAppointmentsByDoctorId(doctorId)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> searchAppointments(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                appointmentService.searchAppointments(keyword)
        );

    }
}