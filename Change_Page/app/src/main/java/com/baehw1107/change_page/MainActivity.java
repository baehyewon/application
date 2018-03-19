package com.baehw1107.change_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView MyTxt;
    Button MyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyTxt = (TextView)findViewById(R.id.MyText);
        MyBtn = (Button)findViewById(R.id.MyBtn);
        MyTxt.setText("화면1: Main화면");

        MyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent MyIntent = new Intent(MainActivity.this, NewPage.class);
                //MyIntent.setFlags();
                startActivity(MyIntent);
            }
        });
    }
}
