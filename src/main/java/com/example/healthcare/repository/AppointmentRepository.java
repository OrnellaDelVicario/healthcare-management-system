// File: src/main/java/com/example/healthcare/repository/AppointmentRepository.java

package com.example.healthcare.repository;

import com.example.healthcare.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository // Indicates that this interface is a repository (Spring component)
public interface AppointmentRepository extends MongoRepository<Appointment, String> {

    // --- Custom Query Methods (Derived Query Methods) ---

    // Find appointments by patient ID
    List<Appointment> findByPatientId(String patientId);

    // Find appointments by doctor ID
    List<Appointment> findByDoctorId(String doctorId);

    // Find appointments scheduled within a specific date range
    List<Appointment> findByDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    // Find appointments for a specific doctor on or after a certain date/time
    List<Appointment> findByDoctorIdAndDateTimeGreaterThanEqual(String doctorId, LocalDateTime dateTime);

    // Find appointments for a specific patient on or before a certain date/time
    List<Appointment> findByPatientIdAndDateTimeLessThanEqual(String patientId, LocalDateTime dateTime);
}