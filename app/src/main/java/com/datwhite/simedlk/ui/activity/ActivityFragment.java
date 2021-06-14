package com.datwhite.simedlk.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datwhite.simedlk.App;
import com.datwhite.simedlk.R;
import com.datwhite.simedlk.entity.ActivityEntity;
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
import java.util.List;

public class ActivityFragment extends Fragment {

    private View root;
    private RecyclerView recyclerView;
    private App app;
    private LayoutInflater inf;

    private List<ActivityEntity> activityEntityList = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_activity, container, false);
        recyclerView = root.findViewById(R.id.activity_recycler_view);
        app = (App) getActivity().getApplication();
        inf = inflater;

        myRef = database.getReference("activity").child(app.getDoctor().getId() + "_from_" + app.getMedOrg().getId());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ActivityAdapter adapter = new ActivityAdapter(inf, activityEntityList);

        recyclerView.setAdapter(adapter);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                snapshot.getChildrenCount();
//                ActivityEntity activityEntity = new ActivityEntity();
                for (DataSnapshot postSnapshotOut : snapshot.getChildren()) {
                    for (DataSnapshot postSnapshotOInner : postSnapshotOut.getChildren()) {
                        WorkerData workerData = postSnapshotOInner.getValue(WorkerData.class);
//                        Log.e("Get Data", workerData.getDOCT_NAME());

                        String[] workerDate = workerData.getREC_TIME().split("-|T");
                        String date = workerDate[0] + "." + workerDate[1] + "." + workerDate[2];
//                        Log.e("DATE", date);
                        int count = (int) postSnapshotOut.getChildrenCount();
//                        Log.e("COUNT", String.valueOf(count));
//                        activityEntity.setActivity_date(date);
//                        activityEntity.setCount_patients(count);



                        if (activityEntityList.size() == 0) {
//                            System.out.println(" 0 " + activityEntity.getActivity_date());
                            activityEntityList.add(new ActivityEntity(date, count));
                            adapter.notifyDataSetChanged();
                        } else if (!activityEntityList.get(activityEntityList.size()-1).getActivity_date().equals(date)) {

                            activityEntityList.add(new ActivityEntity(date, count));
                            adapter.notifyDataSetChanged();
//                            System.out.println("HZ " + activityEntity.getActivity_date());
                        }




//                        System.out.println("activityEntityList " + activityEntityList.size());


                    }
                }

                for(ActivityEntity a : activityEntityList) {
                    System.out.println(a.getActivity_date());
                }


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        return root;
    }

}
