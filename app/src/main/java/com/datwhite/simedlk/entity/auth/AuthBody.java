package com.datwhite.simedlk.entity.auth;

public class AuthBody {
    private String medorg_id;
    private String phone;
    private String password;

    public AuthBody() {
    }

    public AuthBody(String medorg_id, String phone, String password) {
        this.medorg_id = medorg_id;
        this.phone = phone;
        this.password = password;
    }

    public String getMedorg_id() {
        return medorg_id;
    }

    public void setMedorg_id(String medorg_id) {
        this.medorg_id = medorg_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
