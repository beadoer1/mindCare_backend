package com.sparta.mindcare.controllerReturn;

import lombok.Getter;

@Getter
public class MsgReturn {
    private Boolean ok;
    private String msg;

    public MsgReturn(Boolean ok, String msg){
        this.ok = ok;
        this.msg = msg;
    }
}
