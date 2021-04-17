package com.sparta.mindcare.controllerReturn;



import lombok.Getter;
import lombok.Setter;

@Setter
@Getter // 안 넣으면 406 에러 발생
public class CommentReturn {
    private Boolean ok;
    private String msg;
}
