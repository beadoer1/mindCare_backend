package com.sparta.mindcare.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartCommentDto {

    private Long commentId;
    private String username;
    private String writing;

    public PartCommentDto(Long commentId, String username, String writing){
        this.commentId= commentId;
        this.username= username;
        this.writing= writing;
    }

}
