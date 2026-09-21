package com.stackly.healthcare.controller;

import com.stackly.healthcare.common.ApiResponse;
import com.stackly.healthcare.request.PatientRequest;
import com.stackly.healthcare.response.PatientResponse;
import com.stackly.healthcare.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<ApiResponse<PatientResponse>> createPatient(
            @Valid @RequestBody PatientRequest request) {

        return new ResponseEntity<>(
                patientService.createPatient(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<ApiResponse<PatientResponse>> getPatientById(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                patientService.getPatientById(patientId)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PatientResponse>>> getAllPatients() {

        return ResponseEntity.ok(
                patientService.getAllPatients()
        );
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<ApiResponse<PatientResponse>> updatePatient(
            @PathVariable Long patientId,
            @Valid @RequestBody PatientRequest request) {

        return ResponseEntity.ok(
                patientService.updatePatient(patientId, request)
        );
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<ApiResponse<String>> deletePatient(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                patientService.deletePatient(patientId)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<PatientResponse>>> searchPatients(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                patientService.searchPatients(keyword)
        );

    }

    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<PatientResponse>>> getPatientsWithPagination(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(required = false) String sortBy,

            @RequestParam(required = false) String direction) {

        return ResponseEntity.ok(
                patientService.getPatientsWithPagination(
                        page,
                        size,
                        sortBy,
                        direction
                )
        );
    }
}