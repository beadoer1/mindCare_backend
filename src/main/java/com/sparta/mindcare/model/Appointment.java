package com.sparta.mindcare.model;

import com.sparta.mindcare.dto.AppointmentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@Entity
public class Appointment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column
    private String stringTime;

    @Column
    private String stringEndTime;

    @Column
    private Boolean completed;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Doctor doctor;

    private Long userId;

    public Appointment(AppointmentDto requestDto){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("kk:mm");

        this.userId = requestDto.getUser().getId();
        this.doctor = requestDto.getDoctor();
        this.date = requestDto.getDate();
        this.time = requestDto.getTime();
        this.endTime = time.plusMinutes(50);
        this.stringTime = time.format(formatter);
        this.stringEndTime = endTime.format(formatter);
        this.completed = false;
    }

    public void update(Boolean value){
        this.completed = value;
    }
}
