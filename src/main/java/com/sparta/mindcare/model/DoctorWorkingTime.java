package com.sparta.mindcare.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class DoctorWorkingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long startTime;

    @Column
    private Long endTime;

    @OneToOne
    @JoinColumn
    private Doctor doctor;

}
