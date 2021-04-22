package com.sparta.mindcare.controllerReturn;

import lombok.Getter;

@Getter
public class MyPageReturn {
    private Boolean ok;
    private float avgPeriod;
    private int remainPeriod;
    private Object results;
    private String msg;

    public MyPageReturn(Boolean ok,float avgPeriod, int remainPeriod, Object results, String msg) {
        this.ok = ok;
        this.avgPeriod = avgPeriod;
        this.remainPeriod = remainPeriod;
        this.results = results;
        this.msg = msg;
    }
}
