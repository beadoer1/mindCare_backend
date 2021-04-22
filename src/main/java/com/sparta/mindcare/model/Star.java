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
    private Integer totalNum;

    @Column(nullable = false)
    private Integer totalScore;


    public Star(StarDto startDto){
        this.doctorId= startDto.getDoctorId();
        this.totalNum= startDto.getTotalNum();
        this.totalScore= startDto.getTotalScore();
    }

    // 임시 생성자(Star 초기 구성을 위함)
    public Star(Long doctorId){
        this.doctorId= doctorId;
        this.totalNum= 0;
        this.totalScore= 0;
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
