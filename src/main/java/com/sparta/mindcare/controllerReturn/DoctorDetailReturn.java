package com.sparta.mindcare.controllerReturn;

import com.sparta.mindcare.model.Doctor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DoctorDetailReturn {

    private Boolean ok;
    private Doctor results;
}
