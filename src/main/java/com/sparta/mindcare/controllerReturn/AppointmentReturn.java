package com.sparta.mindcare.controllerReturn;

import com.sparta.mindcare.model.Appointment;
import lombok.Getter;

import java.util.List;

@Getter
public class AppointmentReturn {
    private Boolean ok;
    private List<Appointment> results;
    private String msg;

    public AppointmentReturn(Boolean ok, List<Appointment> results, String msg){
        this.ok = ok;
        this.results = results;
        this.msg = msg;
    }
}
