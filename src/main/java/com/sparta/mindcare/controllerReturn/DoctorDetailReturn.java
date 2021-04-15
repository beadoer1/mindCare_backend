package com.sparta.mindcare.controllerReturn;

import com.sparta.mindcare.model.Comment;
import com.sparta.mindcare.model.Doctor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DoctorDetailReturn {
    private Boolean ok;
    private Doctor results;
}
