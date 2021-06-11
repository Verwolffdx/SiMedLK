package com.datwhite.simedlk.entity.auth;

import java.util.List;

public class AuthResponse {

    private String work_name;
    private String work_surname;
    private String work_patronimic;
    private String work_photo;
    private List<WorkerData> WorkerData;
    private List<BranchList> BranchList;
    private String messege;
    private String status;
    private String token;

    public AuthResponse() {
    }

    public AuthResponse(
                        String work_name,
                        String work_surname,
                        String work_patronimic,
                        String work_photo,
                        List<WorkerData> workerData,
                        List<BranchList> branchList,
                        String messege,
                        String status,
                        String token) {

        this.work_name = work_name;
        this.work_surname = work_surname;
        this.work_patronimic = work_patronimic;
        this.work_photo = work_photo;
        WorkerData = workerData;
        BranchList = branchList;
        this.messege = messege;
        this.status = status;
        this.token = token;
    }



    public String getWork_name() {
        return work_name;
    }

    public void setWork_name(String work_name) {
        this.work_name = work_name;
    }

    public String getWork_surname() {
        return work_surname;
    }

    public void setWork_surname(String work_surname) {
        this.work_surname = work_surname;
    }

    public String getWork_patronimic() {
        return work_patronimic;
    }

    public void setWork_patronimic(String work_patronimic) {
        this.work_patronimic = work_patronimic;
    }

    public String getWork_photo() {
        return work_photo;
    }

    public void setWork_photo(String work_photo) {
        this.work_photo = work_photo;
    }

    public List<WorkerData> getWorkerData() {
        return WorkerData;
    }

    public void setWorkerData(List<WorkerData> workerData) {
        WorkerData = workerData;
    }

    public List<BranchList> getBranchList() {
        return BranchList;
    }

    public void setBranchList(List<BranchList> branchList) {
        BranchList = branchList;
    }

    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
