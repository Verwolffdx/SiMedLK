package com.datwhite.simedlk;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.datwhite.simedlk.entity.Doctor;
import com.datwhite.simedlk.entity.MedOrg;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class AuthActivity extends AppCompatActivity {
    private Button btn_auth;
    private MedOrg medorg;
    private Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        btn_auth = findViewById(R.id.btn_auth);
        btn_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                intent.putExtra(Doctor.class.getSimpleName(), doctor);
                intent.putExtra(MedOrg.class.getSimpleName(), medorg);
                startActivity(intent);
                onPause();
            }
        });

        Medorglist myTask = new Medorglist();
        myTask.execute();
    }

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
                    medorglist[i] = list.get(i).getName();
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
            Spinner spinner = (Spinner) findViewById(R.id.spinnerMedorg);
            // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AuthActivity.this, android.R.layout.simple_spinner_item, medorglist);
            // Определяем разметку для использования при выборе элемента
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Применяем адаптер к элементу spinner
            spinner.setAdapter(adapter);


            AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // Получаем выбранный объект
                    String item = (String)parent.getItemAtPosition(position);
                    for (MedOrg medOrg : list) {
                        if (item.equals(medOrg.getName())) {
                            medorg = medOrg;
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
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Doctor(id, nameMedOrg, photo, desc, specialization, qualification, services);
    }

    class Doctors extends AsyncTask<Void, Void, Void> {
        private String[] doctors;
        private List<Doctor> list;
//        public Doctors(MedOrg medorg) {
//            this.medorg = medorg;
//        }

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
            TextView textView = (TextView) findViewById(R.id.text);
            Spinner spinner = (Spinner) findViewById(R.id.spinnerFio);
            // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AuthActivity.this, android.R.layout.simple_spinner_item, doctors);
            // Определяем разметку для использования при выборе элемента
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Применяем адаптер к элементу spinner
            spinner.setAdapter(adapter);

            AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // Получаем выбранный объект
                    String item = (String)parent.getItemAtPosition(position);
                    for (Doctor doct : list) {
                        if (item.equals(doct.getName())) {
                            doctor = doct;
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
}
