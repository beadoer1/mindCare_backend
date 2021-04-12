package com.sparta.mindcare.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class DoctorCareer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String career;

    @ManyToOne
    @JoinColumn(name = "DOCTOR_ID")
    private Doctor doctor;
}
