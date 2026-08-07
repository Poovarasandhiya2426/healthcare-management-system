package com.stackly.healthcare.mapper;

import com.stackly.healthcare.entity.MedicalRecord;
import com.stackly.healthcare.entity.Patient;
import com.stackly.healthcare.request.MedicalRecordRequest;
import com.stackly.healthcare.response.MedicalRecordResponse;
import org.springframework.stereotype.Component;

@Component
public class MedicalRecordMapper {

    public MedicalRecord mapToEntity(MedicalRecordRequest request, Patient patient) {

        return MedicalRecord.builder()
                .patient(patient)
                .diagnosis(request.getDiagnosis())
                .treatment(request.getTreatment())
                .allergies(request.getAllergies())
                .medicalHistory(request.getMedicalHistory())
                .recordDate(request.getRecordDate())
                .build();
    }

    public MedicalRecordResponse mapToResponse(MedicalRecord medicalRecord) {

        return MedicalRecordResponse.builder()
                .recordId(medicalRecord.getRecordId())
                .patientId(medicalRecord.getPatient().getPatientId())
                .patientName(
                        medicalRecord.getPatient().getFirstName() + " " +
                                medicalRecord.getPatient().getLastName()
                )
                .diagnosis(medicalRecord.getDiagnosis())
                .treatment(medicalRecord.getTreatment())
                .allergies(medicalRecord.getAllergies())
                .medicalHistory(medicalRecord.getMedicalHistory())
                .recordDate(medicalRecord.getRecordDate())
                .createdAt(medicalRecord.getCreatedAt())
                .updatedAt(medicalRecord.getUpdatedAt())
                .build();
    }

    public void updateEntity(MedicalRecord medicalRecord,
                             MedicalRecordRequest request,
                             Patient patient) {

        medicalRecord.setPatient(patient);
        medicalRecord.setDiagnosis(request.getDiagnosis());
        medicalRecord.setTreatment(request.getTreatment());
        medicalRecord.setAllergies(request.getAllergies());
        medicalRecord.setMedicalHistory(request.getMedicalHistory());
        medicalRecord.setRecordDate(request.getRecordDate());
    }
}