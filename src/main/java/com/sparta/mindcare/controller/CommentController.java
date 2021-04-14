package com.sparta.mindcare.controller;


import com.sparta.mindcare.model.User;
import com.sparta.mindcare.repository.DoctorRepository;
import com.sparta.mindcare.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final DoctorRepository doctorRepository;
    private final DoctorService doctorService;

    @GetMapping("/user/comment") //id: doctor id
    public String writeComment(){
        return "Hello";

    }

}
