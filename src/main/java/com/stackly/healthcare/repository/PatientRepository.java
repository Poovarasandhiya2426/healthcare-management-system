package com.stackly.healthcare.repository;

import com.stackly.healthcare.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Check whether Email already exists
     */
    boolean existsByEmail(String email);

    /**
     * Check whether Mobile Number already exists
     */
    boolean existsByMobileNumber(String mobileNumber);

    /**
     * Find Patient by Email
     */
    Optional<Patient> findByEmail(String email);

    /**
     * JPQL Query
     * Uses Entity and Java field names.
     */
    @Query("""
            SELECT p
            FROM Patient p
            WHERE
            LOWER(p.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR
            LOWER(p.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR
            p.mobileNumber LIKE CONCAT('%', :keyword, '%')
            """)
    List<Patient> searchPatients(String keyword);

    /**
     * Native SQL Query
     * Uses actual database table and column names.
     */
    @Query(value = """
            SELECT *
            FROM patients
            WHERE
            LOWER(first_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR
            LOWER(last_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR
            mobile_number LIKE CONCAT('%', :keyword, '%')
            """, nativeQuery = true)
    List<Patient> searchPatientsNative(String keyword);

}