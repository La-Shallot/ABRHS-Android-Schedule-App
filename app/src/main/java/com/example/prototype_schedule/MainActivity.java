package com.example.prototype_schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";

    private ArrayList<String> Classes = new ArrayList<>();
    private ArrayList<String> Times = new ArrayList<>();
    private ArrayList<String> Periods = new ArrayList<>();
    private LinearLayout welcome;

    private final int SETUP_ACCESS_CODE = 1111;
    private final String BLUE_ACCESS_CODE = "blue";
    private final String GOLD_ACCESS_CODE = "gold";
    private final String BLUE_LUNCH_ACCESS_CODE = "blue_lunch";
    private final String GOLD_LUNCH_ACCESS_CODE = "gold_lunch";
    private final String SETUP_STATUS = "setup";
    private final String SEPERATER = ",";

    private String[] Day_Blue_Classes = new String[7];
    private String[] Day_Blue_Lunches;
    private String[] Day_Gold_Classes = new String[7];
    private String[] Day_Gold_Lunches;

    private int cur_month, cur_day;
    private Schedule schedule = new Schedule();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        Schedule schedule = new Schedule();

        if (sp.getBoolean(SETUP_STATUS, false)) {
            //Change Later to LOAD data
            retrieveClasses();

            //FOR TESTING PURPOSES ONLY REMOVE FOR USE
            InjecttestingData();

            //OLD SETTING UP STUFF
            if(schedule.getDay(cur_month, cur_day) == -1){
                //VACATION VIEWgrfth88
            }
            else{
                //SCHOOL VIEW
                setContentView(R.layout.activity_main);
                generateData();
                setUpRecycler();
            }

        } else {
            setContentView(R.layout.hello);
            welcome = findViewById(R.id.click_hello);
            welcome.setOnClickListener(new Hello_Listener());
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.reset) {
            Context context = MainActivity.this;
            String textToShow = "Resetting...";
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, SetUp.class);
            ChangesetUpStatus(false);
            startActivityForResult(intent, SETUP_ACCESS_CODE);

            return true;
        }
        else if(itemThatWasClickedId == R.id.refresh){
            Context context = MainActivity.this;
            String textToShow = "Refresh!";
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
            Refresh();
        }
        return super.onOptionsItemSelected(item);
    }

    private class Hello_Listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, SetUp.class);
            startActivityForResult(intent, SETUP_ACCESS_CODE);
        }
    }

    private void Refresh(){
        retrieveClasses();
        InjecttestingData();
        setContentView(R.layout.activity_main);
        generateData();
        setUpRecycler();
    }

    private void InjecttestingData(){
        //Day_Gold_Classes = new String[]{"A","ABB", "B","A", "ABB", "AB", "A"};
        Day_Gold_Lunches = new String[]{"1","1", "1","1", "1", "1", "1"};
        //Day_Blue_Classes = new String[]{"CLASS ONE","CLASS TWO", "CLASS THREE","CLASS FOUR", "CLASS FIVE", "CLASS SIX", "CLASS SEVEN"};;
        Day_Blue_Lunches = new String[]{"1","1", "1","1", "1", "1", "1"};
    }


    private void ChangesetUpStatus(boolean b) {
        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putBoolean(SETUP_STATUS, b);
        spe.commit();
    }

    private void generateData() {
        setDayandMonth();
        //FOR TESTING ONLY
        schedule.setClasses(Day_Blue_Classes, Day_Gold_Classes, Day_Blue_Lunches, Day_Gold_Lunches);

        cur_month = 1;
        cur_day = 8;

        //FOR TESTING ONLY
        Classes = schedule.getDayClasses(cur_month, cur_day);
        Times = schedule.getTimes(cur_month,cur_day);
        Periods = schedule.getPeriods(cur_month,cur_day);

        /*Classes = new ArrayList<>(Arrays.asList("Math", "English", "Free", "APUSH", "APChem", "Lunch", "Art"));
        Times = new ArrayList<>(Arrays.asList("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00"));
        for (int i = 0; i < 7; i++) {
            Periods.add(Integer.toString(i + 1));
        }*/
    }

    private void setDayandMonth(){
        Calendar calendar = Calendar.getInstance();
        cur_day =  calendar.get(Calendar.DAY_OF_MONTH);
        cur_month = calendar.get(Calendar.MONTH) + 1;
    }

    //fix this for customizable
    private void setUpRecycler() {
        RecyclerView recyclerView = findViewById(R.id.recycler_schedule);
        Schedule_Recycler_Adapter sched = new Schedule_Recycler_Adapter(Classes, Times, Periods, getApplicationContext());
        recyclerView.setAdapter(sched);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void permaSaveClasses() {
        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString(BLUE_ACCESS_CODE, concat_Classes(Day_Blue_Classes));
        spe.putString(GOLD_ACCESS_CODE, concat_Classes(Day_Gold_Classes));
        spe.putString(BLUE_LUNCH_ACCESS_CODE, concat_Classes(Day_Blue_Lunches));
        spe.putString(GOLD_LUNCH_ACCESS_CODE, concat_Classes(Day_Gold_Lunches));
        spe.commit();
    }

    private void retrieveClasses() {
        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        Day_Gold_Classes = sp.getString(GOLD_ACCESS_CODE, " ").split(SEPERATER);
        Day_Gold_Lunches = sp.getString(GOLD_LUNCH_ACCESS_CODE, " ").split(SEPERATER);
        Day_Blue_Classes = sp.getString(GOLD_ACCESS_CODE, " ").split(SEPERATER);
        Day_Blue_Lunches = sp.getString(GOLD_ACCESS_CODE, " ").split(SEPERATER);
    }

    private String concat_Classes(String[] Classes) {
        StringBuilder sb = new StringBuilder();
        for (String Class : Classes) {
            sb.append(Class);
            sb.append(SEPERATER);
        }
        return sb.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETUP_ACCESS_CODE) {
            if (resultCode == RESULT_OK) {
                Day_Gold_Classes = data.getStringArrayExtra("Gold_Class");
                Day_Gold_Lunches = data.getStringArrayExtra("Gold_Lunch");
                Day_Blue_Classes = data.getStringArrayExtra("Blue_Class");
                Day_Blue_Lunches = data.getStringArrayExtra("Blue_Lunch");
                ChangesetUpStatus(true);
                permaSaveClasses();
                Refresh();
            }
        }
    }



}
