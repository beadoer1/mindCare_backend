package com.sparta.mindcare.repository;


import com.sparta.mindcare.model.Appointment;
import com.sparta.mindcare.model.Comment;
import com.sparta.mindcare.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByDoctorId(Long doctorId);
    List<Comment> findAllByDoctorIdAndUserId(Long doctorId, Long userId);

}
