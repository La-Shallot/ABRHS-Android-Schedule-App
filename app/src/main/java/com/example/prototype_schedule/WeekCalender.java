package com.example.prototype_schedule;

import android.util.Log;

import com.example.prototype_schedule.Schedule;

import java.util.Calendar;

public class WeekCalender {

    int cur_day, cur_month;
    Schedule schedule;
    private static final String TAG = "WeekCalender";

    public WeekCalender(int month, int day){
        cur_day = day;
        cur_month = month;
        schedule = new Schedule();
    }

    public WeekCalender(){
        schedule = new Schedule();
        resetToToday();

    }



    public int[] getNextSchoolDay(int month, int day){
        if(schedule.getDayofWeek(month, day) >= 5){
            int[] Date = schedule.getDateofWeekday(month,day,0);
            Date = schedule.fastForward(Date[0], Date[1], 7);
            return Date;
        }
        else{
            return new int[] {month, day};
        }

    }

    public int[] getNextSchoolDay(){
        return getNextSchoolDay(cur_month, cur_day);
    }

    public int[] getDay(){
        return new int[] {cur_month, cur_day};
    }

    public void setDay(int month, int day){
        cur_month = month;
        cur_day = day;
    }

    public void setDay(int month, int day, int forward){
        int[] Date = schedule.fastForward(month, day, forward);
        cur_month = Date[0];
        cur_day = Date[1];
    }

            public void resetToToday(){
        int[] Today = getToday();
        Today = getNextSchoolDay(Today[0], Today[1]);
        setDay(Today[0], Today[1]);

    }

    public int[] getToday(){
        Calendar calendar = Calendar.getInstance();
        int cur_day =  calendar.get(Calendar.DAY_OF_MONTH);
        int cur_month = calendar.get(Calendar.MONTH) + 1;
        return new int[] {cur_month, cur_day};
    }

    public int[] getMonday(){
        int[] Date = schedule.getDateofWeekday(cur_month,cur_day,0);
        //Date = schedule.fastForward(Date[0], Date[1], 7);
        return Date;
    }

    public void setMonday(){
        int[] Date = getMonday();
        cur_month = Date[0];
        cur_day = Date[1];
    }

    public void setToDayofWeek(int dayOfWeek){
        int[] Date = schedule.getDateofWeekday(cur_month,cur_day,0);
        Log.d(TAG, "setToDayofWeek: " + Date[0] + Date[1]);
        Date = schedule.fastForward(Date[0], Date[1], dayOfWeek);
        cur_month = Date[0];
        cur_day = Date[1];
        Log.d(TAG, "setToDayofWeek: " + cur_day + cur_month);
    }

    public int getCur_day(){
        return cur_day;
    }

    public int getCur_month() {
        return cur_month;
    }

    private void setDaytoMonday(){
        //from today
        Calendar calendar = Calendar.getInstance();
        setDaytoMonday(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

    }

    private void setDaytoMonday(int month, int day){
        //from the date
        //get current monday
        int[] Date = schedule.getDateofWeekday(month,day,0);
        if(schedule.getDayofWeek(month, day) >= 5){
            //weekend, get next MOnday
            Date = schedule.fastForward(Date[0], Date[1], 7);
        }
        cur_month = Date[0];
        cur_day = Date[1];
    }


}
