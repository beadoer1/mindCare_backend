package com.sparta.mindcare.repository;

import com.sparta.mindcare.model.Appointment;
import com.sparta.mindcare.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByUserId(Long userId);
    List<Appointment> findByDateAndDoctor(LocalDate date , Doctor doctor);

}
