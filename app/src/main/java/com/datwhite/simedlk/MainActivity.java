package com.datwhite.simedlk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.datwhite.simedlk.entity.Doctor;
import com.datwhite.simedlk.entity.MedOrg;
import com.datwhite.simedlk.ui.profile.ProfileActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Bundle arguments;

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
        Doctor doctor;
        if (arguments != null) {
            doctor = (Doctor) arguments.getSerializable(Doctor.class.getSimpleName());

            View headerView = navigationView.getHeaderView(0);

            headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Use the Kotlin extension in the -ktx artifacts


                    // Now use the generated Directions class to navigate to the destination
                    navController.navigate(R.id.nav_profile);
                    drawer.close();

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
            TextView docSpec = (TextView) headerView.findViewById(R.id.doctorSpec);

            docName.setText(doctor.getName());
            if (doctor.getSpecialization().length() > 0)
                docSpec.setText(doctor.getSpecialization());

        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}