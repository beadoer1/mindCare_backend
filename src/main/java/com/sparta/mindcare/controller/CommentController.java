package com.sparta.mindcare.controller;

import com.sparta.mindcare.controllerReturn.CommentReturn;
import com.sparta.mindcare.dto.CommentDto;
import com.sparta.mindcare.model.Comment;
import com.sparta.mindcare.model.User;
import com.sparta.mindcare.repository.CommentRepository;
import com.sparta.mindcare.service.CommentService;
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
    public CommentReturn create(@RequestBody CommentDto commentDto, @PathVariable Long doctorId, @AuthenticationPrincipal User user){
        CommentReturn commentReturn= new CommentReturn();
        List<Comment> commentList =commentRepository.findAll();
        Long userId = user.getId();
        for(Comment comment : commentList){
            if(comment.getDoctor().getId()==doctorId && comment.getUser().getId()==userId){

                commentReturn.setOk(false);
                commentReturn.setMsg("상담후기는 상담사당 1회만 작성 가능합니다.");
                return commentReturn;
            }
        }
        commentService.createComment(commentDto, user);
        commentReturn.setOk(true);
        commentReturn.setMsg("후기작성이 완료되었습니다.");
        return commentReturn;
    }


    @GetMapping("/api/user/test")
    public User test(@AuthenticationPrincipal User user){
        if(user==null) System.out.println("null");
        else
            System.out.println("not null");
        return user;
    }


}
