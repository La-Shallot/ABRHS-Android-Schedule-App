package com.example.prototype_schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SetUp extends AppCompatActivity {

    private TextView next_button;

    //Day Blue
    private String[] Day_Blue_Classes = new String[7];
    private int[] Day_Blue_Lunches = new int[7];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_blue);
        next_button = findViewById(R.id.blue_next);
        next_button.setOnClickListener(new NextListener());
    }

    //button after blue day set-up
    private class NextListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(view == findViewById(R.id.blue_next)){
                //save data
                save_Day_Blue();
                //switch to day blue lunch
                setContentView(R.layout.lunch_blue);
            }
            else if(view == findViewById(R.id.blue_lunch_next)){
                //save data

                //switch to day gold view
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

            Day_Blue_Lunches = String_to_Int(temp);
        }
        private int[] String_to_Int(String[] strs){
            int[] result = new int[strs.length];
            for(int i = 0; i < strs.length; i++){
                result[i] = Integer.parseInt(strs[i]);
            }
            return result;
        }
    }

}
