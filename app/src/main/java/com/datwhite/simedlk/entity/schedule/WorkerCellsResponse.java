package com.datwhite.simedlk.entity.schedule;

import java.util.List;

public class WorkerCellsResponse {
    private int $id;
    private String message;
    private String success;
    private List<Worker> workers;

    public WorkerCellsResponse() {
    }

    public WorkerCellsResponse(int $id, String message, String success, List<Worker> workers) {
        this.$id = $id;
        this.message = message;
        this.success = success;
        this.workers = workers;
    }

    public int get$id() {
        return $id;
    }

    public void set$id(int $id) {
        this.$id = $id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }
}
