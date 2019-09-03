package com.example.prototype_schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gigamole.navigationtabstrip.NavigationTabStrip;

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
            //Re-start setup
            retrieve_and_setClasses();
            setDayandMonth();

            //OLD SETTING UP STUFF(ONE_DAY VIEW)
            /*if(schedule.getDay(cur_month, cur_day) == -1){
                //VACATION VIEW
            }
            else{
                //SCHOOL VIEW
                setContentView(R.layout.activity_main);
                generateData();
                setUpRecycler();
            }*/

            //MULTI_DAY VIEW
            setContentView(R.layout.activity_main);
            generateData();
            setUpNavBar();

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

    //SCHEDULE FUNCTIONS

    private void Refresh(){
        retrieve_and_setClasses();
        setContentView(R.layout.activity_main);
        generateData();
    }

    /*private void InjecttestingData(){
        //Day_Gold_Classes = new String[]{"A","ABB", "B","A", "ABB", "AB", "A"};
        Day_Gold_Lunches = new String[]{"1","1", "1","1", "1", "1", "1"};
        //Day_Blue_Classes = new String[]{"CLASS ONE","CLASS TWO", "CLASS THREE","CLASS FOUR", "CLASS FIVE", "CLASS SIX", "CLASS SEVEN"};;
        Day_Blue_Lunches = new String[]{"1","1", "1","1", "1", "1", "1"};
    }*/

    //get today
    private void generateData() {
        Classes = schedule.getDayClasses(cur_month, cur_day);
        Times = schedule.getTimes(cur_month,cur_day);
        Periods = schedule.getPeriods(cur_month,cur_day);

        Log.d(TAG, "generateData: " + schedule.getLunchNum(cur_month,cur_day));

        Log.d(TAG, "generateData: " + Classes.size() + Times.size() + Periods.size());

        for(int i = 0; i < Classes.size(); i++){
            Log.d(TAG, "generateData: " + Classes.get(i) + " " + Times.get(i) + " " + Periods.get(i));
        }

        setUpRecycler(Classes, Times, Periods);
    }

    private void generateData(int day, int month, int dayofWeek) {
        int[] Date = schedule.getDateofWeekday(month, day, dayofWeek);
        Log.d(TAG, "generateData: getting" + Date[0] + " " + Date[1]);

        Classes = schedule.getDayClasses(Date[0], Date[1]);
        Times = schedule.getTimes(Date[0],Date[1]);
        Periods = schedule.getPeriods(Date[0],Date[1]);

        Log.d(TAG, "generateData: " + Classes.size() + Times.size() + Periods.size());

        for(int i = 0; i < Classes.size(); i++){
            Log.d(TAG, "generateData: " + Classes.get(i) + " " + Times.get(i) + " " + Periods.get(i));
        }

        setUpRecycler(Classes, Times, Periods);
    }

    //fix this for customizable
    private void setUpRecycler(ArrayList<String> Classes, ArrayList<String> Times, ArrayList<String> Periods) {
        RecyclerView recyclerView = findViewById(R.id.recycler_schedule);
        Schedule_Recycler_Adapter sched = new Schedule_Recycler_Adapter(Classes, Times, Periods, getApplicationContext());
        recyclerView.setAdapter(sched);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void setDayandMonth(){
        Calendar calendar = Calendar.getInstance();
        cur_day =  calendar.get(Calendar.DAY_OF_MONTH);
        cur_month = calendar.get(Calendar.MONTH) + 1;

        cur_month = 9;
        cur_day = 3;
    }



    //DATA SETTING AREA

    private void permaSaveClasses() {
        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString(BLUE_ACCESS_CODE, concat_Classes(Day_Blue_Classes));
        spe.putString(GOLD_ACCESS_CODE, concat_Classes(Day_Gold_Classes));
        spe.putString(BLUE_LUNCH_ACCESS_CODE, concat_Classes(Day_Blue_Lunches));
        spe.putString(GOLD_LUNCH_ACCESS_CODE, concat_Classes(Day_Gold_Lunches));
        spe.commit();
    }

    private void retrieve_and_setClasses() {
        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        Day_Gold_Classes = sp.getString(GOLD_ACCESS_CODE, " ").split(SEPERATER);
        Day_Gold_Lunches = sp.getString(GOLD_LUNCH_ACCESS_CODE, " ").split(SEPERATER);
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
        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
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
                Refresh();
            }
        }
    }

    //NAVIGATION SETUP

    private void setUpNavBar(){
        NavigationTabStrip navbar = (NavigationTabStrip) findViewById(R.id.nts_top);
        navbar.setOnTabStripSelectedIndexListener(new NTS_Listener());
        if(schedule.getDayofWeek(cur_month, cur_day) < 5){
            //weekday, find day and place dot
            navbar.setTabIndex(schedule.getDayofWeek(cur_month, cur_day));
        }
        else{
            navbar.setTabIndex(0);
        }
    }

        class NTS_Listener implements NavigationTabStrip.OnTabStripSelectedIndexListener{
            @Override
            public void onStartTabSelected(String s, int index) {
                generateData(cur_day, cur_month, index);
                Log.d(TAG, "onEndTabSelected: " + index);
            }
            @Override
            public void onEndTabSelected(String s, int index) {


            }
        }

}
