package com.datwhite.simedlk.ui.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datwhite.simedlk.R;
import com.datwhite.simedlk.entity.PatientSchedule;
import com.datwhite.simedlk.entity.auth.WorkerData;

import java.text.SimpleDateFormat;
import java.util.List;

public class ScheduleAdapter2 extends RecyclerView.Adapter<ScheduleAdapter2.ViewHolder> {
    interface OnScheduleClickListener{
        void onScheduleClick(WorkerData workerData, int position);
    }


    private final LayoutInflater inflater;
    private final List<PatientSchedule> workerDataList;

    public ScheduleAdapter2(LayoutInflater inflater, List<PatientSchedule> workerDataList) {
        this.inflater = inflater;
        this.workerDataList = workerDataList;
    }

    @NonNull
    @Override
    public ScheduleAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_schedule_2, parent, false);
        return new ScheduleAdapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter2.ViewHolder holder, int position) {
        PatientSchedule workerData = workerDataList.get(position);

        holder.schedule_time_start.setText(workerData.getTime_start());
        holder.schedule_name.setText(workerData.getName());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickListener.onScheduleClick(workerData, position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return workerDataList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        //        final ImageView docPhoto;
        final TextView schedule_time_start;
        final TextView schedule_name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            docPhoto = (ImageView) itemView.findViewById(R.id.colleaguesImageView);
            schedule_time_start = (TextView) itemView.findViewById(R.id.schedule_time_start);
            schedule_name = (TextView) itemView.findViewById(R.id.schedule_name);

        }
    }
}
