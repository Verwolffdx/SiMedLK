package com.datwhite.simedlk.ui.patient;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.datwhite.simedlk.App;
import com.datwhite.simedlk.AuthActivity;
import com.datwhite.simedlk.R;
import com.datwhite.simedlk.entity.MedOrg;
import com.datwhite.simedlk.entity.schedule.Cell;
import com.datwhite.simedlk.entity.schedule.Schedule;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddPatient extends Fragment {
    private View root;
    private App app;

    private EditText patientName;
    private EditText patientSurname;
    private EditText patientPatronymic;
    private TextView patientDate;
    private Button addPatientBtn;

    private String chosenTime;

    private Spinner spinner;

    Calendar dateAndTime = Calendar.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_add_patient, container, false);

        patientName = root.findViewById(R.id.patientName);
        patientSurname = root.findViewById(R.id.patientSurname);
        patientPatronymic = root.findViewById(R.id.patientPatronymic);
        patientDate = root.findViewById(R.id.patientDate);
//        spinner = root.findViewById(R.id.patientTime);
//        addPatientBtn = root.findViewById(R.id.addPatientBtn);

        app = (App) getActivity().getApplication();

//        String[] defaultSpinner = {"Выбрать время"};
//
//
//        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, defaultSpinner);
//        // Определяем разметку для использования при выборе элемента
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Применяем адаптер к элементу spinner
//        spinner.setAdapter(adapter);





//        setInitialDateTime();

        patientDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });

//        addPatientBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().onBackPressed();
//            }
//        });

        return root;
    }

    private void setFreeTimeSpinner() {
        if (app.getWorkerCellsResponse().getWorkers().size() > 0) {

            List<String> timeList = new ArrayList<>();
            List<Schedule> scheduleList = app.getWorkerCellsResponse().getWorkers().get(0).getSchedule();
            System.out.println("SIZE " + app.getWorkerCellsResponse().getWorkers().get(0).getSchedule().size());
            for (Schedule s : scheduleList) {
                System.out.println(s.getDate());
                if (s.getDate().equals(chosenTime)) {
                    for (Cell c : s.getCells()) {
                        System.out.println(c.getDate() + " " + c.getTime_start());
                        if (c.isFree()) {
                            timeList.add(c.getTime_start());
                        }
                    }
                }
            }

            String[] freeTime = new String[timeList.size()];
            for (int i = 0; i < timeList.size(); i++) {
                freeTime[i] = timeList.get(i);
                System.out.println(freeTime[i]);
            }

            // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, freeTime);
            // Определяем разметку для использования при выборе элемента
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Применяем адаптер к элементу spinner
            spinner.setAdapter(adapter);



            //Обработка выбора мед организации из списка
            AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // Получаем выбранный объект
                /*
                String item = (String) parent.getItemAtPosition(position);
                for (MedOrg medOrg : medOrgList) {
                    if (item.equals(medOrg.getTitle())) {
                        medorg = medOrg;
                        app.setMedOrg(medOrg);
                        getDoctors();


//                                            progressBar.setVisibility(ProgressBar.INVISIBLE);
//                                            linearLayout.setAlpha(1);
                        //Запуск потока с запросом списка мед органазаций
//                                            Doctors doctors = new Doctors();
//                                            doctors.execute();
                    }
                }*/
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            };
            spinner.setOnItemSelectedListener(itemSelectedListener);
        }
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(getContext(), d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка начальных даты и времени
    private void setInitialDateTime() {

        patientDate.setText(DateUtils.formatDateTime(getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_WEEKDAY));

        setFreeTimeSpinner();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            chosenTime = formatter.format(dateAndTime);
            DecimalFormat mFormat= new DecimalFormat("00");

            chosenTime = year + "-" + mFormat.format(Double.valueOf(monthOfYear+1)) + "-" + mFormat.format(Double.valueOf(dayOfMonth));
            System.out.println(chosenTime);

            setInitialDateTime();
        }
    };
}
