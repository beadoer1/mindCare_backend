package com.sparta.mindcare.repository;

import com.sparta.mindcare.model.Appointment;
import com.sparta.mindcare.model.Doctor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUserId(Long userId, Sort sort);
    List<Appointment> findByUserIdAndCompleted(Long userId, Boolean completed, Sort sort);
    List<Appointment> findByDate(LocalDate date);
    List<Appointment> findByDateAndDoctor(LocalDate date , Doctor doctor);
    List<Appointment> findByDateAndUserId(LocalDate date , Long userId);

}
