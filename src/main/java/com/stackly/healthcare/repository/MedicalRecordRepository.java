package com.stackly.healthcare.repository;

import com.stackly.healthcare.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByPatientPatientId(Long patientId);

    List<MedicalRecord> findByDiagnosis(String diagnosis);

    List<MedicalRecord> findByRecordDate(LocalDate recordDate);

}