package com.sparta.mindcare.repository;

import com.sparta.mindcare.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    List<Doctor> findAllBySpecialties(String specialty);

}
