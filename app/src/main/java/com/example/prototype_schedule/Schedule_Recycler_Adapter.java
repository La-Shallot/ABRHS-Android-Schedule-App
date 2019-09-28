package com.example.prototype_schedule;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Schedule_Recycler_Adapter extends RecyclerView.Adapter<Schedule_Recycler_Adapter.RecyclerViewHolder> {
    private static final String TAG = "Schedule_Recycler_Adapt";

    private ArrayList<String> mClasses;
    private ArrayList<String> mTimes;
    private ArrayList<String> mperiods;
    private Context mcontext;
    private int viewHolderCount;
    private int lastColor;

    public Schedule_Recycler_Adapter(ArrayList<String> mClasses, ArrayList<String> mTimes, ArrayList<String> mperiods, Context mcontext) {
        this.mClasses = mClasses;
        this.mTimes = mTimes;
        this.mperiods = mperiods;
        this.mcontext = mcontext;
        viewHolderCount = 0;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item, parent, false);
        RecyclerViewHolder viewholder = new RecyclerViewHolder(view);
        TextView tv = viewholder.itemView.findViewById(R.id.class_item_period);
        tv.setTextColor(Color_Picker(viewHolderCount));
        tv = viewholder.itemView.findViewById(R.id.class_item_time);
        tv.setTextColor(Color_Picker(viewHolderCount));
        tv = viewholder.itemView.findViewById(R.id.class_item_name);
        tv.setTextColor(Color_Picker(viewHolderCount));
        viewHolderCount ++;
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

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

    public int Color_Picker(int position){
        position = position % 5;
        switch(position){
            case 0:
                lastColor = ContextCompat.getColor(mcontext, R.color.pastel_1);
                break;


            case 1:
                lastColor = ContextCompat.getColor(mcontext, R.color.pastel_2);
                break;

            case 2:
                lastColor = ContextCompat.getColor(mcontext, R.color.pastel_3);
                break;

            case 3:
                lastColor = ContextCompat.getColor(mcontext, R.color.pastel_4);
                break;

            case 4:
                lastColor = ContextCompat.getColor(mcontext, R.color.pastel_5);
                break;

        }
        return lastColor;
    }
}
