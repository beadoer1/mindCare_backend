package com.sparta.mindcare.dto;

import com.sparta.mindcare.model.Doctor;
import com.sparta.mindcare.model.User;
import lombok.Getter;

import javax.persistence.*;

@Getter
public class CommentDto {

    private String writing;

    private Doctor doctor;

    private User user;

}
