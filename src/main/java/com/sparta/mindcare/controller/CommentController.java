package com.sparta.mindcare.controller;

import com.sparta.mindcare.controllerReturn.CommentReturn;
import com.sparta.mindcare.dto.CommentDto;
import com.sparta.mindcare.model.Comment;
import com.sparta.mindcare.model.Doctor;
import com.sparta.mindcare.model.User;
import com.sparta.mindcare.repository.CommentRepository;
import com.sparta.mindcare.repository.DoctorRepository;
import com.sparta.mindcare.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;
    private final DoctorRepository doctorRepository;
    private final CommentService commentService;

    @PostMapping("/api/comments/{doctorId}")
    public CommentReturn create(@RequestBody CommentDto commentDto, @PathVariable Long doctorId, @AuthenticationPrincipal User user){

        CommentReturn commentReturn= new CommentReturn();

        if(user==null){
            commentReturn.setOk(false);
            commentReturn.setMsg("로그인이 필요한 서비스입니다.");
            return commentReturn;
        }


        Long userId=user.getId();
        List<Comment> commentList =commentRepository.findAllByDoctorIdAndUserId(doctorId, userId);

            if(!commentList.isEmpty()){
                commentReturn.setOk(false);
                commentReturn.setMsg("상담후기는 상담사당 1회만 작성 가능합니다.");
                return commentReturn;
            }


        Doctor doctor=doctorRepository.findById(doctorId).orElseThrow(
                () -> new IllegalArgumentException("상담사 ID가 존재하지 않습니다.")
        );
        commentDto.setDoctor(doctor);
        commentDto.setUser(user);
        commentService.createComment(commentDto);
        commentReturn.setOk(true);
        commentReturn.setMsg("후기작성이 완료되었습니다.");
        return commentReturn;
    }




    @DeleteMapping("/api/comments/{commentId}")
    public CommentReturn delete(@PathVariable Long commentId, @AuthenticationPrincipal User user){
        CommentReturn commentReturn = new CommentReturn();

        if(user==null){
            commentReturn.setOk(false);
            commentReturn.setMsg("로그인이 필요한 서비스입니다.");
            return commentReturn;
        }

        Comment comment =commentRepository.findById(commentId).orElseThrow(
                ()->new IllegalArgumentException("해당 후기가 존재하지 않습니다.")
        );

        if(!comment.getUser().equals(user)){
            commentReturn.setOk(false);
            commentReturn.setMsg("삭제는 작성자 본인만 가능합니다.");
            return commentReturn;
        }
        else{
            commentRepository.deleteById(commentId);
            commentReturn.setOk(true);
            commentReturn.setMsg("삭제가 완료되었습니다.");
            return commentReturn;
        }

    }

    @PutMapping("/api/comments/{commentId}")
    public CommentReturn update(@RequestBody CommentDto commentDto, @PathVariable Long commentId, @AuthenticationPrincipal User user){
        CommentReturn commentReturn = new CommentReturn();
        if(user==null){
            commentReturn.setOk(false);
            commentReturn.setMsg("로그인이 필요한 서비스입니다.");
            return commentReturn;
        }

        commentDto.setId(commentId);
        commentDto.setUser(user);
        if(commentService.updateComment(commentDto)){
            commentReturn.setOk(true);
            commentReturn.setMsg("수정이 완료되었습니다.");
        }
        else{
            commentReturn.setOk(false);
            commentReturn.setMsg("수정은 작성자 본인만 가능합니다");
        }

        return commentReturn;
    }
}
