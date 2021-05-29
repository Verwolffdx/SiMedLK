package com.datwhite.simedlk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.datwhite.simedlk.entity.Doctor;
import com.datwhite.simedlk.entity.MedOrg;
import com.datwhite.simedlk.entity.Specialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

public class AuthActivity extends AppCompatActivity {
    CompositeDisposable disposable = new CompositeDisposable();

    private Button btn_auth;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;

    private MedOrg medorg;
    private Doctor doctor;
    //    private List<Doctor> colleagues = new ArrayList<>();
    private HashMap<String, String> specs = new HashMap<>();

    ArrayList<MedOrg> medOrgArrayList = new ArrayList<>();
    ArrayList<Doctor> doctorArrayList = new ArrayList<>();
    //    ArrayList<Specialization> specializationArrayList = new ArrayList<>();
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        app = (App) getApplication();
        progressBar = findViewById(R.id.progressBar);
        linearLayout = findViewById(R.id.linear_layout);

        linearLayout.setAlpha(0.8F);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        disposable.add(app.getSiMedService().getApi().listMedOrgs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<List<MedOrg>, Throwable>() {
                    @Override
                    public void accept(List<MedOrg> medOrgList, Throwable throwable) throws Exception {
                        if (throwable != null) {
                            Toast.makeText(AuthActivity.this, "Data loading error", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(ProgressBar.INVISIBLE);
                            linearLayout.setAlpha(1);
                        } else {
                            medOrgArrayList = (ArrayList<MedOrg>) medOrgList;

                            String[] medorgArr = new String[medOrgList.size()];
                            for (int i = 0; i < medOrgList.size(); i++) {
//                                System.out.println("Org " + m.getTitle());
                                medorgArr[i] = medOrgList.get(i).getTitle();
                            }

                            //Создание выпадающего списка мед организаций
                            Spinner spinner = (Spinner) findViewById(R.id.spinnerMedorg);
                            // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AuthActivity.this, android.R.layout.simple_spinner_item, medorgArr);
                            // Определяем разметку для использования при выборе элемента
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            // Применяем адаптер к элементу spinner
                            spinner.setAdapter(adapter);

                            //Обработка выбора мед организации из списка
                            AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    // Получаем выбранный объект
                                    String item = (String) parent.getItemAtPosition(position);
                                    for (MedOrg medOrg : medOrgList) {
                                        if (item.equals(medOrg.getTitle())) {
                                            medorg = medOrg;
                                            app.setMedOrg(medOrg);
                                            getDoctors();
                                            getSpecs();
//                                            progressBar.setVisibility(ProgressBar.INVISIBLE);
//                                            linearLayout.setAlpha(1);
                                            //Запуск потока с запросом списка мед органазаций
//                                            Doctors doctors = new Doctors();
//                                            doctors.execute();
                                        }
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            };
                            spinner.setOnItemSelectedListener(itemSelectedListener);

//                            adapter.setDates(dates);
                        }
                    }
                }));


        //Кнопка авторизации
        btn_auth = findViewById(R.id.btn_auth);
        btn_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(AuthActivity.this, MainActivity.class);
//                intent.putExtra(Doctor.class.getSimpleName(), doctor);
//                intent.putExtra(MedOrg.class.getSimpleName(), medorg);
//                intent.putExtra("specialization", specs);
//                startActivity(intent);
//                onPause();
//                System.out.println("AUTH DOCTid " + doctor.toString());
                MainActivity.start(v.getContext(), doctor, medorg.getId(), specs);
                onPause();
            }
        });

        //Запуск потока с запросом списка мед органазаций
//        Medorglist myTask = new Medorglist();
//        myTask.execute();
    }

    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }

    @SuppressLint("CheckResult")
    public void getDoctors() {
        app.getSiMedService().getApi().listDoctors(medorg.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<List<Doctor>, Throwable>() {
                    @Override
                    public void accept(List<Doctor> doctorList, Throwable throwable) throws Exception {
                        if (throwable != null) {
                            Toast.makeText(AuthActivity.this, "Data loading error", Toast.LENGTH_SHORT).show();
                        } else {
                            doctorArrayList = (ArrayList<Doctor>) doctorList;
                            app.setDoctorList(doctorList);
                            String[] doctorsArr = new String[doctorList.size()];
                            for (int i = 0; i < doctorList.size(); i++) {
//                                System.out.println("Org " + m.getTitle());
                                doctorsArr[i] = doctorList.get(i).getName();
                            }

                            //Создание выпадающего списка врачей
                            TextView textView = (TextView) findViewById(R.id.text);
                            Spinner spinner = (Spinner) findViewById(R.id.spinnerFio);
                            // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AuthActivity.this, android.R.layout.simple_spinner_item, doctorsArr);
                            // Определяем разметку для использования при выборе элемента
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            // Применяем адаптер к элементу spinner
                            spinner.setAdapter(adapter);



                            //Обработка выбора мед организации из списка
                            AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    // Получаем выбранный объект
                                    String item = (String) parent.getItemAtPosition(position);
                                    for (Doctor doc : doctorList) {
                                        if (item.equals(doc.getName())) {
                                            doctor = doc;
                                            app.setDoctor(doc);
                                            progressBar.setVisibility(ProgressBar.INVISIBLE);
                                            linearLayout.setAlpha(1);
                                            //Запуск потока с запросом списка мед органазаций
//                                            Doctors doctors = new Doctors();
//                                            doctors.execute();
                                        }
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            };
                            spinner.setOnItemSelectedListener(itemSelectedListener);

//                            adapter.setDates(dates);
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void getSpecs() {
        app.getSiMedService().getApi().listSpecs(medorg.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<List<Specialization>, Throwable>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void accept(List<Specialization> specializationList, Throwable throwable) throws Exception {
                        if (throwable != null) {
                            Toast.makeText(AuthActivity.this, "Data loading error", Toast.LENGTH_SHORT).show();
                        } else {
//                            specializationArrayList = (ArrayList<Specialization>) specializationList;
                            app.setSpecializations(convertListAfterJava8(specializationList));

                            for (Specialization s : specializationList) {
                                specs.put(s.getId(), s.getName());
//                                System.out.println(s.getName());
                            }
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Map<String, String> convertListAfterJava8(List<Specialization> list) {
        Map<String, String> map = list.stream()
                .collect(Collectors.toMap(Specialization::getId, Specialization::getName));
        return map;
    }
}















/*
    //Чтение JSON файла со списком мед органазаций
    public List<MedOrg> readJsonStreamMedOrgs(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readMedOrgsArray(reader);
        } finally {
            reader.close();
        }
    }

    public List<MedOrg> readMedOrgsArray(JsonReader reader) throws IOException {
        List<MedOrg> medOrgs = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            medOrgs.add(readMedOrgs(reader));
        }
        reader.endArray();
        return medOrgs;
    }

    public MedOrg readMedOrgs(JsonReader reader) throws IOException {
        String id = null;
        String nameMedOrg = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextString();
            }
            else if (name.equals("title")) {
                nameMedOrg = reader.nextString();
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new MedOrg(id, nameMedOrg);
    }

    //Класс вызова потока для запроса списка мед органазаций
    class Medorglist extends AsyncTask<Void, Void, Void> {
        private String[] medorglist;
        private List<MedOrg> list;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL("https://patient.simplex48.ru/api/Web/medorglist/");

                HttpsURLConnection myConnection =
                        (HttpsURLConnection) url.openConnection();

                if (myConnection.getResponseCode() == 200) {
                    System.out.println("SUCCESS");
                } else {
                    System.out.println("ERROR");
                }

                InputStream responseBody = myConnection.getInputStream();

                list = readJsonStreamMedOrgs(responseBody);
                medorglist = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    medorglist[i] = list.get(i).getTitle();
                }

                myConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //Создание выпадающего списка мед организаций
            Spinner spinner = (Spinner) findViewById(R.id.spinnerMedorg);
            // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AuthActivity.this, android.R.layout.simple_spinner_item, medorglist);
            // Определяем разметку для использования при выборе элемента
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Применяем адаптер к элементу spinner
            spinner.setAdapter(adapter);

            //Обработка выбора мед организации из списка
            AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // Получаем выбранный объект
                    String item = (String)parent.getItemAtPosition(position);
                    for (MedOrg medOrg : list) {
                        if (item.equals(medOrg.getTitle())) {
                            medorg = medOrg;
                            //Запуск потока с запросом списка мед органазаций
                            Doctors doctors = new Doctors();
                            doctors.execute();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            };
            spinner.setOnItemSelectedListener(itemSelectedListener);
        }
    }

    //Чтение JSON файла со списком врачей в мед органазации
    public List<Doctor> readJsonStreamDoct(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readDoctArray(reader);
        } finally {
            reader.close();
        }
    }

    public List<Doctor> readDoctArray(JsonReader reader) throws IOException {
        List<Doctor> doctors = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            doctors.add(readDoct(reader));
        }
        reader.endArray();
        return doctors;
    }

    public Doctor readDoct(JsonReader reader) throws IOException {
        String id = null;
        String nameMedOrg = null;
        String photo = null;
        String desc = null;
        String specialization = null;
        String qualification = null;
        String services = null;
        List<Integer> DOCT_IDs = new ArrayList<>();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextString();
            } else if (name.equals("name")) {
                nameMedOrg = reader.nextString();
            } else if (name.equals("photo")) {
                photo = reader.nextString();
            } else if (name.equals("desc")) {
                desc = reader.nextString();
            } else if (name.equals("specialization")) {
                specialization = reader.nextString();
            } else if (name.equals("qualification")) {
                qualification = reader.nextString();
            } else if (name.equals("services")) {
                services = reader.nextString();
            } else if (name.equals("DOCT_IDs")) {
                reader.beginArray();
                while (reader.hasNext())
                    DOCT_IDs.add(reader.nextInt());
                reader.endArray();
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Doctor(id, nameMedOrg, photo, desc, specialization, qualification, services, DOCT_IDs);
//        return new Doctor(id, nameMedOrg, photo, desc, specialization, qualification, services);
    }

    //Класс вызова потока для запроса списка мед органазаций
    class Doctors extends AsyncTask<Void, Void, Void> {
        private String[] doctors;
        private List<Doctor> list;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL("https://patient.simplex48.ru/api/Web/medicinfo/" + medorg.getId() + "/1/");

                HttpsURLConnection myConnection =
                        (HttpsURLConnection) url.openConnection();

                if (myConnection.getResponseCode() == 200) {
                    System.out.println("SUCCESS");
                } else {
                    System.out.println("ERROR");
                }

                InputStream responseBody = myConnection.getInputStream();

                list = readJsonStreamDoct(responseBody);

                doctors = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    doctors[i] = list.get(i).getName();
//                    colleagues.add(list.get(i));
                }

                myConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //Создание выпадающего списка врачей
            TextView textView = (TextView) findViewById(R.id.text);
            Spinner spinner = (Spinner) findViewById(R.id.spinnerFio);
            // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AuthActivity.this, android.R.layout.simple_spinner_item, doctors);
            // Определяем разметку для использования при выборе элемента
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Применяем адаптер к элементу spinner
            spinner.setAdapter(adapter);

            //Обработка выбора врача
            AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // Получаем выбранный объект
                    String item = (String)parent.getItemAtPosition(position);
                    for (Doctor doct : list) {
                        if (item.equals(doct.getName())) {
                            doctor = doct;

                            Specs specs = new Specs();
                            specs.execute();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            };
            spinner.setOnItemSelectedListener(itemSelectedListener);
        }
    }

    //Чтение JSON файла со списком специализаций
    public List<Specialization> readJsonStreamSpec(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readSpecArray(reader);
        } finally {
            reader.close();
        }
    }

    public List<Specialization> readSpecArray(JsonReader reader) throws IOException {
        List<Specialization> specializations = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            specializations.add(readSpec(reader));
        }
        reader.endArray();
        return specializations;
    }

    public Specialization readSpec(JsonReader reader) throws IOException {
        String id = null;
        String nameSpec = null;


        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextString();
            } else if (name.equals("name")) {
                nameSpec = reader.nextString();
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Specialization(id, nameSpec);
//        return new Doctor(id, nameMedOrg, photo, desc, specialization, qualification, services);
    }

    //Класс вызова потока для запроса списка мед органазаций
    class Specs extends AsyncTask<Void, Void, Void> {
//        private String[] doctors;
        private List<Specialization> list;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL("https://patient.simplex48.ru/api/Web/allspec/" + medorg.getId());

                HttpsURLConnection myConnection =
                        (HttpsURLConnection) url.openConnection();

                if (myConnection.getResponseCode() == 200) {
                    System.out.println("SUCCESS");
                } else {
                    System.out.println("ERROR");
                }

                InputStream responseBody = myConnection.getInputStream();

                list = readJsonStreamSpec(responseBody);



                for (Specialization s : list) {
                    specs.put(s.getId(), s.getName());
                }
                System.out.println(specs.size());

                myConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

//        @Override
//        protected void onPostExecute(Void result) {
//
//        }
    }
}
*/
