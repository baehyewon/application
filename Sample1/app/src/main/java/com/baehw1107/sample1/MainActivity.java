package com.baehw1107.sample1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView MyTxt;
    Button myButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_out);

        MyTxt = (TextView) findViewById(R.id.MyText);
        myButton = (Button) findViewById(R.id.MyBtn);
        MyTxt.setText("화면1 : Main화면" );

        myButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NewPage.class);

                //i.setFlags();
                i.putExtra("NAME", "배혜원");
                i.putExtra("COLOR", "RED");
                startActivity(i);
            }
        });




    }





}
