package com.baehw1107.share_park5;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    String start_time, end_time, address;

    TextView address_detail, tv_start_time, tv_end_time;
    ImageButton btn_use;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        address_detail = (TextView) findViewById(R.id.address_detail);
        tv_start_time = (TextView)findViewById(R.id.tv_start_time);
        tv_end_time = (TextView)findViewById(R.id.tv_end_time);

        Intent intent2 = getIntent();
        address = intent2.getStringExtra("address");
        start_time = intent2.getStringExtra("start_time");
        end_time = intent2.getStringExtra("end_time");

        address_detail.setText(address);
        tv_start_time.setText(start_time);
        tv_end_time.setText(end_time);

        // add button에 대한 이벤트 처리.
        btn_use = (ImageButton) findViewById(R.id.btn_use);
        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackBarMessage("예약되셨습니다.");

                Intent it = new Intent(getApplicationContext(), ReservationActivity.class);
                it.putExtra("address", address);
                it.putExtra("start_time",start_time);
                it.putExtra("end_time", end_time);
                startActivity(it);
                finish();
            }
        });


    }

    private void showSnackBarMessage(String message) {
        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_LONG).show();
    }
}
