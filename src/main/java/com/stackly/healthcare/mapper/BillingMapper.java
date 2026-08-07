package com.stackly.healthcare.mapper;

import com.stackly.healthcare.entity.Billing;
import com.stackly.healthcare.entity.Patient;
import com.stackly.healthcare.request.BillingRequest;
import com.stackly.healthcare.response.BillingResponse;
import org.springframework.stereotype.Component;

@Component
public class BillingMapper {

    public Billing mapToEntity(BillingRequest request,
                               Patient patient,
                               Double totalAmount) {

        return Billing.builder()
                .patient(patient)
                .consultationFee(request.getConsultationFee())
                .medicineCharge(request.getMedicineCharge())
                .labCharge(request.getLabCharge())
                .otherCharge(request.getOtherCharge())
                .totalAmount(totalAmount)
                .paymentStatus(request.getPaymentStatus())
                .paymentMethod(request.getPaymentMethod())
                .billDate(request.getBillDate())
                .build();
    }

    public BillingResponse mapToResponse(Billing billing) {

        return BillingResponse.builder()
                .billId(billing.getBillId())
                .patientId(billing.getPatient().getPatientId())
                .patientName(
                        billing.getPatient().getFirstName() + " " +
                                billing.getPatient().getLastName()
                )
                .consultationFee(billing.getConsultationFee())
                .medicineCharge(billing.getMedicineCharge())
                .labCharge(billing.getLabCharge())
                .otherCharge(billing.getOtherCharge())
                .totalAmount(billing.getTotalAmount())
                .paymentStatus(billing.getPaymentStatus())
                .paymentMethod(billing.getPaymentMethod())
                .billDate(billing.getBillDate())
                .createdAt(billing.getCreatedAt())
                .updatedAt(billing.getUpdatedAt())
                .build();
    }

    public void updateEntity(Billing billing,
                             BillingRequest request,
                             Patient patient,
                             Double totalAmount) {

        billing.setPatient(patient);
        billing.setConsultationFee(request.getConsultationFee());
        billing.setMedicineCharge(request.getMedicineCharge());
        billing.setLabCharge(request.getLabCharge());
        billing.setOtherCharge(request.getOtherCharge());
        billing.setTotalAmount(totalAmount);
        billing.setPaymentStatus(request.getPaymentStatus());
        billing.setPaymentMethod(request.getPaymentMethod());
        billing.setBillDate(request.getBillDate());
    }
}