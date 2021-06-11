package com.datwhite.simedlk.entity;

public class ActivityEntity {
    private String activity_date;
    private int count_patients;

    public ActivityEntity() {
    }

    public ActivityEntity(String activity_date, int count_patients) {
        this.activity_date = activity_date;
        this.count_patients = count_patients;
    }

    public String getActivity_date() {
        return activity_date;
    }

    public void setActivity_date(String activity_date) {
        this.activity_date = activity_date;
    }

    public int getCount_patients() {
        return count_patients;
    }

    public void setCount_patients(int count_patients) {
        this.count_patients = count_patients;
    }
}
