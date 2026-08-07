package com.stackly.healthcare.repository;

import com.stackly.healthcare.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findByAppointmentAppointmentId(Long appointmentId);

    List<Prescription> findByMedicineName(String medicineName);

}