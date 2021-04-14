package com.sparta.mindcare.model;

import com.sparta.mindcare.dto.AppointmentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

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

    @ManyToOne
    @JoinColumn
    private Doctor doctor;

    @ManyToOne
    @JoinColumn
    private User user;

    public Appointment(AppointmentDto requestDto){
        this.user = requestDto.getUser();
        this.doctor = requestDto.getDoctor();
        this.date = requestDto.getDate();
        this.time = requestDto.getTime();
    }
}
