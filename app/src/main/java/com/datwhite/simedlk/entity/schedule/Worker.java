package com.datwhite.simedlk.entity.schedule;

import java.util.List;

public class Worker {
    private int $id;
    private int work_id;
    private String work_surname;
    private String work_name;
    private String work_patronimic;
    private int reception_duration;
    private List<Schedule> schedule;

    public Worker() {
    }

    public Worker(int $id, int work_id, String work_surname, String work_name, String work_patronimic, int reception_duration, List<Schedule> schedule) {
        this.$id = $id;
        this.work_id = work_id;
        this.work_surname = work_surname;
        this.work_name = work_name;
        this.work_patronimic = work_patronimic;
        this.reception_duration = reception_duration;
        this.schedule = schedule;
    }

    public int get$id() {
        return $id;
    }

    public void set$id(int $id) {
        this.$id = $id;
    }

    public int getWork_id() {
        return work_id;
    }

    public void setWork_id(int work_id) {
        this.work_id = work_id;
    }

    public String getWork_surname() {
        return work_surname;
    }

    public void setWork_surname(String work_surname) {
        this.work_surname = work_surname;
    }

    public String getWork_name() {
        return work_name;
    }

    public void setWork_name(String work_name) {
        this.work_name = work_name;
    }

    public String getWork_patronimic() {
        return work_patronimic;
    }

    public void setWork_patronimic(String work_patronimic) {
        this.work_patronimic = work_patronimic;
    }

    public int getReception_duration() {
        return reception_duration;
    }

    public void setReception_duration(int reception_duration) {
        this.reception_duration = reception_duration;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }
}
