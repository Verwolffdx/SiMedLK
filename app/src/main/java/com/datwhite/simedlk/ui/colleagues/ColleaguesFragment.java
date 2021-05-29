package com.datwhite.simedlk.ui.colleagues;

import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.datwhite.simedlk.App;
import com.datwhite.simedlk.R;
import com.datwhite.simedlk.entity.Doctor;

import io.reactivex.disposables.CompositeDisposable;

public class ColleaguesFragment extends Fragment {
    private View root;
    private RecyclerView recyclerView;

    private String MED_ORG_ID;

    CompositeDisposable disposable = new CompositeDisposable();
    private App app;
    private Bundle arguments;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_colleagues, container, false);
        recyclerView = root.findViewById(R.id.colleagues_view);

        app = (App) getActivity().getApplication();

        ColleaguesAdapter.OnColleagueClickListener colleagueClickListener = new ColleaguesAdapter.OnColleagueClickListener() {
            @Override
            public void onColleagueClick(Doctor doctor, int position) {
                Toast.makeText(getContext(), "Был выбран коллега " + doctor.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        };

        ColleaguesAdapter adapter = new ColleaguesAdapter(colleagueClickListener, inflater, app.getDoctorList());
        recyclerView.setAdapter(adapter);

        return root;
    }



}
