package com.datwhite.simedlk.entity.schedule;

public class WorkerCellsBody {
    private int medorg_id;
    private int branch_id;
    private int worker_id;
    private int doctor_id;
    private String date_start;
    private String date_end;
    private int reception_kind;

    public WorkerCellsBody() {
    }

    public WorkerCellsBody(int medorg_id, int branch_id, int worker_id, int doctor_id, String date_start, String date_end, int reception_kind) {
        this.medorg_id = medorg_id;
        this.branch_id = branch_id;
        this.worker_id = worker_id;
        this.doctor_id = doctor_id;
        this.date_start = date_start;
        this.date_end = date_end;
        this.reception_kind = reception_kind;
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

    public int getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(int worker_id) {
        this.worker_id = worker_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public int getReception_kind() {
        return reception_kind;
    }

    public void setReception_kind(int reception_kind) {
        this.reception_kind = reception_kind;
    }
}
