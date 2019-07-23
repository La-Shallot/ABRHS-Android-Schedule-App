package com.example.prototype_schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String>Classes = new ArrayList<>();
    private ArrayList<String>Times = new ArrayList<>();
    private ArrayList<String>Periods = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateData();
        setUpRecycler();

    }

    private void generateData(){
        Classes = new ArrayList<>(Arrays.asList("Math", "English", "Free", "APUSH", "APChem", "Lunch", "Art", "French"));
        Times = new ArrayList<>(Arrays.asList("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00"));
        for(int i = 0; i < 8; i++){
            Periods.add(Integer.toString(i + 1));
        }
    }

    private void setUpRecycler(){
        RecyclerView recyclerView = findViewById(R.id.recycler_schedule);
        Schedule_Recycler_Adapter sched = new Schedule_Recycler_Adapter(Classes, Times, Periods, getApplicationContext());
        recyclerView.setAdapter(sched);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}