package com.datwhite.simedlk.entity;

public class PatientSchedule {
    private String time_start;
    private String name;

    public PatientSchedule() {
    }

    public PatientSchedule(String time_start, String name) {
        this.time_start = time_start;
        this.name = name;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
