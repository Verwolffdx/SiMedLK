package com.datwhite.simedlk.ui.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datwhite.simedlk.R;
import com.datwhite.simedlk.entity.Doctor;
import com.datwhite.simedlk.entity.schedule.Cell;
import com.datwhite.simedlk.entity.schedule.Schedule;
import com.datwhite.simedlk.ui.colleagues.ColleaguesAdapter;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    interface OnScheduleClickListener{
        void onScheduleClick(Cell cell, int position);
    }

    private final OnScheduleClickListener onClickListener;


    private final LayoutInflater inflater;
    private final List<Cell> scheduleList;

    public ScheduleAdapter(OnScheduleClickListener onClickListener, LayoutInflater inflater, List<Cell> scheduleList) {
        this.onClickListener = onClickListener;
        this.inflater = inflater;
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_schedule, parent, false);
        return new ScheduleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.ViewHolder holder, int position) {
        Cell cell = scheduleList.get(position);
        holder.schedule_time.setText(cell.getTime_start());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onScheduleClick(cell, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        //        final ImageView docPhoto;
        final TextView schedule_time;
        final TextView schedule_worker;
        final TextView schedule_duration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            docPhoto = (ImageView) itemView.findViewById(R.id.colleaguesImageView);
            schedule_time = (TextView) itemView.findViewById(R.id.schedule_time);
            schedule_worker = (TextView) itemView.findViewById(R.id.schedule_worker);
            schedule_duration = (TextView) itemView.findViewById(R.id.schedule_duration);
        }
    }
}
