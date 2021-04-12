package com.sparta.mindcare.repository;

import com.sparta.mindcare.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
}
