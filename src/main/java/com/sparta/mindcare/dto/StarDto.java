package com.sparta.mindcare.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StarDto {

    Long doctorId;
    Integer totalNum;
    Integer totalScore;

    public StarDto(Long doctorId, Integer totalNum, Integer totalScore){
        this.doctorId=doctorId;
        this.totalNum=totalNum;
        this.totalScore=totalScore;

    }

}
