package com.example.prototype_schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String>Classes = new ArrayList<>();
    private ArrayList<String>Times = new ArrayList<>();
    private ArrayList<String>Periods = new ArrayList<>();
    private LinearLayout welcome;
    private boolean setUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp = false;
        super.onCreate(savedInstanceState);

        if(setUp == true){
            //Change Later to LOAD data
            setContentView(R.layout.activity_main);
            generateData();
            setUpRecycler();
        }
        else{
            setContentView(R.layout.hello);
            welcome = (LinearLayout) findViewById(R.id.click_hello);
            welcome.setOnClickListener(new Hello_Listener());
        }



    }

    private class Hello_Listener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, SetUp.class);
            startActivity(intent);
        }
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
