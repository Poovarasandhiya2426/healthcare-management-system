package com.stackly.healthcare.repository;

import com.stackly.healthcare.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatientPatientId(Long patientId);

    List<Appointment> findByDoctorDoctorId(Long doctorId);

    List<Appointment> findByAppointmentDate(LocalDate appointmentDate);

    boolean existsByPatientPatientId(Long patientId);

    @Query("""
SELECT a
FROM Appointment a
WHERE
LOWER(a.patient.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR
LOWER(a.doctor.doctorName) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR
LOWER(a.status) LIKE LOWER(CONCAT('%', :keyword, '%'))
""")
    List<Appointment> searchAppointments(String keyword);

}