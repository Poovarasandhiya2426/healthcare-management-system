package com.stackly.healthcare.service;

import com.stackly.healthcare.common.ApiResponse;
import com.stackly.healthcare.request.PrescriptionRequest;
import com.stackly.healthcare.response.PrescriptionResponse;

import java.util.List;

public interface PrescriptionService {

    ApiResponse<PrescriptionResponse> createPrescription(PrescriptionRequest request);

    ApiResponse<PrescriptionResponse> getPrescriptionById(Long prescriptionId);

    ApiResponse<List<PrescriptionResponse>> getAllPrescriptions();

    ApiResponse<PrescriptionResponse> updatePrescription(Long prescriptionId,
                                                         PrescriptionRequest request);

    ApiResponse<String> deletePrescription(Long prescriptionId);

    ApiResponse<List<PrescriptionResponse>> getPrescriptionsByAppointmentId(Long appointmentId);

}