package com.stackly.healthcare.controller;

import com.stackly.healthcare.common.ApiResponse;
import com.stackly.healthcare.request.MedicalRecordRequest;
import com.stackly.healthcare.response.MedicalRecordResponse;
import com.stackly.healthcare.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<ApiResponse<MedicalRecordResponse>> createMedicalRecord(
            @Valid @RequestBody MedicalRecordRequest request) {

        return new ResponseEntity<>(
                medicalRecordService.createMedicalRecord(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<ApiResponse<MedicalRecordResponse>> getMedicalRecordById(
            @PathVariable Long recordId) {

        return ResponseEntity.ok(
                medicalRecordService.getMedicalRecordById(recordId)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MedicalRecordResponse>>> getAllMedicalRecords() {

        return ResponseEntity.ok(
                medicalRecordService.getAllMedicalRecords()
        );
    }

    @PutMapping("/{recordId}")
    public ResponseEntity<ApiResponse<MedicalRecordResponse>> updateMedicalRecord(
            @PathVariable Long recordId,
            @Valid @RequestBody MedicalRecordRequest request) {

        return ResponseEntity.ok(
                medicalRecordService.updateMedicalRecord(recordId, request)
        );
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<ApiResponse<String>> deleteMedicalRecord(
            @PathVariable Long recordId) {

        return ResponseEntity.ok(
                medicalRecordService.deleteMedicalRecord(recordId)
        );
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponse<List<MedicalRecordResponse>>> getMedicalRecordsByPatientId(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                medicalRecordService.getMedicalRecordsByPatientId(patientId)
        );
    }
}