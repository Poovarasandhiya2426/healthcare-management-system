package com.stackly.healthcare.service;

import com.stackly.healthcare.common.ApiResponse;
import com.stackly.healthcare.request.BillingRequest;
import com.stackly.healthcare.response.BillingResponse;

import java.util.List;

public interface BillingService {

    ApiResponse<BillingResponse> createBilling(BillingRequest request);

    ApiResponse<BillingResponse> getBillingById(Long billId);

    ApiResponse<List<BillingResponse>> getAllBillings();

    ApiResponse<BillingResponse> updateBilling(Long billId,
                                               BillingRequest request);

    ApiResponse<String> deleteBilling(Long billId);

    ApiResponse<List<BillingResponse>> getBillingsByPatientId(Long patientId);

}