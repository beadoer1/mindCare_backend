package com.sparta.mindcare.controllerReturn;

import com.sparta.mindcare.model.Doctor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
public class DoctorReturn {
    private Boolean ok;
    private List<Doctor> results;

    public DoctorReturn(Boolean ok, List<Doctor> doctorList){
        this.ok = ok;
        this.results = doctorList;
    }
}
