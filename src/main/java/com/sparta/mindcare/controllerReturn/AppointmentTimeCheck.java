package com.sparta.mindcare.controllerReturn;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter // 시간을 확인 후 possibleAppointment 를 넣어줘야하므로 @Setter 필요
public class AppointmentTimeCheck {
    private LocalTime time;
    private LocalTime timeEnd;
    private Boolean possibleAppointment;

    public AppointmentTimeCheck(int hour){
        this.time = LocalTime.of(hour,0,0);
        this.timeEnd = LocalTime.of(hour,50,0);
    }
}
