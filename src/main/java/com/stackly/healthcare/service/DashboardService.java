package com.stackly.healthcare.service;

import com.stackly.healthcare.response.DashboardResponse;
import com.stackly.healthcare.common.ApiResponse;

public interface DashboardService {

    ApiResponse<DashboardResponse> getDashboardSummary();

}