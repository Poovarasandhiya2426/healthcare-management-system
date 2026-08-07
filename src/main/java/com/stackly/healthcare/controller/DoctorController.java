package com.stackly.healthcare.controller;

import com.stackly.healthcare.common.ApiResponse;
import com.stackly.healthcare.request.DoctorRequest;
import com.stackly.healthcare.response.DoctorResponse;
import com.stackly.healthcare.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<ApiResponse<DoctorResponse>> createDoctor(
            @Valid @RequestBody DoctorRequest request) {

        return new ResponseEntity<>(
                doctorService.createDoctor(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<ApiResponse<DoctorResponse>> getDoctorById(
            @PathVariable Long doctorId) {

        return ResponseEntity.ok(
                doctorService.getDoctorById(doctorId)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getAllDoctors() {

        return ResponseEntity.ok(
                doctorService.getAllDoctors()
        );
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<ApiResponse<DoctorResponse>> updateDoctor(
            @PathVariable Long doctorId,
            @Valid @RequestBody DoctorRequest request) {

        return ResponseEntity.ok(
                doctorService.updateDoctor(doctorId, request)
        );
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<ApiResponse<String>> deleteDoctor(
            @PathVariable Long doctorId) {

        return ResponseEntity.ok(
                doctorService.deleteDoctor(doctorId)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> searchDoctors(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                doctorService.searchDoctors(keyword)
        );

    }
}