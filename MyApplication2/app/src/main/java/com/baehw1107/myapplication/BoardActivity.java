package com.baehw1107.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by baehw_000 on 2017-05-30.
 */

public class BoardActivity extends Activity {


    Button tv_main_nextbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        bind();

    }


    private void bind() {

        tv_main_nextbutton = (Button) findViewById(R.id.tv_main_nextbutton);

        tv_main_nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectSubjectActivity.class);
                startActivity(intent);
            }
        });


    }
}