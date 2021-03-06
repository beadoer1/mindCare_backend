package com.sparta.mindcare.dto;

import com.sparta.mindcare.model.Doctor;
import com.sparta.mindcare.model.Star;
import com.sparta.mindcare.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
public class CommentDto {

    private Long id;
    private String writing;
    private Integer score;
    private Doctor doctor;
    private User user;
    private Star star;
}
