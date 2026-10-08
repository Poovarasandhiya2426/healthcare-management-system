package com.stackly.healthcare.scheduler;

import com.stackly.healthcare.entity.Appointment;
import com.stackly.healthcare.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HealthcareScheduler {

    private final AppointmentRepository appointmentRepository;

    @Scheduled(
            cron = "0 0 0 * * *",
            zone = "Asia/Kolkata"
    )
    public void runDailyTask() {

        log.info("Daily appointment scheduler started");

        LocalDate today = LocalDate.now();

        List<Appointment> appointments =
                appointmentRepository.findByAppointmentDate(today);

        if (appointments.isEmpty()) {

            log.info("No appointments scheduled for today: {}", today);

        } else {

            log.info(
                    "Found {} appointment(s) scheduled for today: {}",
                    appointments.size(),
                    today
            );

            for (Appointment appointment : appointments) {

                log.info(
                        "Appointment ID: {}, Date: {}, Time: {}, Status: {}",
                        appointment.getAppointmentId(),
                        appointment.getAppointmentDate(),
                        appointment.getAppointmentTime(),
                        appointment.getStatus()
                );
            }
        }

        log.info("Daily appointment scheduler completed");
    }
}