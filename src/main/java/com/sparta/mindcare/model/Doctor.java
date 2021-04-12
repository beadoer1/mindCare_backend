package com.sparta.mindcare.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Doctor extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private Long phone;

    @Column
    private String address;

    @Column
    private String img;

    @OneToMany
    @JoinColumn(name = "DOCTOR_ID")
    private List<DoctorCareer> careers;

    @ManyToMany
    @JoinColumn(name = "DOCTOR_ID")
    private List<DoctorSpecialty> specialties;
}
