package com.sparta.mindcare.controllerReturn;

import com.sparta.mindcare.model.Comment;
import com.sparta.mindcare.model.Doctor;
import com.sparta.mindcare.repository.CommentRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
public class DoctorDetailReturn {


    private Boolean ok;
    private Doctor doctor;
    private String msg;



    public DoctorDetailReturn(Boolean ok, Doctor doctor, String msg){
        this.ok = ok;
        this.msg = msg;
    }
}
