package com.datwhite.simedlk.ui.schedule;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.datwhite.simedlk.App;
import com.datwhite.simedlk.AuthActivity;
import com.datwhite.simedlk.R;
import com.datwhite.simedlk.entity.MedOrg;
import com.datwhite.simedlk.entity.Patient;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

public class PatientInfoFragment extends Fragment {
    private App app;
    CompositeDisposable disposable = new CompositeDisposable();

    private TextView patientName;
    private TextView patientSurname;
    private TextView patientPatronymic;
    private TextView patientAge;
    private TextView patientDate;
    private TextView patientTime;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_patient_info, container, false);

        app = (App) getActivity().getApplication();

        patientName = root.findViewById(R.id.patientNameInfo);
        patientSurname = root.findViewById(R.id.patientSurnameInfo);
        patientPatronymic = root.findViewById(R.id.patientPatronymicInfo);
        patientAge = root.findViewById(R.id.patientAgeInfo);
        patientDate = root.findViewById(R.id.patientDateInfo);
        patientTime = root.findViewById(R.id.patientTimeInfo);

        String cardNumber = (String) getArguments().getString("cardNumber");

        disposable.add(app.getHerokuService().getApi().getPatient(new Patient(cardNumber))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<Patient, Throwable>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void accept(Patient patient, Throwable throwable) throws Exception {
                        if (throwable != null) {
                            Toast.makeText(getContext(), "Data loading error", Toast.LENGTH_SHORT).show();
                        } else {
                            patientName.setText(patient.getPatient_name());
                            patientSurname.setText(patient.getPatient_surname());
                            patientPatronymic.setText(patient.getPatient_patronymic());
                            String[] date = patient.getPatient_age().split("-");

                            LocalDate start = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
                            LocalDate end = LocalDate.now();
                            long years = ChronoUnit.YEARS.between(start, end);
//                            System.out.println(years);
                            patientAge.setText(String.valueOf(years));
                            patientDate.setText(patient.getRecord_date());
                            patientTime.setText(patient.getRecord_time());
                        }
                    }
                }));

        return root;
    }
}
