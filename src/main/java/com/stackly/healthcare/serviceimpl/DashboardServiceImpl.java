package com.stackly.healthcare.serviceimpl;

import com.stackly.healthcare.constants.AppConstants;
import com.stackly.healthcare.repository.AppointmentRepository;
import com.stackly.healthcare.repository.BillingRepository;
import com.stackly.healthcare.repository.DoctorRepository;
import com.stackly.healthcare.repository.PatientRepository;
import com.stackly.healthcare.common.ApiResponse;
import com.stackly.healthcare.response.DashboardResponse;
import com.stackly.healthcare.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final PatientRepository patientRepository;

    private final DoctorRepository doctorRepository;

    private final AppointmentRepository appointmentRepository;

    private final BillingRepository billingRepository;

    @Override
    public ApiResponse<DashboardResponse> getDashboardSummary() {

        log.info("Fetching dashboard summary");

        Long totalPatients = patientRepository.count();

        Long totalDoctors = doctorRepository.count();

        Long totalAppointments = appointmentRepository.count();

        Double totalRevenue = billingRepository.getTotalRevenue();

        if (totalRevenue == null) {

            log.info("No billing revenue found. Setting total revenue to 0.0");

            totalRevenue = 0.0;
        }

        DashboardResponse dashboardResponse =
                DashboardResponse.builder()
                        .totalPatients(totalPatients)
                        .totalDoctors(totalDoctors)
                        .totalAppointments(totalAppointments)
                        .totalRevenue(totalRevenue)
                        .build();

        log.info(
                "Dashboard summary retrieved successfully. Patients: {}, Doctors: {}, Appointments: {}, Revenue: {}",
                totalPatients,
                totalDoctors,
                totalAppointments,
                totalRevenue
        );

        return ApiResponse.<DashboardResponse>builder()
                .success(true)
                .message(AppConstants.DASHBOARD_RETRIEVED)
                .data(dashboardResponse)
                .build();
    }
}