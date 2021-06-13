package com.datwhite.simedlk.ui.calendar;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarFragment extends Fragment {
    private View root;
    private App app;

    private Spinner spinner;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    private List<ActivityEntity> activityEntityList = new ArrayList<>();
    private List<WorkerData> workerDataList = new ArrayList<>();
    private List<String> patientsTime = new ArrayList<>();

    String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

    private TextView monday;
    private TextView tuesday;
    private TextView wednesday;
    private TextView thursday;
    private TextView friday;
    private TextView saturday;
    private TextView sunday;

    private LinearLayout grid;
    private LinearLayout week;

    private int swipeRightCount = 0;
    private int swipeLeftCount = 0;
    private int swipeCount = 0;

    private int current_month;
    private int current_week;

    private int chosenWeek = 0;
    private int chosenMonth = 0;


    @SuppressLint({"NewApi", "ResourceAsColor", "ClickableViewAccessibility"})
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_calendar, container, false);
        app = (App) getActivity().getApplication();

        spinner = (Spinner) root.findViewById(R.id.month_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        spinner.setAdapter(adapter);

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int day = calendar.get(java.util.Calendar.DAY_OF_WEEK);
        int month = calendar.get(java.util.Calendar.MONTH);
        current_month = month - 1;

        GridLayout gridLayout = root.findViewById(R.id.day_patients);
        GridLayout gridLayoutTimes = root.findViewById(R.id.times);

        monday = root.findViewById(R.id.monday);
        tuesday = root.findViewById(R.id.tuesday);
        wednesday = root.findViewById(R.id.wednesday);
        thursday = root.findViewById(R.id.thursday);
        friday = root.findViewById(R.id.friday);
        saturday = root.findViewById(R.id.saturday);
        sunday = root.findViewById(R.id.sunday);

        grid = root.findViewById(R.id.grid);

        week = root.findViewById(R.id.week);

        DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now());

        int ofWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK);
        int ofMonth = calendar.get(Calendar.DAY_OF_MONTH);

        current_week = calendar.get(Calendar.WEEK_OF_YEAR);
        current_month = calendar.get(Calendar.MONTH) + 1;

        String[] weekArr = {};
        weekArr = setWeekArray(true);
        setDatesOfWeek(weekArr);

        week.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeLeft() {
                swipeCount++;
                System.out.println(swipeCount);
                String[] weekArr = setWeekArray(true);
                setDatesOfWeek(weekArr);
                Toast.makeText(getContext(), "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                swipeCount--;
                System.out.println(swipeCount);
                String[] weekArr = setWeekArray(true);
                setDatesOfWeek(weekArr);
                Toast.makeText(getContext(), "right", Toast.LENGTH_SHORT).show();
            }
        });

        int hour = 0;
        int minute = 0;
        String h = "";
        String m = "";
        //Отображение времени
        for (int i = 0; i < 32; i++) {
            FrameLayout frameLayout = new FrameLayout(getContext());
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 140));
            frameLayout.setBackgroundResource(R.drawable.border);

//            for (int j = 8; j <= 18; j++) {
            if (hour == 0) {
                h = "8:";
                if (minute == 0) {
                    m = "00";
                    minute++;
                } else if (minute == 1) {
                    m = "20";
                    minute++;
                } else if (minute == 2) {
                    m = "40";
                    minute = 0;
                    hour++;
                }
            }
            else if (hour == 1) {
                h = "9:";
                if (minute == 0) {
                    m = "00";
                    minute++;
                } else if (minute == 1) {
                    m = "20";
                    minute++;
                } else if (minute == 2) {
                    m = "40";
                    minute = 0;;
                    hour++;
                }
            }
            else if (hour == 2) {
                h = "10:";
                if (minute == 0) {
                    m = "00";
                    minute++;
                } else if (minute == 1) {
                    m = "20";
                    minute++;
                } else if (minute == 2) {
                    m = "40";
                    minute = 0;
                    hour++;
                }
            }
            else if (hour == 3) {
                h = "11:";
                if (minute == 0) {
                    m = "00";
                    minute++;
                } else if (minute == 1) {
                    m = "20";
                    minute++;
                } else if (minute == 2) {
                    m = "40";
                    minute = 0;
                    hour++;
                }
            }
            else if (hour == 4) {
                h = "12:";
                if (minute == 0) {
                    m = "00";
                    minute++;
                } else if (minute == 1) {
                    m = "20";
                    minute++;
                } else if (minute == 2) {
                    m = "40";
                    minute = 0;
                    hour++;
                }
            }
            else if (hour == 5) {
                h = "13:";
                if (minute == 0) {
                    m = "00";
                    minute++;
                } else if (minute == 1) {
                    m = "20";
                    minute++;
                } else if (minute == 2) {
                    m = "40";
                    minute = 0;
                    hour++;
                }
            }
            else if (hour == 6) {
                h = "14:";
                if (minute == 0) {
                    m = "00";
                    minute++;
                } else if (minute == 1) {
                    m = "20";
                    minute++;
                } else if (minute == 2) {
                    m = "40";
                    minute = 0;
                    hour++;
                }
            }
            else if (hour == 7) {
                h = "15:";
                if (minute == 0) {
                    m = "00";
                    minute++;
                } else if (minute == 1) {
                    m = "20";
                    minute++;
                } else if (minute == 2) {
                    m = "40";
                    minute = 0;
                    hour++;
                }
            }
            else if (hour == 8) {
                h = "16:";
                if (minute == 0) {
                    m = "00";
                    minute++;
                } else if (minute == 1) {
                    m = "20";
                    minute++;
                } else if (minute == 2) {
                    m = "40";
                    minute = 0;
                    hour++;
                }
            }
            else if (hour == 9) {
                h = "17:";
                if (minute == 0) {
                    m = "00";
                    minute++;
                } else if (minute == 1) {
                    m = "20";
                    minute++;
                } else if (minute == 2) {
                    m = "40";
                    minute = 0;
                    hour++;
                }
            }
            else if (hour == 10) {
                h = "18:";
            }

//            }


            TextView textView = new TextView(getContext());
            textView.setText(h + m);
            textView.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
            textView.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setTextSize(10);
            textView.setTextColor(R.color.black);
            textView.setGravity(Gravity.CENTER);
            frameLayout.addView(textView);

            gridLayoutTimes.addView(frameLayout);

        }

        myRef = database.getReference("activity").child(app.getDoctor().getId() + "_from_" + app.getMedOrg().getId());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                snapshot.getChildrenCount();

                for (DataSnapshot postSnapshotOut : snapshot.getChildren()) {
                    for (DataSnapshot postSnapshotOInner : postSnapshotOut.getChildren()) {
                        WorkerData workerData = postSnapshotOInner.getValue(WorkerData.class);
//                        Log.e("Get Data", workerData.getDOCT_NAME());

//                        String[] workerDate = workerData.getREC_TIME().split("-|T");
//                        String date = workerDate[0] + "." + workerDate[1] + "." + workerDate[2];
//                        Log.e("DATE", date);
//                        int count = (int) postSnapshotOut.getChildrenCount();
//                        Log.e("COUNT", String.valueOf(count));
//                        activityEntityList.add(new ActivityEntity(date, count));
//                        adapter.notifyDataSetChanged();

//                        workerDataList.add(workerData);

                        patientsTime.add(workerData.getREC_TIME());

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        //Отображение ячеек
        for (int i = 0; i < 256; i++) {
            FrameLayout frameLayout = new FrameLayout(getContext());
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(140, 140));
            frameLayout.setBackgroundResource(R.drawable.border);

            int numOfColumn = 0;
            for (String s : patientsTime) {
                numOfColumn = i % 8;

                String[] workerDate = s.split("-|T|:");
                String date = workerDate[0] + "." + workerDate[1] + "." + workerDate[2];

                String dayOfWMonth = workerDate[2];

                String time = workerDate[3] + ":" + workerDate[4];

                String format = "yyyy-MM-dd";
                SimpleDateFormat df = new SimpleDateFormat(format);
                Date chosen_date = null;
                try {
                    chosen_date = df.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar cal = Calendar.getInstance();
                cal.setTime(chosen_date);
                int week = cal.get(Calendar.WEEK_OF_YEAR);

                if (week == chosenWeek) {
                    if (Integer.parseInt(dayOfWMonth) == numOfColumn) {
//                        ImageView
                    }
                }

            }

            gridLayout.addView(frameLayout);

        }


        return root;
    }

    @SuppressLint("ResourceAsColor")
    private void setDatesOfWeek(String[] week) {
        monday.setText(week[0]);
        tuesday.setText(week[1]);
        wednesday.setText(week[2]);
        thursday.setText(week[3]);
        friday.setText(week[4]);
        saturday.setText(week[5]);
        sunday.setText(week[6]);

        Calendar calendar = Calendar.getInstance();
        int ofWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        if (swipeCount == 0) {
            switch (ofWeek) {
                case 1:
                    monday.setBackgroundResource(R.drawable.today_circle);
                    monday.setTextColor(Color.parseColor("#000000"));
                    break;
                case 2:
                    tuesday.setBackgroundResource(R.drawable.today_circle);
                    tuesday.setTextColor(Color.parseColor("#000000"));
                    break;
                case 3:
                    wednesday.setBackgroundResource(R.drawable.today_circle);
                    wednesday.setTextColor(Color.parseColor("#000000"));
                    break;
                case 4:
                    thursday.setBackgroundResource(R.drawable.today_circle);
                    thursday.setTextColor(Color.parseColor("#000000"));
                    break;
                case 5:
                    friday.setBackgroundResource(R.drawable.today_circle);
                    friday.setTextColor(Color.parseColor("#000000"));
                    break;
                case 6:
                    saturday.setBackgroundResource(R.drawable.today_circle);
                    saturday.setTextColor(Color.parseColor("#000000"));
                    break;
                case 0:
                    sunday.setBackgroundResource(R.drawable.today_circle);
                    sunday.setTextColor(Color.parseColor("#000000"));
                    break;
            }
        } else {
            switch (ofWeek) {
                case 1:
                    monday.setBackgroundResource(R.drawable.circle_day);
                    monday.setTextColor(Color.parseColor("#FFFFFF"));
                    break;
                case 2:
                    tuesday.setBackgroundResource(R.drawable.circle_day);
                    tuesday.setTextColor(Color.parseColor("#FFFFFF"));
                    break;
                case 3:
                    wednesday.setBackgroundResource(R.drawable.circle_day);
                    wednesday.setTextColor(Color.parseColor("#FFFFFF"));
                    break;
                case 4:
                    thursday.setBackgroundResource(R.drawable.circle_day);
                    thursday.setTextColor(Color.parseColor("#FFFFFF"));
                    break;
                case 5:
                    friday.setBackgroundResource(R.drawable.circle_day);
                    friday.setTextColor(Color.parseColor("#FFFFFF"));
                    break;
                case 6:
                    saturday.setBackgroundResource(R.drawable.circle_day);
                    saturday.setTextColor(Color.parseColor("#FFFFFF"));
                    break;
                case 0:
                    sunday.setBackgroundResource(R.drawable.circle_day);
                    sunday.setTextColor(Color.parseColor("#FFFFFF"));
                    break;
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String[] setWeekArray(boolean current_week) {
        String[] arr = new String[7];
        Calendar calendar = Calendar.getInstance();
        int ofWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        switch (ofWeek) {
            case 1:
                if (swipeCount == 0) {
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now());
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(1));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(2));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(3));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(4));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(5));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(6));
                } else if (swipeCount > 0) {
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 0));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 1));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 2));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 3));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 4));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 5));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 6));
                } else if (swipeCount < 0) {
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 0));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 1));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 2));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 3));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 4));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 5));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 6));
                }
                break;
            case 2:
                if (swipeCount == 0) {
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(1));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now());
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(1));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(2));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(3));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(4));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(5));
                } else if (swipeCount > 0) {
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 1));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 0));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 1));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 2));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 3));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 4));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 5));
                } else if (swipeCount < 0) {
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 1));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 0));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 1));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 2));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 3));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 4));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 5));
                }
            case 3:
                if (swipeCount == 0) {
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(2));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(1));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now());
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(1));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(2));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(3));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(4));
                } else if (swipeCount > 0) {
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 2));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 1));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 0));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 1));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 2));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 3));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 4));
                } else if (swipeCount < 0) {
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 2));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 1));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 0));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 1));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 2));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 3));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 4));
                }
                break;
            case 4:
                if (swipeCount == 0) {
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(3));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(2));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(1));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now());
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(1));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(2));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(3));
                } else if (swipeCount > 0) {
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 3));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 2));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 1));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 0));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 1));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 2));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 3));
                } else if (swipeCount < 0) {
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 3));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 2));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 1));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 0));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 1));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 2));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 3));
                }
                break;
            case 5:
                if (swipeCount == 0) {
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(4));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(3));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(2));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(1));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now());
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(1));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(2));
                } else if (swipeCount > 0) {
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 4));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 3));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 2));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 1));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 0));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 1));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 2));
                } else if (swipeCount < 0) {
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 4));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 3));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 2));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 1));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 0));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 1));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 2));
                }
                break;
            case 6:

                if (swipeCount == 0) {
                    spinner.setSelection(Integer.parseInt(DateTimeFormatter.ofPattern("MM").format(LocalDateTime.now().minusDays(5))) - 1);
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(5));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(4));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(3));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(2));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(1));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now());
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(1));
                } else if (swipeCount > 0) {
                    spinner.setSelection(Integer.parseInt(DateTimeFormatter.ofPattern("MM").format(LocalDateTime.now().plusDays(swipeCount * 7 - 5))) - 1);
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 5));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 4));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 3));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 2));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 1));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 0));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 1));
                } else if (swipeCount < 0) {
                    spinner.setSelection(Integer.parseInt(DateTimeFormatter.ofPattern("MM").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 5))) - 1);
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 5));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 4));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 3));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 2));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 1));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 0));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 1));
                }
                break;
            case 0:
                if (swipeCount == 0) {
                    spinner.setSelection(Integer.parseInt(DateTimeFormatter.ofPattern("MM").format(LocalDateTime.now().minusDays(5))) - 1);
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(6));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(5));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(4));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(3));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(2));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(1));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now());
                } else if (swipeCount > 0) {
                    spinner.setSelection(Integer.parseInt(DateTimeFormatter.ofPattern("MM").format(LocalDateTime.now().plusDays(swipeCount * 7 - 5))) - 1);
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 6));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 5));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 4));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 3));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 2));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 1));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 0));
                } else if (swipeCount < 0) {
                    spinner.setSelection(Integer.parseInt(DateTimeFormatter.ofPattern("MM").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 5))) - 1);
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 6));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 5));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 4));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 3));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 2));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 1));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 0));
                }
                break;
        }


//        for (int i = 0; i < 7; i++) {
        System.out.println("Пн" + arr[0]);
        System.out.println("Вт" + arr[1]);
        System.out.println("Ср" + arr[2]);
        System.out.println("Чт" + arr[3]);
        System.out.println("Пт" + arr[4]);
        System.out.println("Сб" + arr[5]);
        System.out.println("Вс" + arr[6]);
//        }

        return arr;
    }
}