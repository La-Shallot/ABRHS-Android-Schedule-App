package com.example.prototype_schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gigamole.navigationtabstrip.NavigationTabStrip;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";

    private ArrayList<String> Classes = new ArrayList<>();
    private ArrayList<String> Times = new ArrayList<>();
    private ArrayList<String> Periods = new ArrayList<>();
    private LinearLayout welcome;
    private LinearLayout main_layout;
    private ImageButton last, next;

    private final int SETUP_ACCESS_CODE = 1111;
    private final String BLUE_ACCESS_CODE = "blue";
    private final String GOLD_ACCESS_CODE = "gold";
    private final String BLUE_LUNCH_ACCESS_CODE = "blue_lunch";
    private final String GOLD_LUNCH_ACCESS_CODE = "gold_lunch";
    private final String SETUP_STATUS = "setup";
    private final String SEPERATER = ",";
    private final String SET_UP_FILE_NAME = "setup_file";

    private String[] Day_Blue_Classes = new String[7];
    private String[] Day_Blue_Lunches;
    private String[] Day_Gold_Classes = new String[7];
    private String[] Day_Gold_Lunches;

    private Schedule schedule = new Schedule();
    WeekCalender weekCalender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = this.getSharedPreferences(SET_UP_FILE_NAME, Context.MODE_PRIVATE);
        Schedule schedule = new Schedule();
        //ActionBar actionBar = getActionBar();


        if (sp.getBoolean(SETUP_STATUS, false)) {
            //Re-start setup
            retrieve_and_setClasses();

            weekCalender = new WeekCalender();

            //MULTI_DAY VIEW
            setContentView(R.layout.activity_main);
            main_layout = (LinearLayout) findViewById(R.id.main_Activity);


            setUpNavBar();
            setTitle(weekCalender.getCur_month() + "/" + weekCalender.getCur_day());
            generateData(weekCalender.getCur_month(), weekCalender.getCur_day());

        } else {
            setContentView(R.layout.hello);
            welcome = findViewById(R.id.click_hello);
            welcome.setOnClickListener(new Hello_Listener());
        }
    }

    //MENUS MENUS

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

    private class Move_Listener implements  View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.next_week_button){
                //update to next week
                int[] Date = weekCalender.getMonday();
                weekCalender.setDay(Date[0], Date[1], 7);
                Log.d(TAG, "onClick: " + weekCalender.getCur_day() + weekCalender.getCur_month());
                Refresh();
            }
            if(view.getId() == R.id.last_week_button){
                //update to last week
                int[] Date = weekCalender.getMonday();
                Log.d(TAG, "onClick: xx" + weekCalender.getCur_day() + weekCalender.getCur_month());
                weekCalender.setDay(Date[0], Date[1], -7);
                Log.d(TAG, "onClick: " + weekCalender.getCur_day() + weekCalender.getCur_month());
                Refresh();
            }
        }
    }

    //SCHEDULE FUNCTIONS

    private void Refresh(){
        retrieve_and_setClasses();
        setContentView(R.layout.activity_main);
        setUpNavBar();
        generateData(weekCalender.cur_month, weekCalender.getCur_day());
    }

    private void Restart(){
        //Re-start setup
        retrieve_and_setClasses();

        weekCalender = new WeekCalender();

        //MULTI_DAY VIEW
        setContentView(R.layout.activity_main);
        main_layout = (LinearLayout) findViewById(R.id.main_Activity);


        setUpNavBar();
        setTitle(weekCalender.getCur_month() + "/" + weekCalender.getCur_day());
        generateData(weekCalender.getCur_month(), weekCalender.getCur_day());
    }

    //get "today"
    //generate by month and day
    private void generateData(int month, int day) {

        int[] Date = {month, day};
        Log.d(TAG, "generateData: getting" + Date[0] + " " + Date[1]);
        if(schedule.hasSchool(Date[0], Date[1])){
            //school + recycler view
            setMainDisplay(true);
            Classes = schedule.getDayClasses(Date[0], Date[1]);
            Times = schedule.getTimes(Date[0],Date[1]);
            Periods = schedule.getPeriods(Date[0],Date[1]);

            Log.d(TAG, "generateData: " + Classes.size() + Times.size() + Periods.size());

            for(int i = 0; i < Classes.size(); i++){
                Log.d(TAG, "generateData: " + Classes.get(i) + " " + Times.get(i) + " " + Periods.get(i));
            }

            setUpRecycler(Classes, Times, Periods);

        }
        else{
            //No school + text view
            setMainDisplay(false);

        }

    }

    //fix this for customizable
    private void setUpRecycler(ArrayList<String> Classes, ArrayList<String> Times, ArrayList<String> Periods) {
        RecyclerView recyclerView = findViewById(R.id.recycler_schedule);
        Schedule_Recycler_Adapter sched = new Schedule_Recycler_Adapter(Classes, Times, Periods, getApplicationContext());
        recyclerView.setAdapter(sched);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(TAG, "setUpRecycler: REACH");
       /* int Color = sched.Color_Picker(sched.getItemCount() - 1);*/
        main_layout.setBackgroundColor(0x343942);
        //Log.d(TAG, "setUpRecycler: " + Color);
    }

    //DATA SETTING AREA

    private void permaSaveClasses() {
        SharedPreferences sp = this.getSharedPreferences(SET_UP_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString(BLUE_ACCESS_CODE, concat_Classes(Day_Blue_Classes));
        spe.putString(GOLD_ACCESS_CODE, concat_Classes(Day_Gold_Classes));
        spe.putString(BLUE_LUNCH_ACCESS_CODE, concat_Classes(Day_Blue_Lunches));
        spe.putString(GOLD_LUNCH_ACCESS_CODE, concat_Classes(Day_Gold_Lunches));
        spe.commit();
    }

    private void retrieve_and_setClasses() {
        SharedPreferences sp = this.getSharedPreferences(SET_UP_FILE_NAME, Context.MODE_PRIVATE);
        Day_Gold_Classes = sp.getString(GOLD_ACCESS_CODE, " ").split(SEPERATER);
        Day_Gold_Lunches = sp.getString(GOLD_LUNCH_ACCESS_CODE, " ").split(SEPERATER);
//        Day_Gold_Lunches = new String[]{"1", "1", "1", "1", "1", "1", "1"};
//        Day_Blue_Lunches = new String[]{"1", "1", "1", "1", "1", "1", "1"};
        Day_Blue_Classes = sp.getString(BLUE_ACCESS_CODE, " ").split(SEPERATER);
        Day_Blue_Lunches = sp.getString(BLUE_LUNCH_ACCESS_CODE, " ").split(SEPERATER);

        schedule.setClasses(Day_Blue_Classes, Day_Gold_Classes, Day_Blue_Lunches, Day_Gold_Lunches);
        checkClasses();
    }

    private void checkClasses(){
        for(String str:Day_Gold_Classes){
            Log.d(TAG, "checkClasses: " + str);
        }
        Log.d(TAG, "checkClasses: ");
        for(String str:Day_Blue_Classes){
            Log.d(TAG, "checkClasses: " + str);
        }
    }


    private void ChangesetUpStatus(boolean b) {
        SharedPreferences sp = this.getSharedPreferences(SET_UP_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putBoolean(SETUP_STATUS, b);
        spe.commit();
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
                Restart();
            }
        }
    }

    //NAVIGATION SETUP

    private void setUpNavBar(){
        NavigationTabStrip navbar = (NavigationTabStrip) findViewById(R.id.nts_top);
        navbar.setOnTabStripSelectedIndexListener(new NTS_Listener());

        //set up week buttons
        last = findViewById(R.id.last_week_button);
        next = findViewById(R.id.next_week_button);
        last.setOnClickListener(new Move_Listener());
        next.setOnClickListener(new Move_Listener());


        //month incremented to meet JANUARY starting at 0 for Calendar library
        if(weekCalender.getDayofWeek(weekCalender.getCur_month(), weekCalender.getCur_day()) < 5){
            //weekday, find day and place dot
            navbar.setTabIndex(weekCalender.getDayofWeek(weekCalender.getCur_month(), weekCalender.getCur_day()));
            generateData(weekCalender.getCur_month(), weekCalender.getCur_day());
            setTitle(weekCalender.getCur_month() + "/" + weekCalender.getCur_day());
        }
        else{
            //should not go here
            Log.d(TAG, "setUpNavBar: AIYO");
        }


    }

        class NTS_Listener implements NavigationTabStrip.OnTabStripSelectedIndexListener{
            @Override
            public void onStartTabSelected(String s, int index) {
                //set day to monday
                weekCalender.setToDayofWeek(index);
                Log.d(TAG, "onEndTabSelected: " + index + "Day generated" + weekCalender.getCur_month() + "/" + weekCalender.getCur_day());
                generateData(weekCalender.getCur_month(), weekCalender.getCur_day());
                setTitle(weekCalender.getCur_month() + "/" + weekCalender.getCur_day());

            }
            @Override
            public void onEndTabSelected(String s, int index) {


            }
        }

        private void setMainDisplay(boolean school){
            //school = 1 --> class list view
            //school = 0 --> no class view
            RecyclerView class_list = findViewById(R.id.recycler_schedule);
            TextView no_class_view = findViewById(R.id.no_school_textview);
            if(school){
                //class list
                class_list.setVisibility(class_list.VISIBLE);
                no_class_view.setVisibility(no_class_view.GONE);
            }
            else{
                no_class_view.setVisibility(no_class_view.VISIBLE);
                class_list.setVisibility(class_list.GONE);
            }
        }


}
