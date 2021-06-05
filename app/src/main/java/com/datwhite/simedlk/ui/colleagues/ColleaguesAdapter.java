package com.datwhite.simedlk.ui.colleagues;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datwhite.simedlk.R;
import com.datwhite.simedlk.entity.Doctor;

import java.util.List;

public class ColleaguesAdapter extends RecyclerView.Adapter<ColleaguesAdapter.ViewHolder> {
    interface OnColleagueClickListener{
        void onColleagueClick(Doctor doctor, int position);
    }

    private final OnColleagueClickListener onClickListener;


    private final LayoutInflater inflater;
    private final List<Doctor> doctorList;

    public ColleaguesAdapter(OnColleagueClickListener onClickListener, LayoutInflater inflater, List<Doctor> doctorList) {
        this.onClickListener = onClickListener;
        this.inflater = inflater;
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public ColleaguesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_colleagues, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColleaguesAdapter.ViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.docName.setText(doctor.getName());
        //Фото
        if (!doctor.getPhoto().equals("-1") && !doctor.getPhoto().equals("")) {
            byte[] decodedByte = Base64.decode(doctor.getPhoto(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
            holder.colleague_profile_photo.setImageBitmap(bitmap);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onColleagueClick(doctor, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
//        final ImageView docPhoto;
        final TextView docName;
        final ImageView colleague_profile_photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            docPhoto = (ImageView) itemView.findViewById(R.id.colleaguesImageView);
            docName = (TextView) itemView.findViewById(R.id.colleaguesName);
            colleague_profile_photo = (ImageView) itemView.findViewById(R.id.colleague_profile_photo);
        }
    }
}
