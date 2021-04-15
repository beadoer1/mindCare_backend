package com.sparta.mindcare.controllerReturn;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
public class AppointmentTimeCheck {
    private LocalTime time;
    private LocalTime timeEnd;
    private Boolean possibleAppointment;

    public AppointmentTimeCheck(int hour){
        this.time = LocalTime.of(hour,0,0);
        this.timeEnd = LocalTime.of(hour,50,0);
    }
}
