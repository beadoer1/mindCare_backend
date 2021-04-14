package com.sparta.mindcare.controller;

import com.sparta.mindcare.dto.CommentDto;
import com.sparta.mindcare.model.Comment;
import com.sparta.mindcare.model.User;
import com.sparta.mindcare.repository.CommentRepository;
import com.sparta.mindcare.repository.DoctorRepository;
import com.sparta.mindcare.service.CommentService;
import com.sparta.mindcare.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;


    @PostMapping("/user/comment/{doctorId}")
    public void createComment(@RequestBody CommentDto commentDto, @PathVariable Long doctorId, @AuthenticationPrincipal User user){

        List<Comment> commentList =commentRepository.findAll();
        String username= user.getUsername();
        Long userId = user.getId();


        try{
            for(Comment comment : commentList){
                if(comment.getDoctor().getId()==doctorId && comment.getUser().getId()==userId){
                    throw new IllegalArgumentException("상담후기는 상담사당 1회만 작성 가능합니다.");
                }
            }

        }
        catch(IllegalArgumentException e){

        }



    }


}
