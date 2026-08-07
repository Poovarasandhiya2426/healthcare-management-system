package com.stackly.healthcare.repository;

import com.stackly.healthcare.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

    Optional<Doctor> findByEmail(String email);

    @Query("""
SELECT d
FROM Doctor d
WHERE
LOWER(d.doctorName) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR
LOWER(d.specialization) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR
d.mobileNumber LIKE CONCAT('%', :keyword, '%')
""")
    List<Doctor> searchDoctors(String keyword);

}