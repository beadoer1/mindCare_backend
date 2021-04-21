package com.sparta.mindcare.repository;

import com.sparta.mindcare.model.Star;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarRepository extends JpaRepository<Star,Long> {
    Star findByDoctorId(Long doctorId);
}
