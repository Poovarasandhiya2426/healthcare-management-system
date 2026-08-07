package com.stackly.healthcare.mapper;

import com.stackly.healthcare.entity.Doctor;
import com.stackly.healthcare.request.DoctorRequest;
import com.stackly.healthcare.response.DoctorResponse;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public Doctor mapToEntity(DoctorRequest request) {

        return Doctor.builder()
                .doctorName(request.getDoctorName())
                .specialization(request.getSpecialization())
                .email(request.getEmail())
                .mobileNumber(request.getMobileNumber())
                .experience(request.getExperience())
                .consultationFee(request.getConsultationFee())
                .qualification(request.getQualification())
                .hospitalName(request.getHospitalName())
                .build();
    }

    public DoctorResponse mapToResponse(Doctor doctor) {

        return DoctorResponse.builder()
                .doctorId(doctor.getDoctorId())
                .doctorName(doctor.getDoctorName())
                .specialization(doctor.getSpecialization())
                .email(doctor.getEmail())
                .mobileNumber(doctor.getMobileNumber())
                .experience(doctor.getExperience())
                .consultationFee(doctor.getConsultationFee())
                .qualification(doctor.getQualification())
                .hospitalName(doctor.getHospitalName())
                .createdAt(doctor.getCreatedAt())
                .updatedAt(doctor.getUpdatedAt())
                .build();
    }

    public void updateEntity(DoctorRequest request, Doctor doctor) {

        doctor.setDoctorName(request.getDoctorName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setEmail(request.getEmail());
        doctor.setMobileNumber(request.getMobileNumber());
        doctor.setExperience(request.getExperience());
        doctor.setConsultationFee(request.getConsultationFee());
        doctor.setQualification(request.getQualification());
        doctor.setHospitalName(request.getHospitalName());
    }

}