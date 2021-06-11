package com.datwhite.simedlk.ui.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datwhite.simedlk.R;
import com.datwhite.simedlk.entity.ActivityEntity;
import com.datwhite.simedlk.entity.auth.WorkerData;
import com.datwhite.simedlk.ui.schedule.ScheduleAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<ActivityEntity> activityEntityList;

    public ActivityAdapter(LayoutInflater inflater, List<ActivityEntity> activityEntityList) {
        this.inflater = inflater;
        this.activityEntityList = activityEntityList;
    }

    @NonNull
    @Override
    public ActivityAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityAdapter.ViewHolder holder, int position) {
        ActivityEntity activityEntity = activityEntityList.get(position);
        holder.activity_date.setText(activityEntity.getActivity_date());
        holder.count_patients.setText("Количество пациентов: " + activityEntity.getCount_patients());
    }

    @Override
    public int getItemCount() {
        return activityEntityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView activity_date;
        final TextView count_patients;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            activity_date = (TextView) itemView.findViewById(R.id.activity_date);
            count_patients = (TextView) itemView.findViewById(R.id.count_patients);
        }
    }
}
