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
        //**

        //setContentView(R.layout.activity_main);
        LinearLayout linearLayout = new LinearLayout(getContext(), null, 0, R.style.profile_item);
        // горизонтальная ориентация
        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.setGravity(Gravity.TOP);
        // создаем параметры позиционирования для элемента
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        // устанавливаем отступы
        layoutParams.setMargins(0, 16, 0, 16);
        linearLayout.setPadding(20, 20, 20, 20);
//        layoutParams.setMargins(R.dimen.activity_horizontal_margin, R.dimen.activity_vertical_margin, R.dimen.activity_horizontal_margin, R.dimen.activity_vertical_margin);
        linearLayout.setLayoutParams(layoutParams);
        //textView.setLayoutParams(layoutParams);

        // добавляем элемент в LinearLayout
        TextView descr = new TextView(getContext());
        descr.setText("Описание");
        descr.setTextAppearance(R.style.TextAppearance_AppCompat_Body1);
        descr.setTextSize(18);
//        descr.setLayoutParams(layoutParams);
        linearLayout.addView(descr);
        // добавляем элемент в LinearLayout
        TextView descrText = new TextView(getContext());
        descrText.setText("Текст описания");
        descrText.setTextSize(18);
        descrText.setPadding(20, 0, 0, 0);
//        descr.setLayoutParams(layoutParams);
        linearLayout.addView(descrText);

        //**

        layout.addView(linearLayout);

//        getActivity().addContentView(linearLayout, layoutParams);


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
