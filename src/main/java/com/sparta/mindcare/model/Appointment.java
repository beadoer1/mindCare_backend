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

    @Column(nullable = false)
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Doctor doctor;

    private Long userId;

    public Appointment(AppointmentDto requestDto){
        this.userId = requestDto.getUser().getId();
        this.doctor = requestDto.getDoctor();
        this.date = requestDto.getDate();
        this.time = requestDto.getTime();
        this.endTime = time.plusMinutes(50);
    }
}
