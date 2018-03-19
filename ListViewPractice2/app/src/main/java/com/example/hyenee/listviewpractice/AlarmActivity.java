package com.example.hyenee.listviewpractice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AlarmActivity extends Activity implements View.OnClickListener{

    private TextView txtMessage;

    private Button btnOk;

    private int intentAlarmId = 0;
    private String intentName = "";
    private String intentPhone = "";
    private String intentBirthday = "";
    private String intentMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        txtMessage = (TextView) findViewById(R.id.aa_txt_message);

        btnOk = (Button) findViewById(R.id.aa_btn_ok);
        btnOk.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        if(intent != null)
        {
            intentAlarmId = intent.getIntExtra("alarmId", 0);
            intentName = intent.getStringExtra("name");
            intentPhone = intent.getStringExtra("phone");
            intentBirthday = intent.getStringExtra("birthday");
            intentMessage = intent.getStringExtra("message");

            String AlarmMsg = "";

            if(intentName!= null)
                AlarmMsg = intentName + "님의 생일이\n";

            if(intentBirthday != null)
                AlarmMsg += intentBirthday + " 입니다!.\n축하메시지를 보내주세요.";

            txtMessage.setText(AlarmMsg);

            Vibrator vibrator;
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(2000);

            AlarmUtil.unregisterAlarm(this, intentAlarmId);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.aa_btn_ok:
                finish();

                String[] splitResult = intentBirthday.split("-");

                int year = Integer.parseInt(splitResult[0]);
                int month = Integer.parseInt(splitResult[1]);
                int day = Integer.parseInt(splitResult[2]);

                AlarmUtil.setSms(this, intentAlarmId, intentName, intentPhone, intentMessage, year, month-1, day );

                /*
                Intent intent = new Intent(this, SmsActivity.class);
                intent.putExtra("name", intentName);
                intent.putExtra("phone", intentPhone);
                intent.putExtra("message", intentMessage);
                startActivity(intent);
                */

                break;
        }
    }
}
