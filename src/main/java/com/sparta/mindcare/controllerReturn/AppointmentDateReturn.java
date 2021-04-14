package com.sparta.mindcare.controllerReturn;

import com.sparta.mindcare.util.Timetable;
import lombok.Setter;

@Setter
public class AppointmentDateReturn {
    private Boolean ok;
    private Timetable results;
    private String msg;
}
