package com.example.prototype_schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Schedule {
    //DAY NUMs:
    // 0 - G, 1 - F , 2 - E, 3 - D, 4 - C, 5 - B, 6 - A;
    private static final String TAG = "Schedule";
    private int[][] Days;
    private int[][] Alphabet_Days;
    String[] Blue_Classes;
    String[] Gold_Classes;
    String[] B_Lunch;
    String[] G_Lunch;
    int [] Days_of_Month = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    //Bell Schedule
    //Format: [Period 1, Period 2, (Advisory) Period 3, Period 4, Lunch 1, Lunch 2, Lunch 3, Period 5, Period 6]
    String[] Regular = {"8:00", "9:00", "10:00", "11:03","12:52", "1:52"};
    String[] Regular_1 = {"8:00", "9:00", "10:00", "11:03", "11:52","12:52", "1:52"};
    String[] Regular_2 = {"8:00", "9:00", "10:00", "11:03", "11:35", "12:19","12:52", "1:52"};
    String[] Regular_3 = {"8:00", "9:00", "10:00", "11:03", "12:03", "12:52", "1:52"};


    String[] Advisory = {"8:00", "9:00", "10:00", "11:03","12:52", "1:52"};
    String[] Advisory_1 = {"8:00", "9:00", "10:00", "11:03", "11:52","12:52", "1:52"};
    String[] Advisory_2 = {"8:00", "9:00", "10:00", "11:03", "11:35", "12:19","12:52", "1:52"};
    String[] Advisory_3 = {"8:00", "9:00", "10:00", "11:03", "12:03", "12:52", "1:52"};

    public Schedule(){
        Days = new int[12][31];
        Alphabet_Days = new int[12][31];
        int year = 2020;

        Calendar calendar = Calendar.getInstance();
        for(int month = 0; month < 12; month++){

            if(month == 8){
                year = 2019;
            }
            for(int day = 0; day < Days_of_Month[month]; day++){
                if(month == 6 || month == 7){
                    //set summer holidays
                    Days[month][day] = -1;
                }
                else{
                    //set school weeks
                    calendar.set(year, month, day + 1);
                    if(calendar.get(Calendar.DAY_OF_WEEK) % 6 == 1){
                        //WEEKEND
                        Days[month][day] = -1;
                    }
                    if(calendar.get(Calendar.DAY_OF_WEEK) == 3){
                        Days[month][day] = -3;
                    }
                }

            }



        }

        //SPECIAL DAYS

        //Holidays
        Days[8][1] = -1;
        Days[8][29] = -1;

        Days[9][8] = -1;
        Days[9][13] = -1;

        Days[10][4] = -1;
        Days[10][10] = -1;
        Days[10][27] = -1;
        Days[10][28] = -1;

        Days[11][22] = -1;
        Days[11][23] = -1;
        Days[11][24] = -1;
        Days[11][25] = -1;
        Days[11][26] = -1;
        Days[11][29] = -1;
        Days[11][30] = -1;

        Days[0][0] = -1;
        Days[0][19] = -1;

        Days[1][17] = -1;
        Days[1][18] = -1;
        Days[1][19] = -1;
        Days[1][20] = -1;
        Days[1][16] = -1;

        Days[3][9] = -1;
        Days[3][19] = -1;
        Days[3][20] = -1;
        Days[3][21] = -1;
        Days[3][22] = -1;
        Days[3][23] = -1;

        Days[4][24] = -1;

        //Iterate through and assign days
        int count = 13;
        for(int month = 8; month < 12; month++){
            for(int day = 0; day < Days_of_Month[month]; day++){
                if(Days[month][day]!= -1){
                    Alphabet_Days[month][day] = count;
                    count ++;
                    count = count % 14;
                }

            }
        }

        for(int month = 0; month < 8; month++){
            for(int day = 0; day < Days_of_Month[month]; day++){
                if(Days[month][day]!= -1){
                    Alphabet_Days[month][day] = count;
                    count ++;
                    count = count % 14;
                }

            }
        }


        //set early release


    }
    public void setClasses(String[] Blue, String[] Gold, String[]B_Lunch, String[] G_Lunch){
        this.Blue_Classes = Blue;
        this.Gold_Classes = Gold;
        this.B_Lunch = B_Lunch;
        this.G_Lunch = G_Lunch;

    }

    public int getDay(int month, int day){
        if(Days[month - 1][day - 1] != -1){
            return Alphabet_Days[month - 1][day - 1];
        }
        else{
            return -1;
        }
    }

    public ArrayList<String> getDayClasses(int month, int day){
        ArrayList<String> Classes;
        int day_num = this.getDay(month, day);

        if(day_num % 2 == 0){
            //BLUE
            day_num = day_num % 7;

            //conversion
            day_num = dayNumtoarrayIndex(day_num);

            Classes = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(Blue_Classes, day_num, Blue_Classes.length)));
            for(int i = 0; i < day_num; i++){
                Classes.add(Blue_Classes[i]);
            }

            switch (B_Lunch[day_num]){
                case "1":
                    Classes.add(3, "Lunch");
                    break;
                case "2":
                    Classes.add(3, Blue_Classes[3]);
                    Classes.add(4, "Lunch");
                    break;
                case "3":
                    Classes.add(4, "Lunch");

            }

        }
        else{
            //GOLD
            day_num = day_num % 7;
            //conversion
            day_num = dayNumtoarrayIndex(day_num);
            Classes = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(Gold_Classes, day_num, Gold_Classes.length)));
            for(int i = 0; i < day_num; i++){
                Classes.add(Gold_Classes[i]);
            }

            switch (G_Lunch[day_num]){
                case "1":
                    Classes.add(3, "Lunch");
                    break;
                case "2":
                    Classes.add(3, Gold_Classes[3]);
                    Classes.add(4, "Lunch");
                    break;
                case "3":
                    Classes.add(4, "Lunch");

            }
        }
        //Drop Last Class
        Classes.remove(Classes.size() - 1);

        //Add Lunch Periods
        return Classes;
    }

    private int dayNumtoarrayIndex (int dayNum){
        return 6 - (dayNum % 7);
    }

    private String getLunchNum(int month, int day){
        int dayNum = getDay(month, day);
        dayNum = dayNumtoarrayIndex(dayNum);
        boolean blue = false;
        if(dayNum % 2 == 0){
            blue = true;
        }
        if(blue){
            return B_Lunch[dayNum];
        }
        else{
            return G_Lunch[dayNum];
        }
    }

    public void Print(){
        for(int month = 0; month < 12; month++){
            for(int day = 0; day < Days_of_Month[month]; day++){
                System.out.print((month + 1) + " " + (day + 1) + " " + Alphabet_Days[month][day] + "|");

            }
            System.out.println(" ");
        }
    }

    public ArrayList<String> getTimes(int month, int day){
        if(Days[month - 1][day - 1] == 0){

            switch (getLunchNum(month, day)){
                case "1":
                    return new ArrayList<String>(Arrays.asList(Regular_1));
                case "2":
                    return new ArrayList<String>(Arrays.asList(Regular_2));
                case "3":
                    return new ArrayList<String>(Arrays.asList(Regular_3));
            }
            return new ArrayList<String>(Arrays.asList(Regular));
        }
        else{
            //replace with others later
            return new ArrayList<String>(Arrays.asList(Regular));
        }
    }

    public ArrayList<String> getPeriods(int month, int day){
        ArrayList<String> result = new ArrayList<String>();
        for(int i = 0; i < 7; i++){
            result.add(Integer.toString(i + 1));
        }
        switch (getLunchNum(month, day)){
            case "1":
                result.add(3, "L");
                break;
            case "2":
                result.add(4, "4");
                result.add(4, "L");
                break;
            case "3":
                result.add(4,"L");
                break;
        }
        return result;
    }


}
