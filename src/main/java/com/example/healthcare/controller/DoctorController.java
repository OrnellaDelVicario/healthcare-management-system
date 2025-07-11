package com.example.healthcare.controller;

import com.example.healthcare.model.Doctor; // Import the Doctor model
import com.example.healthcare.service.DoctorService; // Import the DoctorService
import org.springframework.beans.factory.annotation.Autowired; // For dependency injection
import org.springframework.http.HttpStatus; // For HTTP status codes
import org.springframework.http.ResponseEntity; // For full HTTP responses
import org.springframework.web.bind.annotation.*; // For @RestController, @RequestMapping, @PostMapping, etc.
import jakarta.validation.Valid; // For input validation

import java.util.List;
import java.util.Optional;

@RestController // Marks this class as a REST Controller, combining @Controller and @ResponseBody
@RequestMapping("/api/doctors") // Base URL for all endpoints in this controller
public class DoctorController {

    private final DoctorService doctorService; // Declare the service dependency

    // Constructor for dependency injection of DoctorService
    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // --- CRUD Endpoints ---

    /**
     * POST /api/doctors
     * Creates a new doctor.
     * @param doctor The Doctor object to create, validated automatically by @Valid.
     * @return The created doctor with HTTP status 201 (Created).
     */
    @PostMapping // Maps to POST /api/doctors
    public ResponseEntity<Doctor> createDoctor(@Valid @RequestBody Doctor doctor) {
        Doctor newDoctor = doctorService.createDoctor(doctor);
        return new ResponseEntity<>(newDoctor, HttpStatus.CREATED);
    }

    /**
     * GET /api/doctors
     * Retrieves all doctors.
     * @return A list of all doctors with HTTP status 200 (OK).
     */
    @GetMapping // Maps to GET /api/doctors
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    /**
     * GET /api/doctors/{id}
     * Retrieves a doctor by their ID.
     * @param id The ID of the doctor.
     * @return The doctor if found with HTTP status 200 (OK), or 404 (Not Found).
     */
    @GetMapping("/{id}") // Maps to GET /api/doctors/{id}
    public ResponseEntity<Doctor> getDoctorById(@PathVariable String id) {
        return doctorService.getDoctorById(id)
                .map(doctor -> new ResponseEntity<>(doctor, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * PUT /api/doctors/{id}
     * Updates an existing doctor.
     * @param id The ID of the doctor to update.
     * @param updatedDoctor The Doctor object with updated information.
     * @return The updated doctor with HTTP status 200 (OK), or 404 (Not Found) if the ID does not exist.
     */
    @PutMapping("/{id}") // Maps to PUT /api/doctors/{id}
    public ResponseEntity<Doctor> updateDoctor(@PathVariable String id, @Valid @RequestBody Doctor updatedDoctor) {
        try {
            Doctor doctor = doctorService.updateDoctor(id, updatedDoctor);
            return new ResponseEntity<>(doctor, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * DELETE /api/doctors/{id}
     * Deletes a doctor by their ID.
     * @param id The ID of the doctor to delete.
     * @return HTTP status 204 (No Content) on success, or 404 (Not Found) if the doctor does not exist.
     */
    @DeleteMapping("/{id}") // Maps to DELETE /api/doctors/{id}
    public ResponseEntity<Void> deleteDoctor(@PathVariable String id) {
        try {
            doctorService.deleteDoctor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // --- Custom Query Endpoints ---

    /**
     * GET /api/doctors/specialization/{specialization}
     * Finds doctors by a specified specialization (case-insensitive).
     * @param specialization The specialization to search for.
     * @return A list of doctors with HTTP status 200 (OK).
     */
    @GetMapping("/specialization/{specialization}") // Maps to GET /api/doctors/specialization/cardiology
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialization(@PathVariable String specialization) {
        List<Doctor> doctors = doctorService.findDoctorsBySpecialization(specialization);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    /**
     * GET /api/doctors/experience-above/{years}
     * Finds doctors with more than a specified number of years of experience.
     * @param years The minimum years of experience.
     * @return A list of doctors with HTTP status 200 (OK).
     */
    @GetMapping("/experience-above/{years}") // Maps to GET /api/doctors/experience-above/5
    public ResponseEntity<List<Doctor>> getDoctorsByExperienceGreaterThan(@PathVariable int years) {
        List<Doctor> doctors = doctorService.findDoctorsByYearsOfExperienceGreaterThan(years);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    /**
     * GET /api/doctors/search-by-name?keyword=value
     * Finds doctors whose name contains a given keyword (case-insensitive).
     * @param keyword The keyword to search for in the doctor's name.
     * @return A list of matching doctors with HTTP status 200 (OK).
     */
    @GetMapping("/search-by-name") // Maps to GET /api/doctors/search-by-name?keyword=dr.smith
    public ResponseEntity<List<Doctor>> searchDoctorsByName(@RequestParam String keyword) {
        List<Doctor> doctors = doctorService.searchDoctorsByName(keyword);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }
}