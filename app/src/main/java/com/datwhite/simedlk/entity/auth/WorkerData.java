package com.datwhite.simedlk.entity.auth;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class WorkerData {

    private String BRA_ID;
    private String REC_TIME;
    private String REC_DURATION;
    private String DOCT_NAME;
    private String CARD_NUMBER;

    private String PATIENT_NAME;

    public WorkerData() {
    }

    public WorkerData(String BRA_ID, String REC_TIME, String REC_DURATION, String DOCT_NAME, String CARD_NUMBER) {
        this.BRA_ID = BRA_ID;
        this.REC_TIME = REC_TIME;
        this.REC_DURATION = REC_DURATION;
        this.DOCT_NAME = DOCT_NAME;
        this.CARD_NUMBER = CARD_NUMBER;
    }

    public WorkerData(String BRA_ID, String REC_TIME, String REC_DURATION, String DOCT_NAME, String CARD_NUMBER, String PATIENT_NAME) {
        this.BRA_ID = BRA_ID;
        this.REC_TIME = REC_TIME;
        this.REC_DURATION = REC_DURATION;
        this.DOCT_NAME = DOCT_NAME;
        this.CARD_NUMBER = CARD_NUMBER;
        this.PATIENT_NAME = PATIENT_NAME;
    }

    public String getBRA_ID() {
        return BRA_ID;
    }

    public void setBRA_ID(String BRA_ID) {
        this.BRA_ID = BRA_ID;
    }

    public String getREC_TIME() {
        return REC_TIME;
    }

    public void setREC_TIME(String REC_TIME) {
        this.REC_TIME = REC_TIME;
    }

    public String getREC_DURATION() {
        return REC_DURATION;
    }

    public void setREC_DURATION(String REC_DURATION) {
        this.REC_DURATION = REC_DURATION;
    }

    public String getDOCT_NAME() {
        return DOCT_NAME;
    }

    public void setDOCT_NAME(String DOCT_NAME) {
        this.DOCT_NAME = DOCT_NAME;
    }

    public String getCARD_NUMBER() {
        return CARD_NUMBER;
    }

    public void setCARD_NUMBER(String CARD_NUMBER) {
        this.CARD_NUMBER = CARD_NUMBER;
    }

    public String getPATIENT_NAME() {
        return PATIENT_NAME;
    }

    public void setPATIENT_NAME(String PATIENT_NAME) {
        this.PATIENT_NAME = PATIENT_NAME;
    }

    @Exclude
    public Map toMap() {
        HashMap result = new HashMap<>();
        result.put("BRA_ID", BRA_ID);
        result.put("REC_TIME", REC_TIME);
        result.put("REC_DURATION", REC_DURATION);
        result.put("DOCT_NAME", DOCT_NAME);
        result.put("CARD_NUMBER", CARD_NUMBER);

        return result;
    }


}
