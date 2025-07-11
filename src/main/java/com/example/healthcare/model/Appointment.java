package com.example.healthcare.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "appointments")
public class Appointment {

    @Id
    private String id;

    @NotNull(message = "Appointment date and time cannot be null")
    private LocalDateTime dateTime;

    @NotBlank(message = "Reason for appointment cannot be empty")
    private String reason;

    @NotBlank(message = "Patient ID cannot be empty")
    private String patientId;

    @NotBlank(message = "Doctor ID cannot be empty")
    private String doctorId;
}