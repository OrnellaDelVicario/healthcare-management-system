package com.example.healthcare.repository;

import com.example.healthcare.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends MongoRepository<Patient, String> {

    // --- Custom Query Methods for Patients ---
    List<Patient> findByAgeGreaterThan(int age);
    List<Patient> findByGenderIgnoreCase(String gender);

    // @Query Annotation Example (for name containing a keyword, case-insensitive)
    @Query("{ 'name' : { $regex: ?0, $options: 'i' } }")
    List<Patient> findByNameContainingCustomQuery(String nameKeyword);

    // Optional: If you need to find by email exactly, for uniqueness checks later
    Optional<Patient> findByEmail(String email);

}
