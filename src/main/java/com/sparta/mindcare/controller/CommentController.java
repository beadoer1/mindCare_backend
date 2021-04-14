package com.sparta.mindcare.controller;

import com.sparta.mindcare.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {



    @GetMapping("/user/comment")
    public void createCommnent(RequestBody CommnetDto requestDto, @AuthenticationPrincipal User user){



    }


}
