package com.stackly.healthcare.service;

import com.stackly.healthcare.common.ApiResponse;
import com.stackly.healthcare.request.DoctorRequest;
import com.stackly.healthcare.response.DoctorResponse;

import java.util.List;

public interface DoctorService {

    ApiResponse<DoctorResponse> createDoctor(DoctorRequest request);

    ApiResponse<DoctorResponse> getDoctorById(Long doctorId);

    ApiResponse<List<DoctorResponse>> getAllDoctors();

    ApiResponse<DoctorResponse> updateDoctor(Long doctorId, DoctorRequest request);

    ApiResponse<String> deleteDoctor(Long doctorId);

    ApiResponse<List<DoctorResponse>> searchDoctors(String keyword);

}