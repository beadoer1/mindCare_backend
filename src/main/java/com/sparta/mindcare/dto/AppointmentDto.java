package com.sparta.mindcare.dto;

import com.sparta.mindcare.model.Doctor;
import com.sparta.mindcare.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class AppointmentDto {
    private User user;
    private Doctor doctor;
    private LocalDate date;
    private LocalTime time;
}
