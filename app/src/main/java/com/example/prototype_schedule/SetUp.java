package com.example.prototype_schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SetUp extends AppCompatActivity {

    private static final String TAG = "Setup Activity";

    private TextView next_button;

    //Day Blue
    private String[] Day_Blue_Classes = new String[7];
    private String[] Day_Blue_Lunches;
    private String[] Day_Gold_Classes = new String[7];
    private String[] Day_Gold_Lunches;



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
            switch (view.getId()){
                case R.id.blue_next:
                    //save data
                    save_Day_Blue();
                    //switch to day blue lunch
                    setContentView(R.layout.lunch_blue);
                    next_button = findViewById(R.id.blue_lunch_next);
                    next_button.setOnClickListener(new NextListener());
                    break;

                case R.id.blue_lunch_next:
                    Log.d(TAG, "onClick: enter Click listener afyer blue lunch");
                    //save data
                    save_Day_Blue_Lunch();
                    //switch to day gold view
                    setContentView(R.layout.setup_gold);
                    next_button = findViewById(R.id.gold_next);
                    next_button.setOnClickListener(new NextListener());
                    break;

                case R.id.gold_next:
                    //save data
                    save_Day_Gold();
                    //switch to day gold lunch view
                    setContentView(R.layout.lunch_gold);
                    next_button = findViewById(R.id.gold_lunch_next);
                    next_button.setOnClickListener(new NextListener());
                    break;

                case R.id.gold_lunch_next:
                    //save data
                    save_Day_Gold_Lunch();
                    //switch to schedule activity!
                    Intent intent = new Intent(SetUp.this, MainActivity.class);
                    intent.putExtra("Gold_Class", Day_Gold_Classes);
                    intent.putExtra("Blue_Class", Day_Blue_Classes);
                    intent.putExtra("Gold_Lunch", Day_Gold_Lunches);
                    intent.putExtra("Blue_Lunch", Day_Blue_Lunches);
                    setResult(RESULT_OK, intent);
                    finish();
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
    }

}
