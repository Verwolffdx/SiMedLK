package com.datwhite.simedlk.api;

import com.datwhite.simedlk.entity.Doctor;
import com.datwhite.simedlk.entity.MedOrg;
import com.datwhite.simedlk.entity.Specialization;
import com.datwhite.simedlk.entity.schedule.WorkerCellsBody;
import com.datwhite.simedlk.entity.schedule.WorkerCellsResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SiMedApi {
    @GET("api/Web/medorglist")
    Single<List<MedOrg>> listMedOrgs();

    @GET("api/Web/medicinfo/{MEDORG_ID}/1")
    Single<List<Doctor>> listDoctors(@Path("MEDORG_ID") String medorg_id);

    @GET("api/Web/allspec/{MEDORG_ID}")
    Single<List<Specialization>> listSpecs(@Path("MEDORG_ID") String medorg_id);

    @POST("api/Web/WorkerCells/")
    Single<WorkerCellsResponse> getWorkerCells(@Body WorkerCellsBody workerCellsBody);
}
