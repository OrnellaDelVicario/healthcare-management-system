package com.example.healthcare.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data // Generates getters, setters, equals(), hashCode(), and toString()
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields as arguments
@Document(collection = "doctors") // Maps this Java class to a MongoDB collection named "doctors"
public class Doctor {

    @Id // Marks this field as the primary key in MongoDB
    private String id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Specialization cannot be empty")
    private String specialization;

    @NotNull(message = "Years of experience cannot be null")
    @Min(value = 0, message = "Years of experience must be at least 0")
    private Integer yearsOfExperience;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;

}