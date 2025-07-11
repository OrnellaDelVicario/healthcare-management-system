package com.example.healthcare.repository;

import com.example.healthcare.model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface DoctorRepository extends MongoRepository<Doctor, String> {

    // --- Custom Query Methods (Derived Query Methods) ---

    // Find doctors by specialization (case-insensitive)
    List<Doctor> findBySpecializationIgnoreCase(String specialization);

    // Find doctors by years of experience greater than a certain value
    List<Doctor> findByYearsOfExperienceGreaterThan(int years);

    // Find doctors whose name contains a keyword (case-insensitive)
    @Query("{ 'name' : { $regex: ?0, $options: 'i' } }")
    List<Doctor> findByNameContainingIgnoreCase(String keyword);

    // Find doctors by email
    Optional<Doctor> findByEmail(String email);
}