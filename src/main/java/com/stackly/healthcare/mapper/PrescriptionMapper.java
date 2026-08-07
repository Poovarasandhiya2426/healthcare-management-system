package com.stackly.healthcare.mapper;

import com.stackly.healthcare.entity.Appointment;
import com.stackly.healthcare.entity.Prescription;
import com.stackly.healthcare.request.PrescriptionRequest;
import com.stackly.healthcare.response.PrescriptionResponse;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionMapper {

    public Prescription mapToEntity(PrescriptionRequest request, Appointment appointment) {

        return Prescription.builder()
                .appointment(appointment)
                .medicineName(request.getMedicineName())
                .dosage(request.getDosage())
                .frequency(request.getFrequency())
                .durationInDays(request.getDurationInDays())
                .instructions(request.getInstructions())
                .prescriptionDate(request.getPrescriptionDate())
                .build();
    }

    public PrescriptionResponse mapToResponse(Prescription prescription) {

        return PrescriptionResponse.builder()
                .prescriptionId(prescription.getPrescriptionId())
                .appointmentId(prescription.getAppointment().getAppointmentId())
                .patientId(prescription.getAppointment().getPatient().getPatientId())
                .patientName(
                        prescription.getAppointment().getPatient().getFirstName() + " " +
                                prescription.getAppointment().getPatient().getLastName()
                )
                .doctorId(prescription.getAppointment().getDoctor().getDoctorId())
                .doctorName(prescription.getAppointment().getDoctor().getDoctorName())
                .medicineName(prescription.getMedicineName())
                .dosage(prescription.getDosage())
                .frequency(prescription.getFrequency())
                .durationInDays(prescription.getDurationInDays())
                .instructions(prescription.getInstructions())
                .prescriptionDate(prescription.getPrescriptionDate())
                .createdAt(prescription.getCreatedAt())
                .updatedAt(prescription.getUpdatedAt())
                .build();
    }

    public void updateEntity(Prescription prescription,
                             PrescriptionRequest request,
                             Appointment appointment) {

        prescription.setAppointment(appointment);
        prescription.setMedicineName(request.getMedicineName());
        prescription.setDosage(request.getDosage());
        prescription.setFrequency(request.getFrequency());
        prescription.setDurationInDays(request.getDurationInDays());
        prescription.setInstructions(request.getInstructions());
        prescription.setPrescriptionDate(request.getPrescriptionDate());
    }
}