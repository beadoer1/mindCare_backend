package com.sparta.mindcare.service;

import com.sparta.mindcare.dto.CommentDto;
import com.sparta.mindcare.model.Comment;
import com.sparta.mindcare.model.Doctor;
import com.sparta.mindcare.model.User;
import com.sparta.mindcare.repository.CommentRepository;
import com.sparta.mindcare.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final DoctorRepository doctorRepository;
    @Transactional
    public void createComment(CommentDto commentDto, User user){

        Long doctorId=commentDto.getDoctorId();

        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(
                () -> new IllegalArgumentException("해당 Id의 상담사가 존재하지 않습니다.")
        );
        String writing = commentDto.getWriting();
        Comment comment = new Comment(writing, doctor, user);
        commentRepository.save(comment);


    }

}
