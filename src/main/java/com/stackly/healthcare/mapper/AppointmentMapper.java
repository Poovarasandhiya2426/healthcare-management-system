package com.stackly.healthcare.mapper;

import com.stackly.healthcare.entity.Appointment;
import com.stackly.healthcare.entity.Doctor;
import com.stackly.healthcare.entity.Patient;
import com.stackly.healthcare.request.AppointmentRequest;
import com.stackly.healthcare.response.AppointmentResponse;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public Appointment mapToEntity(AppointmentRequest request, Patient patient, Doctor doctor) {

        return Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentDate(request.getAppointmentDate())
                .appointmentTime(request.getAppointmentTime())
                .reason(request.getReason())
                .status(request.getStatus())
                .build();
    }

    public AppointmentResponse mapToResponse(Appointment appointment) {

        return AppointmentResponse.builder()
                .appointmentId(appointment.getAppointmentId())
                .patientId(appointment.getPatient().getPatientId())
                .patientName(appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName())
                .doctorId(appointment.getDoctor().getDoctorId())
                .doctorName(appointment.getDoctor().getDoctorName())
                .specialization(appointment.getDoctor().getSpecialization())
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())
                .reason(appointment.getReason())
                .status(appointment.getStatus())
                .createdAt(appointment.getCreatedAt())
                .updatedAt(appointment.getUpdatedAt())
                .build();
    }

    public void updateEntity(Appointment appointment, AppointmentRequest request, Patient patient, Doctor doctor) {

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());
        appointment.setStatus(request.getStatus());
    }

}