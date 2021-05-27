package com.datwhite.simedlk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.datwhite.simedlk.entity.Doctor;
import com.datwhite.simedlk.entity.MedOrg;
import com.datwhite.simedlk.ui.profile.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String EXTRA_DATA = "SecondActivity.EXTRA_DATA";

    private AppBarConfiguration mAppBarConfiguration;
    private Bundle arguments;

    public static void start(Context caller, Doctor doctor, ArrayList<Doctor> colleagues) {
        Intent intent = new Intent(caller, MainActivity.class);
        intent.putExtra(Doctor.class.getSimpleName(), (Parcelable) doctor);
        intent.putParcelableArrayListExtra("COLLEAGUES", colleagues);
        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                R.id.nav_schedule, R.id.nav_calendar, R.id.nav_events)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        arguments = getIntent().getExtras();
        if (arguments != null) {
            Doctor doctor = (Doctor) arguments.getSerializable(Doctor.class.getSimpleName());
            MedOrg medOrg = (MedOrg) arguments.getSerializable(MedOrg.class.getSimpleName());

            List<Doctor> colleagues = new ArrayList<>();
//            System.out.println(arguments.getParcelableArray("colleagues"));

//            NavDestination orderDestination = navController.getGraph().findNode(R.id.nav_colleagues);
//            orderDestination.addArgument("medOrgId", new NavArgument.Builder()
//                    .setType(NavType.StringType)
//                    .setDefaultValue(arguments.getSerializable(medOrg.getId()))
//                    .build());



            //Обработка нажатия на хэдер меню (профиль)
            View headerView = navigationView.getHeaderView(0);
            headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HashMap<String, String> specializations = (HashMap<String, String>) arguments.getSerializable("specialization");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("doctor", doctor);
                    bundle.putSerializable("medorg", medOrg);
                    bundle.putSerializable("specialization", specializations);
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

        }
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
}