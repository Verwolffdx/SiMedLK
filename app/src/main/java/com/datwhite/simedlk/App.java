package com.datwhite.simedlk;

import android.app.Application;

import com.datwhite.simedlk.api.SiMedService;

public class App extends Application {
    private SiMedService siMedService;

    @Override
    public void onCreate() {
        super.onCreate();

        siMedService = new SiMedService();
    }

    public SiMedService getSiMedService() {
        return siMedService;
    }
}
