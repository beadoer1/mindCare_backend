package com.sparta.mindcare.util;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

public class Timetable {
    private Asdf time9;
    private Asdf time10;
    private Asdf time11;
    private Asdf time12;
    private Asdf time13;
    private Asdf time14;
    private Asdf time15;
    private Asdf time16;
    private Asdf time17;
    private Asdf time18;
    private Asdf time19;
    private Asdf time20;

    @Getter
    @Setter
    private class Asdf {
        LocalTime time;
        Boolean bool;
    }
}
