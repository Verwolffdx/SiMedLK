package com.datwhite.simedlk.ui.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datwhite.simedlk.R;
import com.datwhite.simedlk.entity.auth.WorkerData;

import java.text.SimpleDateFormat;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    interface OnScheduleClickListener{
        void onScheduleClick(WorkerData workerData, int position);
    }

    private final OnScheduleClickListener onClickListener;

    private final LayoutInflater inflater;
    private final List<WorkerData> workerDataList;

    public ScheduleAdapter(OnScheduleClickListener onClickListener, LayoutInflater inflater, List<WorkerData> workerDataList) {
        this.onClickListener = onClickListener;
        this.inflater = inflater;
        this.workerDataList = workerDataList;
    }

    @NonNull
    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_schedule, parent, false);
        return new ScheduleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.ViewHolder holder, int position) {
        WorkerData workerData = workerDataList.get(position);
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String s = formatter.parse(workerData.getREC_TIME());
        String startTime = workerData.getREC_TIME().split("T|:")[1] + ":" + workerData.getREC_TIME().split("T|:")[2];
        holder.schedule_time_start.setText(startTime);
        holder.schedule_doc.setText(workerData.getDOCT_NAME());
        holder.schedule_duration.setText(workerData.getREC_DURATION() + " мин");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onScheduleClick(workerData, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workerDataList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        //        final ImageView docPhoto;
        final TextView schedule_time_start;
        final TextView schedule_doc;
        final TextView schedule_duration;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            docPhoto = (ImageView) itemView.findViewById(R.id.colleaguesImageView);
            schedule_time_start = (TextView) itemView.findViewById(R.id.schedule_time_start);
            schedule_doc = (TextView) itemView.findViewById(R.id.schedule_doc);
            schedule_duration = (TextView) itemView.findViewById(R.id.schedule_duration);
        }
    }
}
