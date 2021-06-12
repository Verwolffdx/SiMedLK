package com.datwhite.simedlk;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.datwhite.simedlk.entity.Doctor;
import com.datwhite.simedlk.entity.MedOrg;
import com.datwhite.simedlk.entity.auth.AuthBody;
import com.datwhite.simedlk.entity.auth.AuthResponse;
import com.datwhite.simedlk.entity.auth.WorkerData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

public class AuthDoctorActivity extends AppCompatActivity {
    private App app;

    private Button btn_doc_auth;
    private EditText authDocPhone;
    private EditText authDocPassword;

    CompositeDisposable disposable = new CompositeDisposable();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    private MedOrg medorg;
    private Doctor doctor;
    //    private List<Doctor> colleagues = new ArrayList<>();
    private Map<String, String> specs = new HashMap<>();

    public static void start(Context caller, Doctor doctor, String medOrgId, HashMap<String, String> specializations) {
        Intent intent = new Intent(caller, MainActivity.class);
        intent.putExtra(Doctor.class.getSimpleName(), (Serializable) doctor);
        intent.putExtra("medOrgID", medOrgId);
        intent.putExtra("SPECIALIZATIONS", specializations);
        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_doc);

        app = (App) getApplication();

        btn_doc_auth = findViewById(R.id.btn_doc_auth);
        authDocPhone = findViewById(R.id.authDocPhone);
        authDocPassword = findViewById(R.id.authDocPassword);

        doctor = app.getDoctor();
        medorg = app.getMedOrg();
        specs = app.getSpecializations();


        btn_doc_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (authDocPhone.getText().toString().equals("")) {
                    Toast.makeText(AuthDoctorActivity.this, "Введите номер телефона", Toast.LENGTH_SHORT).show();
                } else if (authDocPassword.getText().toString().equals("")) {
                    Toast.makeText(AuthDoctorActivity.this, "Введите пароль", Toast.LENGTH_SHORT).show();
                } else {
                    AuthBody authBody = new AuthBody(
                            medorg.getId(),
                            authDocPhone.getText().toString(),
                            authDocPassword.getText().toString()
                    );

                    disposable.add(app.getSiMedService().getApi().authorization(authBody)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BiConsumer<AuthResponse, Throwable>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void accept(AuthResponse authResponse, Throwable throwable) throws Exception {
                                    if (throwable != null) {
                                        Toast.makeText(AuthDoctorActivity.this, "Data loading error", Toast.LENGTH_SHORT).show();
                                        System.out.println(throwable.getCause());
                                    } else {
                                        System.out.println(authResponse.getMessege());
                                        if (authResponse.getMessege() != null && authResponse.getMessege().equals("Номер не привязан ни к одной учётной записи в данной медорганизации.")) {
                                            Toast.makeText(AuthDoctorActivity.this, "Номер не привязан ни к одной учётной записи в данной медорганизации", Toast.LENGTH_SHORT).show();
                                        } else if (authResponse.getMessege() != null && authResponse.getMessege().equals("Неверный пароль.")) {
                                            Toast.makeText(AuthDoctorActivity.this, "Неверный пароль", Toast.LENGTH_SHORT).show();
                                        } else {
                                            app.setAuthResponse(authResponse);

                                            myRef = database.getReference("activity").child(app.getDoctor().getId() + "_from_" + app.getMedOrg().getId()).child(String.valueOf(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now())));

//                                            myRef.setValue(authResponse.getWorkerData());
//                                            for (WorkerData w : authResponse.getWorkerData())
//                                                myRef.setValue(w);


                                            if (authResponse.getWorkerData() != null) {
                                                myRef.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                                                    Log.e("Count ", "" + snapshot.getChildrenCount());
                                                        //если в БД записей меньше, чем приходит в ответ на автоизацию ->
                                                        //дописать недостающие в БД
                                                        if (snapshot.getChildrenCount() != authResponse.getWorkerData().size()) {
                                                            for (int i = (int) (snapshot.getChildrenCount() - 1); i < authResponse.getWorkerData().size(); i++) {
                                                                myRef.setValue(authResponse.getWorkerData().get(i));
                                                            }
                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                                    }
                                                });
                                            }


//                                            if (myRef.get().equals(authResponse.getWorkerData()))
//                                            myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                                                @Override
//                                                public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
//                                                    if (!task.isSuccessful()) {
//                                                        Log.e("firebase", "Error getting data", task.getException());
//                                                    } else {
//                                                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
//
//
//                                                        task.getResult().getValue();
//                                                        System.out.println(task.getResult().getValue().equals(authResponse.getWorkerData()));
//                                                    }
//
//                                                }
//                                            });


                                            Intent intent = new Intent(v.getContext(), MainActivity.class);
                                            startActivity(intent);
                                            onPause();
                                        }


                                    }
                                }
                            }));

//                MainActivity.start(v.getContext(), doctor, medorg.getId(), specs);

                }
            }
        });
    }
}
