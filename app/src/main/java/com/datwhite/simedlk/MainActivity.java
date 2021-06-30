package com.datwhite.simedlk;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.datwhite.simedlk.entity.Doctor;
import com.datwhite.simedlk.entity.MedOrg;
import com.datwhite.simedlk.entity.Specialization;
import com.datwhite.simedlk.entity.auth.WorkerData;
import com.datwhite.simedlk.ui.profile.ProfileFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavType;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String EXTRA_DATA = "SecondActivity.EXTRA_DATA";
    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";

    private AppBarConfiguration mAppBarConfiguration;
    private Bundle arguments;
    private NavController navController;
    private App app;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        app = (App) getApplication();

        myRef = database.getReference("activity").child(app.getDoctor().getId() + "_from_" + app.getMedOrg().getId());


//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_schedule, R.id.nav_calendar, R.id.nav_colleagues, R.id.nav_activity)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        app.setNavController(navController);

        arguments = getIntent().getExtras();
        if (app.getDoctor().getName() != null) {
//            Doctor doctor = (Doctor) arguments.getSerializable(Doctor.class.getSimpleName());
            Doctor doctor = app.getDoctor();
            MedOrg medOrg = app.getMedOrg();
//            MedOrg medOrg = (MedOrg) arguments.getSerializable(MedOrg.class.getSimpleName());



            //Обработка нажатия на хэдер меню (профиль)
            View headerView = navigationView.getHeaderView(0);
            headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    HashMap<String, String> specializations = (HashMap<String, String>) arguments.getSerializable("SPECIALIZATIONS");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("doctor", doctor);
//                    bundle.putSerializable("medorg", medOrg);s
//                    bundle.putSerializable("specialization", specializations);
                    bundle.putString("PROFILE_TYPE", "DOCTOR");
                    //Переход на страницу с профилем

                    navController.navigate(R.id.nav_profile, bundle);
                    drawer.close();

                    //Анимации
//                    navController.navigate(
//                            R.id.EditProfileFragment,
//                            null,
//                            new NavOptions.Builder()
//                                    .setEnterAnim(android.R.animator.fade_in)
//                                    .setExitAnim(android.R.animator.fade_out)
//                                    .build());

//                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
//                    startActivity(intent);
//                    onPause();
                }
            });

            TextView docName = (TextView) headerView.findViewById(R.id.doctorName);
//            TextView docSpec = (TextView) headerView.findViewById(R.id.doctorSpec);

//            HashMap<String, String> specializations = (HashMap<String, String>) arguments.getSerializable("specialization");

            docName.setText(doctor.getName());
//            if (specializations.size() > 0)
//                docSpec.setText(specializations.get(doctor.getDOCT_IDs().get(0).toString()));

            //Фото
            if (!doctor.getPhoto().equals("-1") && !doctor.getPhoto().equals("")) {
                ImageView profile_photo = (ImageView) headerView.findViewById(R.id.menu_profile_photo);
                byte[] decodedByte = Base64.decode(doctor.getPhoto(), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
                profile_photo.setImageBitmap(bitmap);
            }



        }

        //Календарь
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                snapshot.getChildrenCount();
                List<String> patientsTime = new ArrayList<>();
                for (DataSnapshot postSnapshotOut : snapshot.getChildren()) {
                    for (DataSnapshot postSnapshotOInner1 : postSnapshotOut.getChildren()) {
                        for (DataSnapshot postSnapshotOInner : postSnapshotOInner1.getChildren()) {
                            WorkerData workerData = postSnapshotOInner.getValue(WorkerData.class);
                            patientsTime.add(workerData.getREC_TIME());
//                        System.out.println("workerData " + workerData.getREC_TIME());
//                        System.out.println("postSnapshotOInner " + postSnapshotOut.getChildrenCount());
                        }
                    }
                }

                app.setPatientsTime(patientsTime);

                System.out.println("snapshot.getChildrenCount(); " + snapshot.getChildrenCount());
                System.out.println("ADD PATIENTS TIME");

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    ////Обработка нажатия на кнопкку настроек (правый верхний угол)
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }


    //Обработка нажатия на кнопкку меню (левый верхний угол)
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public NavController getNavController() {
        return navController;
    }
}