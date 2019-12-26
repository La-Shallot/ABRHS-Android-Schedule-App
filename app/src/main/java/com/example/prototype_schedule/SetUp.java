package com.example.prototype_schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SetUp extends AppCompatActivity {

    private static final String TAG = "Setup Activity";

    private LinearLayout classes_button;
    private LinearLayout lunch_button;
    private TextView classes_next;

    //Day Blue
    private String[] Day_Blue_Classes = new String[7];
    private String[] Day_Blue_Lunches = new  String[7];
    private String[] Day_Gold_Classes = new String[7];
    private String[] Day_Gold_Lunches = new  String[7];

    private final String BLUE_ACCESS_CODE = "blue";
    private final String GOLD_ACCESS_CODE = "gold";
    private final String BLUE_LUNCH_ACCESS_CODE = "blue_lunch";
    private final String GOLD_LUNCH_ACCESS_CODE = "gold_lunch";
    private final String SETUP_STATUS = "setup";
    private final String SEPERATER = ",";
    private final String SET_UP_FILE_NAME = "setup_file";

    boolean setup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu);
        SharedPreferences sp = this.getSharedPreferences(SET_UP_FILE_NAME, Context.MODE_PRIVATE);

        //get setup status
        if(sp.getBoolean(SETUP_STATUS, false)){
            Log.d(TAG, "onCreate: Setup, initiating classes");
            setup = true;
            retrieveClasses();
        }
        else{
            Log.d(TAG, "onCreate: Not Setup, initiating classes");
            setup = false;
        }

        classes_button = findViewById(R.id.Classes_Settings);
        classes_button.setOnClickListener(new ButtonListener());

        lunch_button = findViewById(R.id.Lunch_Settings);
        lunch_button.setOnClickListener(new ButtonListener());


    }

    //MENUS MENUS

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.setting, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        switch (itemThatWasClickedId){
            case R.id.finish_settings_button:
                Intent intent = new Intent(SetUp.this, MainActivity.class);
                intent.putExtra("Gold_Class", Day_Gold_Classes);
                intent.putExtra("Blue_Class", Day_Blue_Classes);
                intent.putExtra("Gold_Lunch", Day_Gold_Lunches);
                intent.putExtra("Blue_Lunch", Day_Blue_Lunches);
                setResult(RESULT_OK, intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //button after blue day set-up
    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.Classes_Settings:
                    //go to classes page
                    setContentView(R.layout.classes_setup);
                    classes_next = findViewById(R.id.class_next_button);
                    classes_next.setOnClickListener(new ButtonListener());
                    if(Day_Blue_Classes.length > 5) {
                        display_Day_Blue();
                        display_Day_Gold();
                    }

                    break;

                case R.id.Lunch_Settings:
                    setContentView(R.layout.lunches_setup);
                    classes_next = findViewById(R.id.lunch_next_button);
                    classes_next.setOnClickListener(new ButtonListener());
                    if(Day_Blue_Lunches.length > 5){
                        display_Day_Blue_Lunch();
                        display_Day_Gold_Lunch();
                    }
                    break;

                case R.id.class_next_button:
                    save_Day_Blue();
                    save_Day_Gold();
                    setup = true;
                    setContentView(R.layout.settings_menu);
                    setup_main_setting_menu();
                    break;

                case R.id.lunch_next_button:
                    save_Day_Blue_Lunch();
                    save_Day_Gold_Lunch();
                    setup = true;
                    setContentView(R.layout.settings_menu);
                    setup_main_setting_menu();
                    break;

                case R.id.gold_lunch_next:
                    //save data
                    save_Day_Gold_Lunch();
                    //switch to schedule activity!
            }


        }


        private void save_Day_Blue(){
            //Day A
            EditText A = (EditText) findViewById(R.id.B_Period1);
            Day_Blue_Classes[0] = A.getText().toString();

            //Day B
            EditText B = (EditText) findViewById(R.id.B_Period2);
            Day_Blue_Classes[1] = B.getText().toString();

            //Day C
            EditText C = (EditText) findViewById(R.id.B_Period3);
            Day_Blue_Classes[2] = C.getText().toString();

            //Day D
            EditText D = (EditText) findViewById(R.id.B_Period4);
            Day_Blue_Classes[3] = D.getText().toString();

            //Day E
            EditText E = (EditText) findViewById(R.id.B_Period5);
            Day_Blue_Classes[4] = E.getText().toString();

            //Day F
            EditText F = (EditText) findViewById(R.id.B_Period6);
            Day_Blue_Classes[5] = F.getText().toString();

            //Day G
            EditText G = (EditText) findViewById(R.id.B_Period7);
            Day_Blue_Classes[6] = G.getText().toString();

        }

        private void save_Day_Blue_Lunch(){
            String[] temp = new String[7];
            //Day A
            EditText A = (EditText) findViewById(R.id.BL_Period1);
            temp[0] = A.getText().toString();

            //Day B
            EditText B = (EditText) findViewById(R.id.BL_Period2);
            temp[1] = B.getText().toString();

            //Day C
            EditText C = (EditText) findViewById(R.id.BL_Period3);
            temp[2] = C.getText().toString();

            //Day D
            EditText D = (EditText) findViewById(R.id.BL_Period4);
            temp[3] = D.getText().toString();

            //Day E
            EditText E = (EditText) findViewById(R.id.BL_Period5);
            temp[4] = E.getText().toString();

            //Day F
            EditText F = (EditText) findViewById(R.id.BL_Period6);
            temp[5] = F.getText().toString();

            //Day G
            EditText G = (EditText) findViewById(R.id.BL_Period7);
            temp[6] = G.getText().toString();

            Day_Blue_Lunches = temp;
        }

        //plz check that day blue lunch updates
        private int[] String_to_Int(String[] strs){
            int[] result = new int[strs.length];
            for(int i = 0; i < strs.length; i++){
                result[i] = Integer.parseInt(strs[i]);
            }
            return result;
        }

        private void save_Day_Gold(){
            //Day A
            EditText A = (EditText) findViewById(R.id.G_Period1);
            Day_Gold_Classes[0] = A.getText().toString();

            //Day B
            A = (EditText) findViewById(R.id.G_Period2);
            Day_Gold_Classes[1] = A.getText().toString();

            //Day C
            A = (EditText) findViewById(R.id.G_Period3);
            Day_Gold_Classes[2] = A.getText().toString();

            //Day D
            A = (EditText) findViewById(R.id.G_Period4);
            Day_Gold_Classes[3] = A.getText().toString();

            //Day E
            A = (EditText) findViewById(R.id.G_Period5);
            Day_Gold_Classes[4] = A.getText().toString();

            //Day F
            A = (EditText) findViewById(R.id.G_Period6);
            Day_Gold_Classes[5] = A.getText().toString();

            //Day G
            A = (EditText) findViewById(R.id.G_Period7);
            Day_Gold_Classes[6] = A.getText().toString();

        }

        private void save_Day_Gold_Lunch(){

            String[] temp = new String[7];
            //Day A
            EditText A = (EditText) findViewById(R.id.GL_Period1);
            temp[0] = A.getText().toString();

            //Day B
            A = (EditText) findViewById(R.id.GL_Period2);
            temp[1] = A.getText().toString();

            //Day C
            A = (EditText) findViewById(R.id.GL_Period3);
            temp[2] = A.getText().toString();

            //Day D
            A = (EditText) findViewById(R.id.GL_Period4);
            temp[3] = A.getText().toString();

            //Day E
            A = (EditText) findViewById(R.id.GL_Period5);
            temp[4] = A.getText().toString();

            //Day F
            A = (EditText) findViewById(R.id.GL_Period6);
            temp[5] = A.getText().toString();

            //Day G
            A = (EditText) findViewById(R.id.GL_Period7);
            temp[6] = A.getText().toString();

            Day_Gold_Lunches = temp;
        }

        private void display_Day_Blue(){
            //Day Blue
            //Day A
            EditText A = (EditText) findViewById(R.id.B_Period1);
            A.setText(Day_Blue_Classes[0], TextView.BufferType.EDITABLE);

            //Day B
            EditText B = (EditText) findViewById(R.id.B_Period2);
            B.setText(Day_Blue_Classes[1], TextView.BufferType.EDITABLE);

            //Day C
            EditText C = (EditText) findViewById(R.id.B_Period3);
            C.setText(Day_Blue_Classes[2], TextView.BufferType.EDITABLE);

            //Day D
            EditText D = (EditText) findViewById(R.id.B_Period4);
            D.setText(Day_Blue_Classes[3], TextView.BufferType.EDITABLE);

            //Day E
            EditText E = (EditText) findViewById(R.id.B_Period5);
            E.setText(Day_Blue_Classes[4], TextView.BufferType.EDITABLE);

            //Day F
            EditText F = (EditText) findViewById(R.id.B_Period6);
            F.setText(Day_Blue_Classes[5], TextView.BufferType.EDITABLE);

            //Day G
            EditText G = (EditText) findViewById(R.id.B_Period7);
            G.setText(Day_Blue_Classes[6], TextView.BufferType.EDITABLE);


        }

        private void display_Day_Gold(){
            EditText A = (EditText) findViewById(R.id.G_Period1);
            A.setText(Day_Gold_Classes[0], TextView.BufferType.EDITABLE);

            //Day B
            A = (EditText) findViewById(R.id.G_Period2);
            A.setText(Day_Gold_Classes[1], TextView.BufferType.EDITABLE);

            //Day C
            A = (EditText) findViewById(R.id.G_Period3);
            A.setText(Day_Gold_Classes[2], TextView.BufferType.EDITABLE);

            //Day D
            A = (EditText) findViewById(R.id.G_Period4);
            A.setText(Day_Gold_Classes[3], TextView.BufferType.EDITABLE);
            //Day E
            A = (EditText) findViewById(R.id.G_Period5);
            A.setText(Day_Gold_Classes[4], TextView.BufferType.EDITABLE);

            //Day F
            A = (EditText) findViewById(R.id.G_Period6);
            A.setText(Day_Gold_Classes[5], TextView.BufferType.EDITABLE);

            //Day G
            A = (EditText) findViewById(R.id.G_Period7);
            A.setText(Day_Gold_Classes[6], TextView.BufferType.EDITABLE);
        }

        private void display_Day_Gold_Lunch(){
            //Day A
            EditText A = (EditText) findViewById(R.id.GL_Period1);
            A.setText(Day_Gold_Lunches[0], TextView.BufferType.EDITABLE);

            //Day B
            A = (EditText) findViewById(R.id.GL_Period2);
            A.setText(Day_Gold_Lunches[1], TextView.BufferType.EDITABLE);

            //Day C
            A = (EditText) findViewById(R.id.GL_Period3);
            A.setText(Day_Gold_Lunches[2], TextView.BufferType.EDITABLE);

            //Day D
            A = (EditText) findViewById(R.id.GL_Period4);
            A.setText(Day_Gold_Lunches[3], TextView.BufferType.EDITABLE);

            //Day E
            A = (EditText) findViewById(R.id.GL_Period5);
            A.setText(Day_Gold_Lunches[4], TextView.BufferType.EDITABLE);

            //Day F
            A = (EditText) findViewById(R.id.GL_Period6);
            A.setText(Day_Gold_Lunches[5], TextView.BufferType.EDITABLE);

            //Day G
            A = (EditText) findViewById(R.id.GL_Period7);
            A.setText(Day_Gold_Lunches[6], TextView.BufferType.EDITABLE);
        }

        private void display_Day_Blue_Lunch(){
            String[] temp = new String[7];
            //Day A
            EditText A = (EditText) findViewById(R.id.BL_Period1);
           A.setText(Day_Blue_Lunches[0], TextView.BufferType.EDITABLE);

            //Day B
            EditText B = (EditText) findViewById(R.id.BL_Period2);
            B.setText(Day_Blue_Lunches[1], TextView.BufferType.EDITABLE);

            //Day C
            EditText C = (EditText) findViewById(R.id.BL_Period3);
            C.setText(Day_Blue_Lunches[2], TextView.BufferType.EDITABLE);

            //Day D
            EditText D = (EditText) findViewById(R.id.BL_Period4);
            D.setText(Day_Blue_Lunches[3], TextView.BufferType.EDITABLE);

            //Day E
            EditText E = (EditText) findViewById(R.id.BL_Period5);
            E.setText(Day_Blue_Lunches[4], TextView.BufferType.EDITABLE);

            //Day F
            EditText F = (EditText) findViewById(R.id.BL_Period6);
            F.setText(Day_Blue_Lunches[5], TextView.BufferType.EDITABLE);

            //Day G
            EditText G = (EditText) findViewById(R.id.BL_Period7);
            G.setText(Day_Blue_Lunches[6], TextView.BufferType.EDITABLE);
        }


    }

    private void retrieveClasses(){
        SharedPreferences sp = this.getSharedPreferences(SET_UP_FILE_NAME, Context.MODE_PRIVATE);
        Day_Gold_Classes = sp.getString(GOLD_ACCESS_CODE, " ").split(SEPERATER);
        Day_Gold_Lunches = sp.getString(GOLD_LUNCH_ACCESS_CODE, " ").split(SEPERATER);
        Day_Blue_Classes = sp.getString(BLUE_ACCESS_CODE, " ").split(SEPERATER);
        Day_Blue_Lunches = sp.getString(BLUE_LUNCH_ACCESS_CODE, " ").split(SEPERATER);
    }

    private void setup_main_setting_menu(){
        classes_button = findViewById(R.id.Classes_Settings);
        classes_button.setOnClickListener(new ButtonListener());

        lunch_button = findViewById(R.id.Lunch_Settings);
        lunch_button.setOnClickListener(new ButtonListener());
    }


}
