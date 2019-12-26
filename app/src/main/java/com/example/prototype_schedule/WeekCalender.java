package com.example.prototype_schedule;

import android.util.Log;

import java.util.Calendar;

public class WeekCalender {

    int cur_day, cur_month;
    int[] Days_of_Month = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final String TAG = "WeekCalender";

    public WeekCalender(int month, int day) {
        setDay(month, day);
    }

    public WeekCalender() {
        resetToToday();
    }

    public int[] getDateofWeekday(int month, int day, int DayofWeek) {
        //month and day are their normal values
        //days of the week go form 0 = monday to 6 - Sunday
        int curWeekday = getDayofWeek(month, day);
        int difference = DayofWeek - curWeekday;
        return fastForward(month, day, difference);
    }

    public int[] getNextSchoolDay(int month, int day) {
        if (getDayofWeek(month, day) >= 5) {
            int[] Date = getDateofWeekday(month, day, 0);
            Date = fastForward(Date[0], Date[1], 7);
            return Date;
        } else {
            return new int[]{month, day};
        }

    }

    public int[] fastForward(int month, int day, int days_forward) {
        //method that takes a date and returns the day "days forward" later
        //month and day are normal notation
        if (days_forward > 0) {
            for (int i = 0; i < days_forward; i++) {
                // month - 1 becaues arrays start at 0
                if (day == Days_of_Month[month - 1]) {
                    //overflow into next month
                    if (month == 12) {
                        //overflow into next year
                        month = 1;
                        day = 1;
                    } else {
                        month++;
                        day = 1;
                    }
                } else {
                    day++;
                }
            }
        }
        if (days_forward < 0) {
            for (int i = 0; i < Math.abs(days_forward); i++) {
                if (day == 1) {
                    //overflow into last month
                    if (month == 1) {
                        //overflow into last year
                        month = 12;
                        day = 31;
                    } else {
                        month--;
                        day = Days_of_Month[month - 1];
                    }
                } else {
                    day--;
                }
            }

        }
        return new int[]{month, day};
    }

    public int getDayofWeek(int month, int day) {
        //MONDAY START AT 0
        Calendar calendar = Calendar.getInstance();
        if (month < 8) {
            calendar.set(2020, month - 1, day);
        } else {
            calendar.set(2019, month - 1, day);
        }

        return (calendar.get(Calendar.DAY_OF_WEEK) - 2) % 7;
    }

    public int[] getDay() {
        return new int[]{cur_month, cur_day};
    }

    public void setDay(int month, int day) {
        cur_month = month;
        cur_day = day;
    }

    public void setDay(int month, int day, int forward) {
        int[] Date = fastForward(month, day, forward);
        cur_month = Date[0];
        cur_day = Date[1];
    }

    public void resetToToday() {
        int[] Today = getToday();
        Today = getNextSchoolDay(Today[0], Today[1]);
        setDay(Today[0], Today[1]);

    }

    public int[] getToday() {
        Calendar calendar = Calendar.getInstance();
        int cur_day = calendar.get(Calendar.DAY_OF_MONTH);
        int cur_month = calendar.get(Calendar.MONTH) + 1;
        return new int[]{cur_month, cur_day};
    }

    public int[] getMonday() {
        int[] Date = getDateofWeekday(cur_month, cur_day, 0);
        //Date = schedule.fastForward(Date[0], Date[1], 7);
        return Date;
    }

    public void setToDayofWeek(int dayOfWeek) {
        int[] Date = getDateofWeekday(cur_month, cur_day, 0);
        Log.d(TAG, "setToDayofWeek: " + Date[0] + Date[1]);
        Date = fastForward(Date[0], Date[1], dayOfWeek);
        cur_month = Date[0];
        cur_day = Date[1];
        Log.d(TAG, "setToDayofWeek: " + cur_day + cur_month);
    }

    public int getCur_day() {
        return cur_day;
    }

    public int getCur_month() {
        return cur_month;
    }


}
