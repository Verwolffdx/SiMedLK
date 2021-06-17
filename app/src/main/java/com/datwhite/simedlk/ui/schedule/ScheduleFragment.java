package com.datwhite.simedlk.ui.schedule;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.datwhite.simedlk.App;
import com.datwhite.simedlk.MainActivity;
import com.datwhite.simedlk.R;
import com.datwhite.simedlk.entity.Doctor;
import com.datwhite.simedlk.entity.Patient;
import com.datwhite.simedlk.entity.auth.WorkerData;
import com.datwhite.simedlk.entity.schedule.Cell;
import com.datwhite.simedlk.entity.schedule.Schedule;
import com.datwhite.simedlk.entity.schedule.Worker;
import com.datwhite.simedlk.entity.schedule.WorkerCellsResponse;
import com.datwhite.simedlk.ui.colleagues.ColleaguesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

public class ScheduleFragment extends Fragment {

    private ScheduleViewModel scheduleViewModel;

    CompositeDisposable disposable = new CompositeDisposable();

    private App app;
    private WorkerCellsResponse workerCellsResponse;
    private RecyclerView recyclerView;

    private MainActivity mainActivity;

    private NavController navController;

    private TextView schedule_name;

    private LayoutInflater inf;

    private List<Cell> scheduleList;

    private List<WorkerData> todayPatients = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scheduleViewModel =
                new ViewModelProvider(this).get(ScheduleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);
        app = (App) getActivity().getApplication();
        inf = inflater;
        recyclerView = root.findViewById(R.id.recycler_schedule);

//        final TextView textView = root.findViewById(R.id.text_home);

        navController = (NavController) app.getNavController();

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                navController.navigate(R.id.nav_add_patient);
            }
        });


        if (app.getAuthResponse().getWorkerData() != null) {

            workerCellsResponse = app.getWorkerCellsResponse();
            todayPatients.clear();

            for (WorkerData w : app.getAuthResponse().getWorkerData()) {
                String[] workerDate = w.getREC_TIME().split("-|T|:");
                String date = workerDate[0] + "-" + workerDate[1] + "-" + workerDate[2];


                todayPatients.add(w);
            }



//            scheduleList = app.getWorkerCellsResponse().getWorkers().get(0).getSchedule().get(0).getCells();
//            List<Cell> schedule = new ArrayList<>();
//            for (Cell c : scheduleList) {
//                if (!c.isFree())
//                    schedule.add(c);
//            }
            ScheduleAdapter adapter = new ScheduleAdapter(createAdapter(), inf, todayPatients);
            recyclerView.setAdapter(adapter);
        } else {
            CoordinatorLayout schedule_main = root.findViewById(R.id.schedule_main);
            TextView textView = new TextView(getContext());
            textView.setLayoutParams(new CoordinatorLayout.LayoutParams
                    (CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT));
//            textView.setWidth(CoordinatorLayout.LayoutParams.MATCH_PARENT);
//            textView.setHeight(CoordinatorLayout.LayoutParams.WRAP_CONTENT);
            textView.setText("Сегодня записей нет");
            textView.setTextSize(24);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(24, 24, 24, 24);
            schedule_main.addView(textView);
        }




        /*
        System.out.println("RESPONSE");
        System.out.println("$id " + workerCellsResponse.get$id());
        System.out.println("message " + workerCellsResponse.getMessage());
        System.out.println("success " + workerCellsResponse.getSuccess());
        System.out.println("\tWORKER");
//        Worker w1 = workerCellsResponse.getWorkers().size();
//        System.out.println(workerCellsResponse.getWorkers().size());
        for (Worker w : workerCellsResponse.getWorkers()) {
            System.out.println("\t$id " + w.get$id());
            System.out.println("\twork_id " + w.getWork_id());
            System.out.println("\twork_surname " + w.getWork_surname());
            System.out.println("\twork_name " + w.getWork_name());
            System.out.println("\twork_patronimic " + w.getWork_patronimic());
            System.out.println("\treception_duration " + w.getReception_duration());
            System.out.println("\t\tSCHEDULE");
            for (Schedule s : w.getSchedule()) {
                System.out.println("\t\t$id " + s.get$id());
                System.out.println("\t\tworker_id " + s.getWorker_id());
                System.out.println("\t\tsched_id " + s.getSched_id());
                System.out.println("\t\tmedorg_id " + s.getMedorg_id());
                System.out.println("\t\tbranch_id " + s.getBranch_id());
                System.out.println("\t\tdoctor_id " + s.getDoctor_id());
                System.out.println("\t\t\tCELLS");
                for (Cell c : s.getCells()) {
                    System.out.println("\t\t\t$id " + c.get$id());
                    System.out.println("\t\t\tfree " + c.isFree());
                    System.out.println("\t\t\troom " + c.getRoom());
                    System.out.println("\t\t\tdate " + c.getDate());
                    System.out.println("\t\t\ttime_start " + c.getTime_start());
                    System.out.println("\t\t\ttime_end " + c.getTime_end());
                }
            }

        }

         */


//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    private ScheduleAdapter.OnScheduleClickListener createAdapter() {
        ScheduleAdapter.OnScheduleClickListener colleagueClickListener = new ScheduleAdapter.OnScheduleClickListener() {
            @Override
            public void onScheduleClick(WorkerData workerData, int position) {

                Bundle bundle = new Bundle();
                bundle.putString("cardNumber", workerData.getCARD_NUMBER());
                navController.navigate(R.id.nav_patient_info, bundle);

//                Bundle bundle = new Bundle();
//                bundle.putSerializable("doctor", doctor);
//                bundle.putString("PROFILE_TYPE", "COLLEAGUE");
//                mainActivity.getNavController().navigate(R.id.nav_profile, bundle);


//                ProfileFragment fragment = new ProfileFragment();

//                fragment.setArguments(bundle);
//
//
//
//
//
//
//                FragmentTransaction transaction = getFragmentManager().beginTransaction(); // Или getSupportFragmentManager(), если используете support.v4
//                transaction.add(R.id.nav_host_fragment, fragment, TAG_FRAGMENT); // Заменяете вторым фрагментом. Т.е. вместо метода `add()`, используете метод `replace()`
//                transaction.addToBackStack("Back"); // Добавляете в backstack, чтобы можно было вернутся обратно
//
//                transaction.commit();


//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.fragment_container_view, ContentFragment.class, null)
//                        .commit();
//                ProfileFragment.start(getContext(), doctor);
            }
        };

        return colleagueClickListener;
    }

}