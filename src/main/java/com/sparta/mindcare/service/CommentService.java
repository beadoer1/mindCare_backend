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

    @Transactional
    public void createComment(CommentDto commentDto){

        Comment comment = new Comment(commentDto);
        commentRepository.save(comment);
    }

    @Transactional
    public boolean updateComment(CommentDto commentDto){

        Long commentId= commentDto.getId();
        User user = commentDto.getUser();
        Comment comment =commentRepository.findById(commentId).orElseThrow(
                ()->new NullPointerException("해당 후기가 존재하지 않습니다.")
        );
        if(comment.getUser().equals(user)){
            comment.update(commentDto);
            return true;
        }
        else
            return false;
    }
}
