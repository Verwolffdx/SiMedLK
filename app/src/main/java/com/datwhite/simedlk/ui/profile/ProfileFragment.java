package com.datwhite.simedlk.ui.profile;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.datwhite.simedlk.R;
import com.datwhite.simedlk.entity.Doctor;

public class ProfileFragment extends Fragment {

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        Doctor doctor = (Doctor) getArguments().getSerializable("doctor");
        System.out.println("DOCTOR " + doctor.getName());

        TextView docName = root.findViewById(R.id.doctorName);
        TextView docSpec = root.findViewById(R.id.doctorSpec);

        docName.setText(doctor.getName());
        if (doctor.getSpecialization().length() > 0)
            docSpec.setText(doctor.getSpecialization());

        LinearLayout layout = root.findViewById(R.id.profile_layout);

        //Описание
        //setContentView(R.layout.activity_main);
        LinearLayout descrLayout = new LinearLayout(getContext(), null, 0, R.style.profile_item);
        // горизонтальная ориентация
        descrLayout.setOrientation(LinearLayout.VERTICAL);
//        descrLayout.setGravity(Gravity.TOP);
        // создаем параметры позиционирования для элемента
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // устанавливаем отступы
        layoutParams.setMargins(0, 16, 0, 16);
        descrLayout.setPadding(20, 20, 20, 20);
//        layoutParams.setMargins(R.dimen.activity_horizontal_margin, R.dimen.activity_vertical_margin, R.dimen.activity_horizontal_margin, R.dimen.activity_vertical_margin);
        descrLayout.setLayoutParams(layoutParams);
        //textView.setLayoutParams(layoutParams);
        // добавляем элемент в LinearLayout
        TextView descr = new TextView(getContext());
        descr.setText("Описание");
        descr.setTextAppearance(R.style.TextAppearance_AppCompat_Body1);
        descr.setTextSize(18);
//        descr.setLayoutParams(layoutParams);
        descrLayout.addView(descr);
        // добавляем элемент в LinearLayout
        TextView descrText = new TextView(getContext());
        descrText.setText("Текст описания");
        descrText.setTextSize(18);
        descrText.setPadding(20, 0, 0, 0);
//        descr.setLayoutParams(layoutParams);
        descrLayout.addView(descrText);

        layout.addView(descrLayout);

        //Квалификация
        //setContentView(R.layout.activity_main);
        LinearLayout qualifLayout = new LinearLayout(getContext(), null, 0, R.style.profile_item);
        // горизонтальная ориентация
        qualifLayout.setOrientation(LinearLayout.VERTICAL);
//        descrLayout.setGravity(Gravity.TOP);
        // создаем параметры позиционирования для элемента
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
//                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // устанавливаем отступы
        layoutParams.setMargins(0, 16, 0, 16);
        qualifLayout.setPadding(20, 20, 20, 20);
//        layoutParams.setMargins(R.dimen.activity_horizontal_margin, R.dimen.activity_vertical_margin, R.dimen.activity_horizontal_margin, R.dimen.activity_vertical_margin);
        qualifLayout.setLayoutParams(layoutParams);
        //textView.setLayoutParams(layoutParams);
        // добавляем элемент в LinearLayout
        TextView qualif = new TextView(getContext());
        qualif.setText("Квалификация");
        qualif.setTextAppearance(R.style.TextAppearance_AppCompat_Body1);
        qualif.setTextSize(18);
//        qualif.setLayoutParams(layoutParams);
        qualifLayout.addView(qualif);
        // добавляем элемент в LinearLayout
        TextView qualifText = new TextView(getContext());
        qualifText.setText("Текст квалификации");
        qualifText.setTextSize(18);
        qualifText.setPadding(20, 0, 0, 0);
//        qualif.setLayoutParams(layoutParams);
        qualifLayout.addView(qualifText);

        layout.addView(qualifLayout);

        //Оказываемые услуги
        //setContentView(R.layout.activity_main);
        LinearLayout servicesLayout = new LinearLayout(getContext(), null, 0, R.style.profile_item);
        // горизонтальная ориентация
        servicesLayout.setOrientation(LinearLayout.VERTICAL);
//        descrLayout.setGravity(Gravity.TOP);
        // создаем параметры позиционирования для элемента
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
//                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // устанавливаем отступы
        layoutParams.setMargins(0, 16, 0, 16);
        servicesLayout.setPadding(20, 20, 20, 20);
//        layoutParams.setMargins(R.dimen.activity_horizontal_margin, R.dimen.activity_vertical_margin, R.dimen.activity_horizontal_margin, R.dimen.activity_vertical_margin);
        servicesLayout.setLayoutParams(layoutParams);
        //textView.setLayoutParams(layoutParams);
        // добавляем элемент в LinearLayout
        TextView services = new TextView(getContext());
        services.setText("Оказываемые услуги");
        services.setTextAppearance(R.style.TextAppearance_AppCompat_Body1);
        services.setTextSize(18);
//        services.setLayoutParams(layoutParams);
        servicesLayout.addView(services);
        // добавляем элемент в LinearLayout
        TextView servicesText = new TextView(getContext());
        servicesText.setText("Услуги");
        servicesText.setTextSize(18);
        servicesText.setPadding(20, 0, 0, 0);
//        services.setLayoutParams(layoutParams);
        servicesLayout.addView(servicesText);

        layout.addView(servicesLayout);

//        getActivity().addContentView(descrLayout, layoutParams);


//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        // create a layout
//        LinearLayout layout = new LinearLayout(getContext());
//        layout.setOrientation(LinearLayout.VERTICAL);
//        // create a textview
//        TextView textView = new TextView(getContext());
//        textView.setText("This is a TextView");
//        textView.setLayoutParams(params);
//        // create a button
//        Button button = new Button(getContext());
//        button.setText("This is a Button");
//        button.setLayoutParams(params);
//        // adds the textview
//        layout.addView(textView);
//        // adds the button
//        layout.addView(button);
//        // create a layout param for the layout
//        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        this.addContentView(layout, layoutParam);

        return root;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);
//    }
}
