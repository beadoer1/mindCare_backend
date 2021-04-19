package com.sparta.mindcare.controllerReturn;

import com.sparta.mindcare.dto.PartCommentDto;
import com.sparta.mindcare.model.Comment;
import com.sparta.mindcare.model.Doctor;
import lombok.Getter;

import java.util.List;

@Getter
public class DoctorDetailReturn {

    private Boolean ok;
    private Doctor doctor;
    private List<PartCommentDto> comments;
    private String msg;

    public DoctorDetailReturn(Boolean ok, Doctor doctor, List<PartCommentDto> comments, String msg){
        this.ok = ok;
        this.doctor=doctor;
        this.comments=comments;
        this.msg = msg;
    }

}
