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



    @PostMapping("/api/comment/{doctorId}")
    public CommentReturn create(@RequestBody CommentDto commentDto, @PathVariable Long doctorId, @AuthenticationPrincipal User user){
        CommentReturn commentReturn= new CommentReturn();

        if(user==null){
            commentReturn.setOk(false);
            commentReturn.setMsg("로그인이 필요한 서비스입니다.");
            return commentReturn;
        }
        List<Comment> commentList =commentRepository.findAll();
        Long userId = user.getId();
        for(Comment comment : commentList){
            if(comment.getDoctor().getId()==doctorId && comment.getUser().getId()==userId){

                commentReturn.setOk(false);
                commentReturn.setMsg("상담후기는 상담사당 1회만 작성 가능합니다.");
                return commentReturn;
            }
        }
        commentService.createComment(commentDto, doctorId, user);
        commentReturn.setOk(true);
        commentReturn.setMsg("후기작성이 완료되었습니다.");
        return commentReturn;
    }




    @DeleteMapping("/api/comment/{commentId}")
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
        if(comment.getUser().getId()==user.getId()){
            commentRepository.deleteById(commentId);
            commentReturn.setOk(true);
            commentReturn.setMsg("삭제가 완료되었습니다.");
            return commentReturn;

        }
        commentReturn.setOk(false);
        commentReturn.setMsg("삭제는 작성자 본인만 가능합니다.");
        return commentReturn;
    }

    @PutMapping("/api/comment/{commentId}")
    public CommentReturn update(@RequestBody CommentDto commentDto, @PathVariable Long commentId, @AuthenticationPrincipal User user){
        CommentReturn commentReturn = new CommentReturn();
        if(user==null){
            commentReturn.setOk(false);
            commentReturn.setMsg("로그인이 필요한 서비스입니다.");
            return commentReturn;
        }

        if(commentService.updateComment(commentDto, commentId, user)){
            commentReturn.setOk(true);
            commentReturn.setMsg("수정이 완료되었습니다.");
        }
        else{
            commentReturn.setOk(false);
            commentReturn.setMsg("수정은 작성자 본인만 가능합니다");
        }

        return commentReturn;
    }

    @GetMapping("/api/test/{id}")
    public CommentReturn test(@AuthenticationPrincipal User user, @PathVariable Long id){
        CommentReturn commentReturn = new CommentReturn();
        if(user==null){
            commentReturn.setOk(false);
            commentReturn.setMsg("토큰이 잘못됨");
        }
        else{
            commentReturn.setOk(true);
            if(user.getId()==id)
                commentReturn.setMsg("해당 토큰 맞음");
            else
                commentReturn.setMsg("토큰은 올바르지만 해당 유저의 정보가 안들어가 있음");
        }
        return commentReturn;
    }




}
