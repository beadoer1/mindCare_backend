package com.sparta.mindcare.model;

import com.sparta.mindcare.dto.StarDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Star {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable=false)
    private Long doctorId;

    @Column(nullable = false)
    private Integer totalNum=0;

    @Column(nullable = false)
    private Integer totalScore=0;


    public Star(StarDto startDto){
        this.doctorId= startDto.getDoctorId();
        this.totalNum= startDto.getTotalNum();
        this.totalScore= startDto.getTotalScore();
    }

    public void add(StarDto starDto){
        this.totalNum+=starDto.getTotalNum();
        this.totalScore+=starDto.getTotalScore();
    }

    public void update(Integer oldScore, Integer newScore){
        this.totalScore=this.totalScore-oldScore+newScore;
    }
    public void delete(Integer score){
        this.totalNum-=1;
        this.totalScore-=score;
    }
}
