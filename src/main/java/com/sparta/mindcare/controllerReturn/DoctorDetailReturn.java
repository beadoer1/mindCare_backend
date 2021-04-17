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
    private List<Comment> comments;
    private String msg;

    public DoctorDetailReturn(Boolean ok, Doctor doctor, List<Comment> comments, String msg){
        this.ok = ok;
        this.doctor=doctor;
        this.comments=comments;
        this.msg = msg;
    }

}
