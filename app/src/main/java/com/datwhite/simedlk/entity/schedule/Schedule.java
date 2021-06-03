package com.datwhite.simedlk.entity.schedule;

import java.util.List;

public class Schedule {
    private int $id;
    private int worker_id;
    private int sched_id;
    private int medorg_id;
    private int branch_id;
    private int doctor_id;
    private String date;
    private String time_start;
    private String time_end;
    private List<Cell> cells;

    public Schedule() {
    }

    public Schedule(int $id, int worker_id, int sched_id, int medorg_id, int branch_id, int doctor_id, String date, String time_start, String time_end, List<Cell> cells) {
        this.$id = $id;
        this.worker_id = worker_id;
        this.sched_id = sched_id;
        this.medorg_id = medorg_id;
        this.branch_id = branch_id;
        this.doctor_id = doctor_id;
        this.date = date;
        this.time_start = time_start;
        this.time_end = time_end;
        this.cells = cells;
    }

    public int get$id() {
        return $id;
    }

    public void set$id(int $id) {
        this.$id = $id;
    }

    public int getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(int worker_id) {
        this.worker_id = worker_id;
    }

    public int getSched_id() {
        return sched_id;
    }

    public void setSched_id(int sched_id) {
        this.sched_id = sched_id;
    }

    public int getMedorg_id() {
        return medorg_id;
    }

    public void setMedorg_id(int medorg_id) {
        this.medorg_id = medorg_id;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }
}
