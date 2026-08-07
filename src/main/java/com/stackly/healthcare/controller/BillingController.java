package com.stackly.healthcare.controller;

import com.stackly.healthcare.common.ApiResponse;
import com.stackly.healthcare.request.BillingRequest;
import com.stackly.healthcare.response.BillingResponse;
import com.stackly.healthcare.service.BillingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billings")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @PostMapping
    public ResponseEntity<ApiResponse<BillingResponse>> createBilling(
            @Valid @RequestBody BillingRequest request) {

        return new ResponseEntity<>(
                billingService.createBilling(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{billId}")
    public ResponseEntity<ApiResponse<BillingResponse>> getBillingById(
            @PathVariable Long billId) {

        return ResponseEntity.ok(
                billingService.getBillingById(billId)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BillingResponse>>> getAllBillings() {

        return ResponseEntity.ok(
                billingService.getAllBillings()
        );
    }

    @PutMapping("/{billId}")
    public ResponseEntity<ApiResponse<BillingResponse>> updateBilling(
            @PathVariable Long billId,
            @Valid @RequestBody BillingRequest request) {

        return ResponseEntity.ok(
                billingService.updateBilling(billId, request)
        );
    }

    @DeleteMapping("/{billId}")
    public ResponseEntity<ApiResponse<String>> deleteBilling(
            @PathVariable Long billId) {

        return ResponseEntity.ok(
                billingService.deleteBilling(billId)
        );
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponse<List<BillingResponse>>> getBillingsByPatientId(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                billingService.getBillingsByPatientId(patientId)
        );
    }
}