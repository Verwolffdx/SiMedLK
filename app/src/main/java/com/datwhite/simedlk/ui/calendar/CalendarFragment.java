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

import com.datwhite.simedlk.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;

public class CalendarFragment extends Fragment {
    private View root;

    private Spinner spinner;

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

    @SuppressLint({"NewApi", "ResourceAsColor", "ClickableViewAccessibility"})
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_calendar, container, false);

        spinner = (Spinner) root.findViewById(R.id.month_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        spinner.setAdapter(adapter);

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int day = calendar.get(java.util.Calendar.DAY_OF_WEEK);
        int month = calendar.get(java.util.Calendar.MONTH);
        current_month = month-1;

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

        for (int i = 0; i < 12; i++) {
            FrameLayout frameLayout = new FrameLayout(getContext());
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 140));
            frameLayout.setBackgroundResource(R.drawable.border);

            TextView textView = new TextView(getContext());
            textView.setText("8:00");
            textView.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
            textView.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setTextSize(10);
            textView.setTextColor(R.color.black);
            textView.setGravity(Gravity.CENTER);
            frameLayout.addView(textView);

            gridLayoutTimes.addView(frameLayout);

        }

        for (int i = 0; i < 96; i++) {
            FrameLayout frameLayout = new FrameLayout(getContext());
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(140, 140));
            frameLayout.setBackgroundResource(R.drawable.border);

            gridLayout.addView(frameLayout);

        }




        return root;
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDatesOfWeek(String[] week) {
        monday.setText(week[0]);
        tuesday.setText(week[1]);
        wednesday.setText(week[2]);
        thursday.setText(week[3]);
        friday.setText(week[4]);
        saturday.setText(week[5]);
        sunday.setText(week[6]);

        Calendar calendar = Calendar.getInstance();
        int ofWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if (swipeCount == 0) {
            switch (ofWeek - 1) {
                case 1:
                    monday.setBackgroundResource(R.drawable.today_circle);
                    monday.setTextColor(R.color.black);
                    break;
                case 2:
                    tuesday.setBackgroundResource(R.drawable.today_circle);
                    tuesday.setTextColor(R.color.black);
                    break;
                case 3:
                    wednesday.setBackgroundResource(R.drawable.today_circle);
                    wednesday.setTextColor(R.color.black);
                    break;
                case 4:
                    thursday.setBackgroundResource(R.drawable.today_circle);
                    thursday.setTextColor(R.color.black);
                    break;
                case 5:
                    friday.setBackgroundResource(R.drawable.today_circle);
                    friday.setTextColor(R.color.black);
                    break;
                case 6:
                    saturday.setBackgroundResource(R.drawable.today_circle);
                    saturday.setTextColor(R.color.black);
                    break;
                case 7:
                    sunday.setBackgroundResource(R.drawable.today_circle);
                    sunday.setTextColor(R.color.black);
                    break;
            }
        } else {
            switch (ofWeek - 1) {
                case 1:
                    monday.setBackgroundResource(R.drawable.circle_day);
                    monday.setTextColor(R.color.white);
                    break;
                case 2:
                    tuesday.setBackgroundResource(R.drawable.circle_day);
                    tuesday.setTextColor(R.color.white);
                    break;
                case 3:
                    wednesday.setBackgroundResource(R.drawable.circle_day);
                    wednesday.setTextColor(R.color.white);
                    break;
                case 4:
                    thursday.setBackgroundResource(R.drawable.circle_day);
                    thursday.setTextColor(R.color.white);
                    break;
                case 5:
                    friday.setBackgroundResource(R.drawable.circle_day);
                    friday.setTextColor(R.color.white);
                    break;
                case 6:
                    saturday.setBackgroundResource(R.drawable.circle_day);
                    saturday.setTextColor(Color.parseColor("#FFFFFF"));

                    break;
                case 7:
                    sunday.setBackgroundResource(R.drawable.circle_day);
                    sunday.setTextColor(R.color.white);
                    break;
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String[] setWeekArray(boolean current_week) {
        String[] arr = new String[7];
        Calendar calendar = Calendar.getInstance();
        int ofWeek = calendar.get(Calendar.DAY_OF_WEEK);

        switch (ofWeek - 1) {
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
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 1));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 2));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 3));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 4));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 5));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 6));
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
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 1));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 0));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 1));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 2));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 3));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 4));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 5));
                } else if (swipeCount < 0) {
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 1));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 0));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 1));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 2));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 3));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 4));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 5));
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
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 2));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 1));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 0));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 1));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 2));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 3));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 4));
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
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(swipeCount * 7 - 3));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(swipeCount * 7 - 2));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 1));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 0));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 1));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 2));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 3));
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
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 4));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 3));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 2));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 1));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 0));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 1));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 2));
                }
                break;
            case 6:

                if (swipeCount == 0) {
                    spinner.setSelection(Integer.parseInt(DateTimeFormatter.ofPattern("MM").format(LocalDateTime.now().minusDays(5)))-1);
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(5));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(4));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(3));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(2));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(1));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now());
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(1));
                } else if (swipeCount > 0) {
                    spinner.setSelection(Integer.parseInt(DateTimeFormatter.ofPattern("MM").format(LocalDateTime.now().plusDays(swipeCount * 7 - 5)))-1);
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 5));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 4));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 3));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 2));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 1));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 0));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 1));
                } else if (swipeCount < 0) {
                    spinner.setSelection(Integer.parseInt(DateTimeFormatter.ofPattern("MM").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 5)))-1);
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 5));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 4));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 3));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 2));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 1));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 + 0));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 1));
                }
                break;
            case 7:
                if (swipeCount == 0) {
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(6));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(5));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(4));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(3));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(2));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(1));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now());
                } else if (swipeCount > 0) {
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 6));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 5));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 4));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 3));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 2));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 - 1));
                    arr[6] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().plusDays(swipeCount * 7 + 0));
                } else if (swipeCount < 0) {
                    arr[0] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 6));
                    arr[1] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 5));
                    arr[2] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 4));
                    arr[3] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 3));
                    arr[4] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 2));
                    arr[5] = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now().minusDays(Math.abs(swipeCount) * 7 - 1));
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