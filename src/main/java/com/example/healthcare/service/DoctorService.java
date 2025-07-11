package com.example.healthcare.service;

import com.example.healthcare.model.Doctor;
import com.example.healthcare.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository; // Declare the repository

    // Constructor for dependency injection of DoctorRepository
    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    // --- CRUD Operations ---

    /**
     * Creates a new doctor.
     * @param doctor The doctor object to save.
     * @return The saved doctor with its generated ID.
     */
    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    /**
     * Retrieves all doctors.
     * @return A list of all doctors.
     */
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    /**
     * Retrieves a doctor by their ID.
     * @param id The ID of the doctor to retrieve.
     * @return An Optional containing the doctor if found, or empty if not.
     */
    public Optional<Doctor> getDoctorById(String id) {
        return doctorRepository.findById(id);
    }

    /**
     * Updates an existing doctor.
     * @param id The ID of the doctor to update.
     * @param updatedDoctor The doctor object with updated information.
     * @return The updated doctor.
     * @throws RuntimeException if the doctor with the given ID is not found.
     */
    public Doctor updateDoctor(String id, Doctor updatedDoctor) {
        // Check if the doctor exists before updating
        return doctorRepository.findById(id)
                .map(doctor -> {
                    // Update fields from updatedDoctor
                    doctor.setName(updatedDoctor.getName());
                    doctor.setSpecialization(updatedDoctor.getSpecialization());
                    doctor.setYearsOfExperience(updatedDoctor.getYearsOfExperience());
                    doctor.setEmail(updatedDoctor.getEmail());
                    doctor.setPhoneNumber(updatedDoctor.getPhoneNumber());
                    // Save the updated doctor back to the database
                    return doctorRepository.save(doctor);
                })
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id)); // Throw if not found
    }

    /**
     * Deletes a doctor by their ID.
     * @param id The ID of the doctor to delete.
     * @throws RuntimeException if the doctor with the given ID is not found.
     */
    public void deleteDoctor(String id) {
        // Check if the doctor exists before deleting
        if (!doctorRepository.existsById(id)) {
            throw new RuntimeException("Doctor not found with ID: " + id);
        }
        doctorRepository.deleteById(id);
    }

    // --- Custom Query Methods (Delegating to Repository) ---

    /**
     * Finds doctors by specialization (case-insensitive).
     * @param specialization The specialization to search for.
     * @return A list of doctors matching the specialization.
     */
    public List<Doctor> findDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecializationIgnoreCase(specialization);
    }

    /**
     * Finds doctors by years of experience greater than a certain value.
     * @param years The minimum years of experience.
     * @return A list of doctors with more than the specified years of experience.
     */
    public List<Doctor> findDoctorsByYearsOfExperienceGreaterThan(int years) {
        return doctorRepository.findByYearsOfExperienceGreaterThan(years);
    }

    /**
     * Finds doctors whose name contains a keyword (case-insensitive).
     * @param keyword The keyword to search for in the doctor's name.
     * @return A list of doctors whose name contains the keyword.
     */
    public List<Doctor> searchDoctorsByName(String keyword) {
        return doctorRepository.findByNameContainingIgnoreCase(keyword);
    }
}