package com.sparta.mindcare.dto;

import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
public class DoctorDto{
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String img;
    private List<String> careers;
    private Set<String> specialties;
    private List<String> daysOfWeek;
    private Map<String,Long> workingTime;
}