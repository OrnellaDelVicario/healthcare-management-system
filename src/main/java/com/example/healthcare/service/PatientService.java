package com.example.healthcare.service;

import com.example.healthcare.model.Patient;
import com.example.healthcare.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository){ // Constructor
        this.patientRepository = patientRepository;
    }

    // --- CRUD Operations ---

    public Patient createPatient (Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(String id) {
        return patientRepository.findById(id);
    }

    public Patient updatePatient(String id, Patient updatedPatient) {
        return patientRepository.findById(id)
                .map(existingPatient -> {
                    existingPatient.setName(updatedPatient.getName());
                    existingPatient.setAge(updatedPatient.getAge());
                    existingPatient.setGender(updatedPatient.getGender());
                    existingPatient.setEmail(updatedPatient.getEmail());
                    existingPatient.setPhoneNumber(updatedPatient.getPhoneNumber());
                    return patientRepository.save(existingPatient);
                })
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + id));
    }


    public void deletePatient(String id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found with ID: " + id);
        }
        patientRepository.deleteById(id);
    }

    // --- Custom Query Service Methods ---

    public List<Patient> findPatientsByAgeGreaterThan(int age) {
        return patientRepository.findByAgeGreaterThan(age);
    }

    public List<Patient> findPatientsByGender(String gender) {
        return patientRepository.findByGenderIgnoreCase(gender);
    }

    public List<Patient> searchPatientsByName(String keyword) {
        return patientRepository.findByNameContainingCustomQuery(keyword);
    }
}