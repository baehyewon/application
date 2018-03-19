package com.baehw1107.share_park5;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReservationActivity extends AppCompatActivity {
    LinearLayout layout;
    String address, start_time, end_time, email;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        email = new String("baehw1107@naver.com");

        Intent it = getIntent();
        address = it.getStringExtra("address");
        start_time = it.getStringExtra("start_time");
        end_time = it.getStringExtra("end_time");


        layout = (LinearLayout)findViewById(R.id.my_reservation);


        LinearLayout layout_list = new LinearLayout(this);
        layout_list.setOrientation(LinearLayout.HORIZONTAL);
        layout_list.setPadding(30, 0, 20, 10); //0, 0, 20, 10
        //layout_list.setId(i);
        layout_list.setTag(address);

        int strokeWidth = 1; // 3px not dp
        int roundRadius = 15; // 8px not dp
        int strokeColor = Color.parseColor("#b6c8df");
        int fillColor = Color.parseColor("#e5f9ff");

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        layout_list.setBackground(gd);

        ////////////
        LinearLayout layout_item = new LinearLayout(this);
        layout_item.setOrientation(LinearLayout.VERTICAL);
        layout_item.setPadding(10, 0, 10, 10); // ,  , , 밑

        TextView tv_list = new TextView(this);
        tv_list.setText(email);
        tv_list.setTextSize(15);
        tv_list.setPadding(5, 5, 5, 5);
        layout_item.addView(tv_list);

        TextView tv_list2 = new TextView(this);
        tv_list2.setText("주소: " + address  + "\n");
        tv_list2.append("시작시간: " + start_time + "\n" + "종료시간: " + end_time);
        layout_item.addView(tv_list2);

        layout_list.addView(layout_item);

        layout.addView(layout_list);
    }
}
