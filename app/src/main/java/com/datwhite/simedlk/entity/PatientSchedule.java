package com.datwhite.simedlk.entity;

public class PatientSchedule {
    private String time_start;
    private String name;
    private String duration;
    private String CARD_NUMBER;

    public PatientSchedule() {
    }

    public PatientSchedule(String time_start, String name, String duration, String CARD_NUMBER) {
        this.time_start = time_start;
        this.name = name;
        this.duration = duration;
        this.CARD_NUMBER = CARD_NUMBER;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCARD_NUMBER() {
        return CARD_NUMBER;
    }

    public void setCARD_NUMBER(String CARD_NUMBER) {
        this.CARD_NUMBER = CARD_NUMBER;
    }
}
