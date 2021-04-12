package com.sparta.mindcare.controllerReturn;

import com.sparta.mindcare.model.Doctor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class DoctorReturn {
    private Boolean ok;
    private List<Doctor> results;
}
