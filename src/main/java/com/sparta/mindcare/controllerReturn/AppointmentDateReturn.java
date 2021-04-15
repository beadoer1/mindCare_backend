package com.sparta.mindcare.controllerReturn;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AppointmentDateReturn {
    private Boolean ok;
    private List<AppointmentTimeCheck> results;
    private String msg;
}
