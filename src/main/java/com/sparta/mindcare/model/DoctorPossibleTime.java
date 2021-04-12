package com.sparta.mindcare.model;



import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

import javax.persistence.*;

public class DoctorPossibleTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private LocalDateTime possibleDatetime;

    public DoctorPossibleTime() {
    }
}
