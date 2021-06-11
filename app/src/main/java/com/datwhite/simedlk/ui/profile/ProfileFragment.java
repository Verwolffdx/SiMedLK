package com.datwhite.simedlk.ui.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.datwhite.simedlk.App;
import com.datwhite.simedlk.MainActivity;
import com.datwhite.simedlk.R;
import com.datwhite.simedlk.entity.Doctor;
import com.datwhite.simedlk.ui.chat.ChatFragment;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {
    private View root;
    private App app;

    public static void start(Context caller, Doctor doctor) {
        Intent intent = new Intent(caller, MainActivity.class);
        caller.startActivity(intent);
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_profile, container, false);
        app = (App) getActivity().getApplication();

        Doctor doctor = (Doctor) getArguments().getSerializable("doctor");
//        Doctor doctor = app.getDoctor();
//        System.out.println("DOCTOR " + doctor.getName());
//        System.out.println("PROFILE DOCTid " + doctor.getDOCT_IDs());

        TextView docName = root.findViewById(R.id.doctorName);
//        TextView docSpec = root.findViewById(R.id.doctorSpec);

        docName.setText(doctor.getName());
//        if (doctor.getSpecialization().length() > 0)
//            docSpec.setText(doctor.getSpecialization());

//        //Скрол страницы
//        ScrollView scrollView = new ScrollView(getContext());

        LinearLayout layout = root.findViewById(R.id.profile_layout);
        // создаем параметры позиционирования для элемента
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if (getArguments().getString("PROFILE_TYPE").equals("COLLEAGUE")) {
            Button chatBtn = new Button(getContext(), null, 0, R.style.auth_button);
            chatBtn.setText("Перейти в чат");
            chatBtn.setTextSize(18);
            chatBtn.setGravity(Gravity.CENTER);
            layoutParams.setMargins(0, 16, 0, 16);
            chatBtn.setLayoutParams(layoutParams);
            chatBtn.setPadding(20, 20, 20, 20);
            layout.addView(chatBtn);

            chatBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    MainActivity mainActivity = (MainActivity) getActivity();
//                    Bundle bundle = new Bundle();
//                    bundle.putParcelable("doctor", doctor);
//                    mainActivity.getNavController().navigate(R.id.chat, bundle);
                    ChatFragment.start(v.getContext(), doctor);
                    onPause();
                }
            });
        }

        //Фото
        if (!doctor.getPhoto().equals("-1") && !doctor.getPhoto().equals("")) {
            ImageView profile_photo = root.findViewById(R.id.profile_photo);
            byte[] decodedByte = Base64.decode(doctor.getPhoto(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
            profile_photo.setImageBitmap(bitmap);
        }

        //Описание
        if (!doctor.getDesc().equals("") && !doctor.getDesc().equals("-1")) {
            LinearLayout descrLayout = new LinearLayout(getContext(), null, 0, R.style.profile_item);
            // горизонтальная ориентация
            descrLayout.setOrientation(LinearLayout.VERTICAL);

            // устанавливаем отступы
            layoutParams.setMargins(0, 16, 0, 16);
            descrLayout.setPadding(20, 20, 20, 20);
            descrLayout.setLayoutParams(layoutParams);
            // добавляем элемент в LinearLayout
            TextView descr = new TextView(getContext());
            descr.setText("Описание");
            descr.setTextAppearance(R.style.TextAppearance_AppCompat_Body1);
            descr.setTextSize(18);
            descrLayout.addView(descr);
            // добавляем элемент в LinearLayout
            TextView descrText = new TextView(getContext());
            descrText.setText(doctor.getDesc());
            descrText.setTextSize(18);
            descrText.setPadding(20, 0, 0, 0);
            descrLayout.addView(descrText);

            layout.addView(descrLayout);
        }

        //Квалификация
        if (!doctor.getQualification().equals("")) {
            LinearLayout qualifLayout = new LinearLayout(getContext(), null, 0, R.style.profile_item);
            // горизонтальная ориентация
            qualifLayout.setOrientation(LinearLayout.VERTICAL);
            // устанавливаем отступы
            layoutParams.setMargins(0, 16, 0, 16);
            qualifLayout.setPadding(20, 20, 20, 20);
            qualifLayout.setLayoutParams(layoutParams);
            // добавляем элемент в LinearLayout
            TextView qualif = new TextView(getContext());
            qualif.setText("Квалификация");
            qualif.setTextAppearance(R.style.TextAppearance_AppCompat_Body1);
            qualif.setTextSize(18);
            qualifLayout.addView(qualif);
            // добавляем элемент в LinearLayout
            TextView qualifText = new TextView(getContext());
            qualifText.setText(doctor.getQualification());
            qualifText.setTextSize(18);
            qualifText.setPadding(20, 0, 0, 0);
            qualifLayout.addView(qualifText);

            layout.addView(qualifLayout);
        }

        //Оказываемые услуги
        if (!doctor.getServices().equals("")) {
            LinearLayout servicesLayout = new LinearLayout(getContext(), null, 0, R.style.profile_item);
            // горизонтальная ориентация
            servicesLayout.setOrientation(LinearLayout.VERTICAL);
            // устанавливаем отступы
            layoutParams.setMargins(0, 16, 0, 16);
            servicesLayout.setPadding(20, 20, 20, 20);
            servicesLayout.setLayoutParams(layoutParams);

            // добавляем элемент в LinearLayout
            TextView services = new TextView(getContext());
            services.setText("Оказываемые услуги");
            services.setTextAppearance(R.style.TextAppearance_AppCompat_Body1);
            services.setTextSize(18);
            servicesLayout.addView(services);
            // добавляем элемент в LinearLayout
            TextView servicesText = new TextView(getContext());
            servicesText.setText(doctor.getServices());
            servicesText.setTextSize(18);
            servicesText.setPadding(20, 0, 0, 0);
            servicesLayout.addView(servicesText);

            layout.addView(servicesLayout);
        }

        //Специальности
//        for (Integer i : doctor.getDOCT_IDs())
//            System.out.println(i);

        if (doctor.getDOCT_IDs().size() > 0 && app.getSpecializations().size() > 0) {
            LinearLayout specsLayout = new LinearLayout(getContext(), null, 0, R.style.profile_item);
            // горизонтальная ориентация
            specsLayout.setOrientation(LinearLayout.VERTICAL);
            // устанавливаем отступы
            layoutParams.setMargins(0, 16, 0, 16);
            specsLayout.setPadding(20, 20, 20, 20);
            specsLayout.setLayoutParams(layoutParams);

            // добавляем элемент в LinearLayout
            TextView specs = new TextView(getContext());
            specs.setText("Специальность");
            specs.setTextAppearance(R.style.TextAppearance_AppCompat_Body1);
            specs.setTextSize(18);
            specsLayout.addView(specs);
            // добавляем элемент в LinearLayout
//            HashMap<String, String> specializations = (HashMap<String, String>) getArguments().getSerializable("specialization");
            Map<String, String> specializations = app.getSpecializations();
            for (int i : doctor.getDOCT_IDs()) {
//                System.out.println(specializations.get(Integer.toString(i)));
                if (specializations.get(Integer.toString(i)) == null)
                    continue;
                TextView specsText = new TextView(getContext());
                specsText.setText(specializations.get(Integer.toString(i)));
                specsText.setTextSize(18);
                specsText.setPadding(20, 0, 0, 0);
                specsLayout.addView(specsText);
            }

            layout.addView(specsLayout);
        }

//        scrollView.addView(layout);



        return root;
    }



}
