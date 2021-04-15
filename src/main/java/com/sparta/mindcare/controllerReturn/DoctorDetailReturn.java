package com.sparta.mindcare.controllerReturn;

import com.sparta.mindcare.model.Doctor;
import lombok.Getter;

@Getter
public class DoctorDetailReturn {
    private Boolean ok;
    private Doctor results;
    private String msg;

    public DoctorDetailReturn(Boolean ok, Doctor doctor, String msg){
        this.ok = ok;
        this.results = doctor;
        this.msg = msg;
    }
}
