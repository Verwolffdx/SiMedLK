package com.datwhite.simedlk.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datwhite.simedlk.App;
import com.datwhite.simedlk.AuthActivity;
import com.datwhite.simedlk.R;
import com.datwhite.simedlk.entity.ActivityEntity;
import com.datwhite.simedlk.entity.MedOrg;
import com.datwhite.simedlk.entity.auth.WorkerData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityFragment extends Fragment {

    private View root;
    private RecyclerView recyclerView;
    private Spinner spinnerBranch;
    private App app;
    private LayoutInflater inf;
    private int branch = 0;

    private List<ActivityEntity> activityEntityList = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_activity, container, false);
        recyclerView = root.findViewById(R.id.activity_recycler_view);
        spinnerBranch = root.findViewById(R.id.branchSpinner);
        app = (App) getActivity().getApplication();
        inf = inflater;


        myRef = database.getReference("activity").child(app.getDoctor().getId() + "_from_" + app.getMedOrg().getId());

        String[] brList = new String[app.getBranchList().size()];
        for (Map.Entry<String, String> entry : app.getBranchList().entrySet()) {
            brList[Integer.parseInt(entry.getKey()) - 1] = entry.getValue();
        }
//
//        String[] brList = new String[app.getBranchList().size()];
//        brList[0] = "Основная поликлиника";
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_item, brList);
        // Определяем разметку для использования при выборе элемента
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinnerBranch.setAdapter(arrayAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ActivityAdapter adapter = new ActivityAdapter(inf, activityEntityList, branch);

        recyclerView.setAdapter(adapter);

        //Обработка выбора мед организации из списка
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String item = (String) parent.getItemAtPosition(position);

                for (Map.Entry<String, String> entry : app.getBranchList().entrySet()) {
                    if (item.equals(entry.getValue())) {
                        branch = Integer.parseInt(entry.getKey()) - 1;


                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                snapshot.getChildrenCount();
//                ActivityEntity activityEntity = new ActivityEntity();
                                int br;
                                for (DataSnapshot postSnapshotOut : snapshot.getChildren()) {

                                    for (DataSnapshot postSnapshotOInner1 : postSnapshotOut.getChildren()) {
                                        br = Integer.parseInt(postSnapshotOInner1.getKey()) - 1;
                                        System.out.println("BR " + br);
                                        System.out.println("branch " + branch);

                                        if (br == branch) {
                                            activityEntityList.clear();
                                            for (DataSnapshot postSnapshotOInner : postSnapshotOInner1.getChildren()) {
                                                WorkerData workerData = postSnapshotOInner.getValue(WorkerData.class);
//                        Log.e("Get Data", workerData.getDOCT_NAME());

                                                String[] workerDate = workerData.getREC_TIME().split("-|T");
                                                String date = workerDate[0] + "." + workerDate[1] + "." + workerDate[2];
//                        Log.e("DATE", date);
                                                int count = (int) postSnapshotOInner1.getChildrenCount();
//                        Log.e("COUNT", String.valueOf(count));
//                        activityEntity.setActivity_date(date);
//                        activityEntity.setCount_patients(count);


                                                if (activityEntityList.size() == 0) {
//                            System.out.println(" 0 " + activityEntity.getActivity_date());
                                                    activityEntityList.add(new ActivityEntity(date, count));
                                                    adapter.notifyDataSetChanged();
                                                } else if (!activityEntityList.get(activityEntityList.size() - 1).getActivity_date().equals(date)) {

                                                    activityEntityList.add(new ActivityEntity(date, count));
                                                    adapter.notifyDataSetChanged();
//                            System.out.println("HZ " + activityEntity.getActivity_date());
                                                }


//                        System.out.println("activityEntityList " + activityEntityList.size());


                                            }
                                        }
                                    }
                                }

//                                for (ActivityEntity a : activityEntityList) {
//                                    System.out.println(a.getActivity_date());
//                                }


                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });



                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinnerBranch.setOnItemSelectedListener(itemSelectedListener);





        return root;
    }

}
