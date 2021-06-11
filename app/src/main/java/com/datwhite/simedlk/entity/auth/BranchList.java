package com.datwhite.simedlk.entity.auth;

public class BranchList {

    private String BRA_ID;
    private String BRA_NAME;

    public BranchList() {
    }

    public BranchList( String BRA_ID, String BRA_NAME) {

        this.BRA_ID = BRA_ID;
        this.BRA_NAME = BRA_NAME;
    }




    public String getBRA_ID() {
        return BRA_ID;
    }

    public void setBRA_ID(String BRA_ID) {
        this.BRA_ID = BRA_ID;
    }

    public String getBRA_NAME() {
        return BRA_NAME;
    }

    public void setBRA_NAME(String BRA_NAME) {
        this.BRA_NAME = BRA_NAME;
    }
}
