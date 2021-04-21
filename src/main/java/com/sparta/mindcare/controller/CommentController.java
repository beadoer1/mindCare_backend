package com.sparta.mindcare.controller;

import com.sparta.mindcare.controllerReturn.ResultReturn;
import com.sparta.mindcare.dto.CommentDto;
import com.sparta.mindcare.dto.StarDto;
import com.sparta.mindcare.model.Comment;
import com.sparta.mindcare.model.Doctor;
import com.sparta.mindcare.model.Star;
import com.sparta.mindcare.model.User;
import com.sparta.mindcare.repository.CommentRepository;
import com.sparta.mindcare.repository.DoctorRepository;
import com.sparta.mindcare.service.CommentService;
import com.sparta.mindcare.service.StarService;
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
    private final StarService starService;
    //후기 작성
    @PostMapping("/api/comments/{doctorId}")
    public ResultReturn create(@RequestBody CommentDto commentDto, @PathVariable Long doctorId, @AuthenticationPrincipal User user){


        //로그인 확인
        if(user==null)
            return new ResultReturn(false, null, "로그인이 필요한 서비스입니다.");

        Long userId=user.getId();
        List<Comment> commentList =commentRepository.findAllByDoctorIdAndUserId(doctorId, userId);

            if(!commentList.isEmpty())
                return new ResultReturn(false, null, "상담후기는 상담사당 1회만 작성 가능합니다.");



        Doctor doctor=doctorRepository.findById(doctorId).orElseThrow(
                () -> new IllegalArgumentException("상담사 ID가 존재하지 않습니다.")
        );

        StarDto startDto = new StarDto(doctorId, 1, commentDto.getScore());
        Star star = starService.createStar(startDto);

        //후기 db에 저장
        commentDto.setDoctor(doctor);
        commentDto.setUser(user);
        commentDto.setStar(star);
        commentService.createComment(commentDto);

        return new ResultReturn(true, null, "후기작성이 완료되었습니다.");
    }



    //작성한 후기 삭제
    @DeleteMapping("/api/comments/{commentId}")
    public ResultReturn delete(@PathVariable Long commentId, @AuthenticationPrincipal User user){

        //로그인 확인
        if(user==null)
            return new ResultReturn(false, null, "로그인이 필요한 서비스입니다.");

        Comment comment =commentRepository.findById(commentId).orElseThrow(
                ()->new IllegalArgumentException("해당 후기가 존재하지 않습니다.")
        );

        //작성자 본인이 아닌 경우
        if(!comment.getUser().equals(user))
            return new ResultReturn(false, null, "삭제는 작성자 본인만 가능합니다.");

        //작성한 후기 삭제
        else{
            starService.deleteStar(commentId);
            commentRepository.deleteById(commentId);
            return new ResultReturn(true, null, "삭제가 완료되었습니다.");
        }

    }

    //작성한 후기 수정
    @PutMapping("/api/comments/{commentId}")
    public ResultReturn update(@RequestBody CommentDto commentDto, @PathVariable Long commentId, @AuthenticationPrincipal User user){

        //로그인 확인
        if(user==null)
            return new ResultReturn(false, null, "로그인이 필요한 서비스입니다.");


        Star star = starService.updateStar(commentId, commentDto.getScore());
        commentDto.setStar(star);
        commentDto.setId(commentId);
        commentDto.setUser(user);

        //작성자 본인이면 후기수정
        if(commentService.updateComment(commentDto))
            return new ResultReturn(true, null, "수정이 완료되었습니다.");

        else
            return new ResultReturn(false, null, "수정은 작성자 본인만 가능합니다.");

    }


}
