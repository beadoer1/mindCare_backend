package com.sparta.mindcare.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private String phone;

    @Column
    private String address;

    @Column
    private String img;

    @ElementCollection
    private Set<String> careers;

    @ElementCollection
    private Set<String> specialties;

    @ElementCollection
    private List<String> daysOfWeek;

    @ElementCollection
    private Map<String,Long> workingTime;

}
