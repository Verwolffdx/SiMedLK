package com.datwhite.simedlk;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.navigation.NavController;

import com.datwhite.simedlk.api.heroku.HerokuService;
import com.datwhite.simedlk.api.simed.SiMedService;
import com.datwhite.simedlk.entity.Doctor;
import com.datwhite.simedlk.entity.MedOrg;
import com.datwhite.simedlk.entity.auth.AuthResponse;
import com.datwhite.simedlk.entity.schedule.WorkerCellsResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App extends Application {
    private SiMedService siMedService;
    private HerokuService herokuService;

    private Doctor doctor;
    private MedOrg medOrg;
    private List<Doctor> doctorList;
    private Map<String, String> specializations;
    private WorkerCellsResponse workerCellsResponse;
    private AuthResponse authResponse;
    private List<String> patientsTime = new ArrayList<>();

    private NavController navController;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        siMedService = new SiMedService();
        herokuService = new HerokuService();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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

    public WorkerCellsResponse getWorkerCellsResponse() {
        return workerCellsResponse;
    }

    public void setWorkerCellsResponse(WorkerCellsResponse workerCellsResponse) {
        this.workerCellsResponse = workerCellsResponse;
    }

    public NavController getNavController() {
        return navController;
    }

    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    public AuthResponse getAuthResponse() {
        return authResponse;
    }

    public void setAuthResponse(AuthResponse authResponse) {
        this.authResponse = authResponse;
    }

    public List<String> getPatientsTime() {
        return patientsTime;
    }

    public void setPatientsTime(List<String> patientsTime) {
        this.patientsTime = patientsTime;
    }

    public HerokuService getHerokuService() {
        return herokuService;
    }
}
