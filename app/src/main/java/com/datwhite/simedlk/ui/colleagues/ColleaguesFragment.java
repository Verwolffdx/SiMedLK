package com.datwhite.simedlk.ui.colleagues;

import android.os.Build;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.datwhite.simedlk.App;
import com.datwhite.simedlk.MainActivity;
import com.datwhite.simedlk.R;
import com.datwhite.simedlk.entity.Doctor;
import com.datwhite.simedlk.ui.profile.ProfileFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import io.reactivex.disposables.CompositeDisposable;

public class ColleaguesFragment extends Fragment {
    private View root;
    private RecyclerView recyclerView;

    private String MED_ORG_ID;
    private List<Doctor> doctorList;

    CompositeDisposable disposable = new CompositeDisposable();
    private App app;
    private MainActivity mainActivity;
    private Bundle arguments;
    private LayoutInflater inf;

    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";

    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

//        System.out.println("RESUME!!!!");
        doctorList = new ArrayList<>(app.getDoctorList());
        doctorList.remove(app.getDoctor());
        ColleaguesAdapter adapter = new ColleaguesAdapter(createAdapter(), inf, doctorList);
        recyclerView.setAdapter(adapter);

//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener( new View.OnKeyListener()
//        {
//            @Override
//            public boolean onKey( View v, int keyCode, KeyEvent event )
//            {
//                if( keyCode == KeyEvent.KEYCODE_BACK )
//                {
//                    System.out.println("HZ!!! " + true);
////                    getActivity().getSupportFragmentManager().popBackStack();
//                    return true;
//                }
//                return false;
//            }
//        } );
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        inf = inflater;
        root = inflater.inflate(R.layout.fragment_colleagues, container, false);
        recyclerView = root.findViewById(R.id.colleagues_view);

        app = (App) getActivity().getApplication();
        mainActivity = (MainActivity) getActivity();




        ColleaguesAdapter adapter = new ColleaguesAdapter(createAdapter(), inflater, app.getDoctorList());
        recyclerView.setAdapter(adapter);

        return root;
    }

    private ColleaguesAdapter.OnColleagueClickListener createAdapter() {
        ColleaguesAdapter.OnColleagueClickListener colleagueClickListener = new ColleaguesAdapter.OnColleagueClickListener() {
            @Override
            public void onColleagueClick(Doctor doctor, int position) {


                Bundle bundle = new Bundle();
                bundle.putSerializable("doctor", doctor);
                bundle.putString("PROFILE_TYPE", "COLLEAGUE");
                mainActivity.getNavController().navigate(R.id.nav_profile, bundle);


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
