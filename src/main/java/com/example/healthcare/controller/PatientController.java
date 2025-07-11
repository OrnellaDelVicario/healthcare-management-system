package com.example.healthcare.controller;

import com.example.healthcare.model.Patient;
import com.example.healthcare.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/patients")

public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * POST /api/patients
     * Create new patient.
     * @param patient the object Patient to crate, automatically validated by @Valid
     * @return Created Patient with state HTTP 201 (Created).
     */

    @PostMapping
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient) {
        Patient newPatient = patientService.createPatient(patient);
        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }

    /**
     * GET /api/patients
     * Recupera todos los pacientes.
     * @return Una lista de todos los pacientes con estado HTTP 200 (OK).
     */

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    /**
     * GET /api/patients/{id}
     * Retrieves a patient by ID.
     * @param id Patient's ID.
     * @return Patient if state  HHTP 200 (OK) or 404 (Not Found).
     */

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable String id) {
        return patientService.getPatientById(id)
                .map(patient -> new ResponseEntity<>(patient, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * PUT /api/patients/{id}
     * Updates an existing patient.
     * @param id The ID of the patient to update (from the URL path).
     * @param updatedPatient The Patient object with updated information (from the request body).
     * @return The updated patient with HTTP status 200 (OK), or 404 (Not Found) if the ID does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable String id, @Valid @RequestBody Patient updatedPatient) {
        try {
            Patient patient = patientService.updatePatient(id, updatedPatient);
            return new ResponseEntity<>(patient, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * DELETE /api/patients/{id}
     * Deletes a patient by their ID.
     * @param id The ID of the patient to delete.
     * @return HTTP status 204 (No Content) on success, or 404 (Not Found) if the patient does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
        try {
            patientService.deletePatient(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // --- Custom Query Endpoints ---

    /**
     * GET /api/patients/age-above/{age}
     * Finds patients older than a specified age.
     * @param age The minimum age.
     * @return A list of patients with HTTP status 200 (OK).
     */
    @GetMapping("/age-above/{age}")
    public ResponseEntity<List<Patient>> getPatientsByAgeGreaterThan(@PathVariable int age) {
        List<Patient> patients = patientService.findPatientsByAgeGreaterThan(age);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    /**
     * GET /api/patients/gender/{gender}
     * Finds patients by a specified gender (case-insensitive).
     * @param gender The gender to search for.
     * @return A list of patients with HTTP status 200 (OK).
     */
    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<Patient>> getPatientsByGender(@PathVariable String gender) {
        List<Patient> patients = patientService.findPatientsByGender(gender);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    /**
     * GET /api/patients/search-by-name?keyword=value
     * Finds patients whose name contains a given keyword (case-insensitive) using the custom @Query method.
     * @param keyword The keyword to search for in the patient's name.
     * @return A list of matching patients with HTTP status 200 (OK).
     */
    @GetMapping("/search-by-name")
    public ResponseEntity<List<Patient>> searchPatientsByName(@RequestParam String keyword) {
        List<Patient> patients = patientService.searchPatientsByName(keyword);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }


}
