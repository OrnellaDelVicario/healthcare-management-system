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


}
