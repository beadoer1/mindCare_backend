package com.sparta.mindcare.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter // 시간을 확인 후 possibleAppointment 를 넣어줘야하므로 @Setter 필요
public class AppointmentTimeCheck {
    private LocalTime time;
    private LocalTime timeEnd;
    private String stringTime;
    private String stringTimeEnd;
    private Boolean possibleAppointment;

    public AppointmentTimeCheck(int hour){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("kk:mm");
        this.time = LocalTime.of(hour,0);
        this.timeEnd = LocalTime.of(hour,50);
        this.stringTime = time.format(formatter);
        this.stringTimeEnd = timeEnd.format(formatter);
    }
}
