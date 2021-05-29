package com.datwhite.simedlk;

import android.app.Application;

import com.datwhite.simedlk.api.SiMedService;
import com.datwhite.simedlk.entity.Doctor;
import com.datwhite.simedlk.entity.MedOrg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App extends Application {
    private SiMedService siMedService;

    private Doctor doctor;
    private MedOrg medOrg;
    private List<Doctor> doctorList;
    private Map<String, String> specializations;

    @Override
    public void onCreate() {
        super.onCreate();

        siMedService = new SiMedService();
    }

    public SiMedService getSiMedService() {
        return siMedService;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public MedOrg getMedOrg() {
        return medOrg;
    }

    public void setMedOrg(MedOrg medOrg) {
        this.medOrg = medOrg;
    }

    public List<Doctor> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    public Map<String, String> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(Map<String, String> specializations) {
        this.specializations = specializations;
    }
}
