package com.sparta.mindcare.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartCommentDto {

    private Long commentId;
    private String username;
    private String writing;
    private Float score;

    public PartCommentDto(Long commentId, String username, String writing, Float score){
        this.commentId= commentId;
        this.username= username;
        this.writing= writing;
        this.score= score;
    }

}
