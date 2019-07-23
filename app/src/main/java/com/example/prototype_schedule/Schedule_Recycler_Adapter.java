package com.example.prototype_schedule;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Schedule_Recycler_Adapter extends RecyclerView.Adapter<Schedule_Recycler_Adapter.RecyclerViewHolder> {
    private static final String TAG = "Schedule_Recycler_Adapt";

    private ArrayList<String> mClasses;
    private ArrayList<String> mTimes;
    private ArrayList<String> mperiods;
    private Context mcontext;

    public Schedule_Recycler_Adapter(ArrayList<String> mClasses, ArrayList<String> mTimes, ArrayList<String> mperiods, Context mcontext) {
        this.mClasses = mClasses;
        this.mTimes = mTimes;
        this.mperiods = mperiods;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item, parent, false);
        RecyclerViewHolder viewholder = new RecyclerViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.classPeriod.setText(mperiods.get(position));
        holder.className.setText(mClasses.get(position));
        holder.classTime.setText(mTimes.get(position));
    }

    @Override
    public int getItemCount() {
        return mClasses.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView className;
        TextView classTime;
        TextView classPeriod;
        RelativeLayout parentLayout;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.class_item_name);
            classTime = itemView.findViewById(R.id.class_item_time);
            classPeriod = itemView.findViewById(R.id.class_item_period);
        }
    }
}
