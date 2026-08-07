package com.stackly.healthcare.controller;

import com.stackly.healthcare.common.ApiResponse;
import com.stackly.healthcare.request.PrescriptionRequest;
import com.stackly.healthcare.response.PrescriptionResponse;
import com.stackly.healthcare.service.PrescriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PostMapping
    public ResponseEntity<ApiResponse<PrescriptionResponse>> createPrescription(
            @Valid @RequestBody PrescriptionRequest request) {

        return new ResponseEntity<>(
                prescriptionService.createPrescription(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{prescriptionId}")
    public ResponseEntity<ApiResponse<PrescriptionResponse>> getPrescriptionById(
            @PathVariable Long prescriptionId) {

        return ResponseEntity.ok(
                prescriptionService.getPrescriptionById(prescriptionId)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PrescriptionResponse>>> getAllPrescriptions() {

        return ResponseEntity.ok(
                prescriptionService.getAllPrescriptions()
        );
    }

    @PutMapping("/{prescriptionId}")
    public ResponseEntity<ApiResponse<PrescriptionResponse>> updatePrescription(
            @PathVariable Long prescriptionId,
            @Valid @RequestBody PrescriptionRequest request) {

        return ResponseEntity.ok(
                prescriptionService.updatePrescription(prescriptionId, request)
        );
    }

    @DeleteMapping("/{prescriptionId}")
    public ResponseEntity<ApiResponse<String>> deletePrescription(
            @PathVariable Long prescriptionId) {

        return ResponseEntity.ok(
                prescriptionService.deletePrescription(prescriptionId)
        );
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<ApiResponse<List<PrescriptionResponse>>> getPrescriptionsByAppointmentId(
            @PathVariable Long appointmentId) {

        return ResponseEntity.ok(
                prescriptionService.getPrescriptionsByAppointmentId(appointmentId)
        );
    }
}