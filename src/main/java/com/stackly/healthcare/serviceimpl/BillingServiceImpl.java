package com.stackly.healthcare.serviceimpl;

import com.stackly.healthcare.common.ApiResponse;
import com.stackly.healthcare.constants.AppConstants;
import com.stackly.healthcare.entity.Billing;
import com.stackly.healthcare.entity.Patient;
import com.stackly.healthcare.exception.ResourceNotFoundException;
import com.stackly.healthcare.mapper.BillingMapper;
import com.stackly.healthcare.repository.BillingRepository;
import com.stackly.healthcare.repository.PatientRepository;
import com.stackly.healthcare.request.BillingRequest;
import com.stackly.healthcare.response.BillingResponse;
import com.stackly.healthcare.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final BillingRepository billingRepository;
    private final PatientRepository patientRepository;
    private final BillingMapper billingMapper;

    @Override
    public ApiResponse<BillingResponse> createBilling(BillingRequest request) {

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(AppConstants.PATIENT_NOT_FOUND));

        Double totalAmount = request.getConsultationFee()
                + request.getMedicineCharge()
                + request.getLabCharge()
                + request.getOtherCharge();

        Billing billing = billingMapper.mapToEntity(
                request,
                patient,
                totalAmount
        );

        Billing savedBilling = billingRepository.save(billing);

        return ApiResponse.<BillingResponse>builder()
                .success(true)
                .message(AppConstants.BILLING_CREATED)
                .data(billingMapper.mapToResponse(savedBilling))
                .build();
    }

    @Override
    public ApiResponse<BillingResponse> getBillingById(Long billId) {

        Billing billing = billingRepository.findById(billId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AppConstants.BILLING_NOT_FOUND));

        return ApiResponse.<BillingResponse>builder()
                .success(true)
                .message(AppConstants.BILLING_FOUND)
                .data(billingMapper.mapToResponse(billing))
                .build();
    }

    @Override
    public ApiResponse<List<BillingResponse>> getAllBillings() {

        List<BillingResponse> billings = billingRepository.findAll()
                .stream()
                .map(billingMapper::mapToResponse)
                .collect(Collectors.toList());

        return ApiResponse.<List<BillingResponse>>builder()
                .success(true)
                .message(AppConstants.BILLING_LIST)
                .data(billings)
                .build();
    }

    @Override
    public ApiResponse<BillingResponse> updateBilling(
            Long billId,
            BillingRequest request) {

        Billing billing = billingRepository.findById(billId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AppConstants.BILLING_NOT_FOUND));

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(AppConstants.PATIENT_NOT_FOUND));

        Double totalAmount = request.getConsultationFee()
                + request.getMedicineCharge()
                + request.getLabCharge()
                + request.getOtherCharge();

        billingMapper.updateEntity(
                billing,
                request,
                patient,
                totalAmount
        );

        Billing updatedBilling = billingRepository.save(billing);

        return ApiResponse.<BillingResponse>builder()
                .success(true)
                .message(AppConstants.BILLING_UPDATED)
                .data(billingMapper.mapToResponse(updatedBilling))
                .build();
    }

    @Override
    public ApiResponse<String> deleteBilling(Long billId) {

        Billing billing = billingRepository.findById(billId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AppConstants.BILLING_NOT_FOUND));

        billingRepository.delete(billing);

        return ApiResponse.<String>builder()
                .success(true)
                .message(AppConstants.BILLING_DELETED)
                .data(AppConstants.BILLING_DELETED)
                .build();
    }

    @Override
    public ApiResponse<List<BillingResponse>> getBillingsByPatientId(
            Long patientId) {

        List<BillingResponse> billings = billingRepository
                .findByPatientPatientId(patientId)
                .stream()
                .map(billingMapper::mapToResponse)
                .collect(Collectors.toList());

        return ApiResponse.<List<BillingResponse>>builder()
                .success(true)
                .message(AppConstants.BILLING_LIST)
                .data(billings)
                .build();
    }
}