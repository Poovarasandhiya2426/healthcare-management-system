package com.stackly.healthcare.repository;

import com.stackly.healthcare.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {

    List<Billing> findByPatientPatientId(Long patientId);

    List<Billing> findByPaymentStatus(String paymentStatus);

    List<Billing> findByBillDate(LocalDate billDate);

    @Query("""
            SELECT COALESCE(SUM(b.totalAmount), 0)
            FROM Billing b
            """)
    Double getTotalRevenue();

}