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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final BillingRepository billingRepository;
    private final PatientRepository patientRepository;
    private final BillingMapper billingMapper;

    @Override
    public ApiResponse<BillingResponse> createBilling(
            BillingRequest request) {

        log.info(
                "Creating billing for patient ID: {}",
                request.getPatientId()
        );

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> {

                    log.warn(
                            "Billing creation failed. Patient not found with ID: {}",
                            request.getPatientId()
                    );

                    return new ResourceNotFoundException(
                            AppConstants.PATIENT_NOT_FOUND
                    );
                });

        Double totalAmount = request.getConsultationFee()
                + request.getMedicineCharge()
                + request.getLabCharge()
                + request.getOtherCharge();

        log.info(
                "Calculated total billing amount: {} for patient ID: {}",
                totalAmount,
                request.getPatientId()
        );

        Billing billing = billingMapper.mapToEntity(
                request,
                patient,
                totalAmount
        );

        Billing savedBilling = billingRepository.save(billing);

        log.info(
                "Billing created successfully with ID: {}",
                savedBilling.getBillId()
        );

        return ApiResponse.<BillingResponse>builder()
                .success(true)
                .message(AppConstants.BILLING_CREATED)
                .data(billingMapper.mapToResponse(savedBilling))
                .build();
    }

    @Override
    public ApiResponse<BillingResponse> getBillingById(Long billId) {

        log.info("Fetching billing with ID: {}", billId);

        Billing billing = billingRepository.findById(billId)
                .orElseThrow(() -> {

                    log.warn(
                            "Billing not found with ID: {}",
                            billId
                    );

                    return new ResourceNotFoundException(
                            AppConstants.BILLING_NOT_FOUND
                    );
                });

        log.info(
                "Billing found successfully with ID: {}",
                billId
        );

        return ApiResponse.<BillingResponse>builder()
                .success(true)
                .message(AppConstants.BILLING_FOUND)
                .data(billingMapper.mapToResponse(billing))
                .build();
    }

    @Override
    public ApiResponse<List<BillingResponse>> getAllBillings() {

        log.info("Fetching all billings");

        List<BillingResponse> billings =
                billingRepository.findAll()
                        .stream()
                        .map(billingMapper::mapToResponse)
                        .collect(Collectors.toList());

        log.info(
                "Successfully fetched {} billings",
                billings.size()
        );

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

        log.info(
                "Updating billing with ID: {}",
                billId
        );

        Billing billing = billingRepository.findById(billId)
                .orElseThrow(() -> {

                    log.warn(
                            "Billing update failed. Billing not found with ID: {}",
                            billId
                    );

                    return new ResourceNotFoundException(
                            AppConstants.BILLING_NOT_FOUND
                    );
                });

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> {

                    log.warn(
                            "Billing update failed. Patient not found with ID: {}",
                            request.getPatientId()
                    );

                    return new ResourceNotFoundException(
                            AppConstants.PATIENT_NOT_FOUND
                    );
                });

        Double totalAmount = request.getConsultationFee()
                + request.getMedicineCharge()
                + request.getLabCharge()
                + request.getOtherCharge();

        log.info(
                "Recalculated total billing amount: {} for billing ID: {}",
                totalAmount,
                billId
        );

        billingMapper.updateEntity(
                billing,
                request,
                patient,
                totalAmount
        );

        Billing updatedBilling = billingRepository.save(billing);

        log.info(
                "Billing updated successfully with ID: {}",
                updatedBilling.getBillId()
        );

        return ApiResponse.<BillingResponse>builder()
                .success(true)
                .message(AppConstants.BILLING_UPDATED)
                .data(billingMapper.mapToResponse(updatedBilling))
                .build();
    }

    @Override
    public ApiResponse<String> deleteBilling(Long billId) {

        log.info(
                "Deleting billing with ID: {}",
                billId
        );

        Billing billing = billingRepository.findById(billId)
                .orElseThrow(() -> {

                    log.warn(
                            "Billing deletion failed. Billing not found with ID: {}",
                            billId
                    );

                    return new ResourceNotFoundException(
                            AppConstants.BILLING_NOT_FOUND
                    );
                });

        billingRepository.delete(billing);

        log.info(
                "Billing deleted successfully with ID: {}",
                billId
        );

        return ApiResponse.<String>builder()
                .success(true)
                .message(AppConstants.BILLING_DELETED)
                .data(AppConstants.BILLING_DELETED)
                .build();
    }

    @Override
    public ApiResponse<List<BillingResponse>> getBillingsByPatientId(
            Long patientId) {

        log.info(
                "Fetching billings for patient ID: {}",
                patientId
        );

        List<BillingResponse> billings =
                billingRepository
                        .findByPatientPatientId(patientId)
                        .stream()
                        .map(billingMapper::mapToResponse)
                        .collect(Collectors.toList());

        log.info(
                "Found {} billings for patient ID: {}",
                billings.size(),
                patientId
        );

        return ApiResponse.<List<BillingResponse>>builder()
                .success(true)
                .message(AppConstants.BILLING_LIST)
                .data(billings)
                .build();
    }
}