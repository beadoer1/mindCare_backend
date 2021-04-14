package com.sparta.mindcare.controller;

import com.sparta.mindcare.dto.CommentDto;
import com.sparta.mindcare.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {



    @PostMapping("/user/comment/{doctorId}")
    public void createCommnent(@RequestBody CommentDto requestDto, @PathVariable Long doctorId, @AuthenticationPrincipal User user){
        
    }


}
