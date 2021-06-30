package com.datwhite.simedlk.api.heroku;

import com.datwhite.simedlk.entity.Patient;
import com.datwhite.simedlk.entity.auth.AuthBody;
import com.datwhite.simedlk.entity.auth.AuthResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface HerokuApi {
    @POST("patient")
    Single<Patient> getPatient(@Body Patient cardNumber);

    @POST("patient")
    Call<Patient> getPatientCall(@Body Patient cardNumber);
}
