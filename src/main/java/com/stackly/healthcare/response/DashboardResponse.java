package com.stackly.healthcare.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private Long totalPatients;

    private Long totalDoctors;

    private Long totalAppointments;

    private Double totalRevenue;

}