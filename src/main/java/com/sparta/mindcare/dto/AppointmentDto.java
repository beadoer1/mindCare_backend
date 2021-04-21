package com.sparta.mindcare.dto;

import com.sparta.mindcare.model.Doctor;
import com.sparta.mindcare.model.User;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class AppointmentDto {
    private User user;
    private Doctor doctor;
    private LocalDate date;
    private LocalTime time;

    public AppointmentDto(User user,Doctor doctor,LocalDate date, LocalTime time){
        this.user = user;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
    }

}
