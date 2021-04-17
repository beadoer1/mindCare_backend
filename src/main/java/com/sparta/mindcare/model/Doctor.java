package com.sparta.mindcare.model;

import com.sparta.mindcare.dto.DoctorDto;
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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column
    private String img;

    @ElementCollection
    private List<String> careers;

    @ElementCollection
    private Set<String> specialties;

    @ElementCollection
    private List<String> daysOfWeek;

    @ElementCollection
    private Map<String,Long> workingTime;


    public Doctor(DoctorDto requestDto){
        this.name = requestDto.getName();
        this.phone = requestDto.getPhone();
        this.address = requestDto.getAddress();
        this.img = requestDto.getImg();
        this.careers = requestDto.getCareers();
        this.specialties = requestDto.getSpecialties();
        this.daysOfWeek = requestDto.getDaysOfWeek();
        this.workingTime = requestDto.getWorkingTime();
    }

    public void update(DoctorDto requestDto){
        this.name = requestDto.getName();
        this.phone = requestDto.getPhone();
        this.address = requestDto.getAddress();
        this.img = requestDto.getImg();
        this.careers = requestDto.getCareers();
        this.specialties = requestDto.getSpecialties();
        this.daysOfWeek = requestDto.getDaysOfWeek();
        this.workingTime = requestDto.getWorkingTime();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User)
            return id==((Doctor)obj).id;
        else
            return false;
    }
}
