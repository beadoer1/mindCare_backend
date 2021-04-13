package com.sparta.mindcare.controller;


import com.sparta.mindcare.controllerReturn.AppointmentPhoneReturn;
import com.sparta.mindcare.model.Doctor;
import com.sparta.mindcare.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

public class CommentController {




//    @PutMapping("/api/user/comment/{id}") //id: doctor id
//    public String writeComment(@PathVariable Long id, @AuthenticationPrincipal User user){
//
//    }

    @GetMapping("/api/user/comment") //id: doctor id
    public String writeComment(@AuthenticationPrincipal User user){
        System.out.println("result: ");
        System.out.println("result : " + user.getUsername());
        return user.getUsername();
    }

}
