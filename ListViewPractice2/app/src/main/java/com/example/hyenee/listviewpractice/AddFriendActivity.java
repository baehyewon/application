package com.example.hyenee.listviewpractice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddFriendActivity extends Activity implements View.OnClickListener{

    private CheckBox chkIsHoney;

    private EditText edtName, edtPhone, edtGavenGift, edtTakenGift, edtStyle, edtLike, edtDisLike, edtWillGiveGift, edtMessage;

    private DatePicker dateBirthday, dateAlarm;
    private TimePicker timeAlarm;

    private Button btnAdd;

    private DBHelper mDBHelper;

    private boolean isModify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        mDBHelper = new DBHelper(this, DBConstants.DATABASE_NAME);

        edtName = findViewById(R.id.aaf_edt_name);
        edtPhone = findViewById(R.id.aaf_edt_phone);
        chkIsHoney = findViewById(R.id.aaf_chk_honey);

        edtGavenGift = findViewById(R.id.aaf_edt_gaven_gift);
        edtTakenGift = findViewById(R.id.aaf_edt_taken_gift);
        edtStyle = findViewById(R.id.aaf_edt_style);
        edtLike = findViewById(R.id.aaf_edt_like);
        edtDisLike = findViewById(R.id.aaf_edt_dislike);
        edtWillGiveGift = findViewById(R.id.aaf_edt_will_give_gift);

        edtMessage = findViewById(R.id.aaf_edt_message);

        dateBirthday = findViewById(R.id.aaf_date_birthday);
        dateBirthday.init(dateBirthday.getYear(), dateBirthday.getMonth(), dateBirthday.getDayOfMonth(),

            new DatePicker.OnDateChangedListener() {

                //값이 바뀔때마다 텍스트뷰의 값을 바꿔준다.
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    // TODO Auto-generated method stub
                    dateAlarm.init(year, monthOfYear, dayOfMonth - 7, null);
                }
        });

        dateAlarm = findViewById(R.id.aaf_date_alarm);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        /**
         * 알람 날짜는 생일 7일전으로 맞춘다.
         */
        dateAlarm.init(year, month, day-7, null);

        timeAlarm = findViewById(R.id.aaf_time_alarm);

        btnAdd = findViewById(R.id.aaf_btn_add);
        btnAdd.setOnClickListener(this);

        isModify = getIntent().getBooleanExtra(Define.INTENT_INFO_IS_MODIFY, false);
        if(isModify == true)
        {
            btnAdd.setText("수정");

            String strIsHoney = getIntent().getStringExtra(Define.INTENT_INFO_IS_HONEY);
            String strName = getIntent().getStringExtra(Define.INTENT_INFO_NAME);
            String strPhone = getIntent().getStringExtra(Define.INTENT_INFO_PHONE);
            String strBirthday = getIntent().getStringExtra(Define.INTENT_INFO_BIRTHDAY);
            String strAlarmDate = getIntent().getStringExtra(Define.INTENT_INFO_ALARM_DATE);
            String strAlarmHour = getIntent().getStringExtra(Define.INTENT_INFO_ALARM_HOUR);
            String strAlarmMinute = getIntent().getStringExtra(Define.INTENT_INFO_ALARM_MINUTE);
            String strGavenGift = getIntent().getStringExtra(Define.INTENT_INFO_GAVEN_GIFT);
            String strTakenGift = getIntent().getStringExtra(Define.INTENT_INFO_TAKEN_GIFT);
            String strStyle = getIntent().getStringExtra(Define.INTENT_INFO_STYLE);
            String strLike = getIntent().getStringExtra(Define.INTENT_INFO_LIKE);
            String strDisLike = getIntent().getStringExtra(Define.INTENT_INFO_DISLIKE);
            String strWillGiveGift = getIntent().getStringExtra(Define.INTENT_INFO_WILL_GIVE_GIFT);
            String strMessage = getIntent().getStringExtra(Define.INTENT_INFO_MESSAGE);

            boolean isHoney = false;

            if(strIsHoney.equals("T"))
                isHoney = true;
            else
                isHoney = false;

            chkIsHoney.setChecked(isHoney);
            edtName.setText(strName);
            edtPhone.setText(strPhone);

            String[] splitResult = strBirthday.split("-");

            year = Integer.parseInt(splitResult[0]);
            month = Integer.parseInt(splitResult[1]);
            day = Integer.parseInt(splitResult[2]);
            dateBirthday.init(year, month-1, day, null);

            splitResult = strAlarmDate.split("-");
            year = Integer.parseInt(splitResult[0]);
            month = Integer.parseInt(splitResult[1]);
            day = Integer.parseInt(splitResult[2]);
            dateAlarm.init(year, month-1, day, null);

            int hour = Integer.parseInt(strAlarmHour);
            int minute = Integer.parseInt(strAlarmMinute);
            timeAlarm.setHour(hour);
            timeAlarm.setMinute(minute);

            edtGavenGift.setText(strGavenGift);
            edtTakenGift.setText(strTakenGift);
            edtStyle.setText(strStyle);
            edtLike.setText(strLike);
            edtDisLike.setText(strDisLike);
            edtWillGiveGift.setText(strWillGiveGift);
            edtMessage.setText(strMessage);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.aaf_btn_add:
                String name = edtName.getText().toString();
                if(name.equals(""))
                {
                    Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String phone = edtPhone.getText().toString();
                if(phone.equals(""))
                {
                    Toast.makeText(this, "연락처를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String message = edtMessage.getText().toString();
                if(message.equals(""))
                {
                    Toast.makeText(this, "축하메시지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String birthday = String.format("%d-%d-%d", dateBirthday.getYear(), dateBirthday.getMonth() + 1, dateBirthday.getDayOfMonth());
                String alarm_date = String.format("%d-%d-%d", dateAlarm.getYear(), dateAlarm.getMonth() + 1, dateAlarm.getDayOfMonth());
                String alarm_hour = Integer.toString(timeAlarm.getCurrentHour());
                String alarm_minute = Integer.toString(timeAlarm.getCurrentMinute());

                int AlarmId = 0;
                AlarmId = mDBHelper.getLastAlarmId();
                AlarmId++;

                String isHoney = "F";
                if(chkIsHoney.isChecked() == true)
                    isHoney = "T";
                else
                    isHoney = "F";

                //DB 수정하기 일경우
                //기존 DB는 삭제하고 새로 추가한다.
                if(isModify == true)
                {
                    mDBHelper.deleteDB(name);
                }

                int nResult = mDBHelper.insertDB(name, phone, isHoney, birthday, alarm_date, alarm_hour, alarm_minute, Integer.toString(AlarmId), edtGavenGift.getText().toString(),
                        edtTakenGift.getText().toString(), edtStyle.getText().toString(), edtLike.getText().toString(), edtDisLike.getText().toString(),
                        edtWillGiveGift.getText().toString(), message);

                //DB등록이 성공일 경우 알람 설정한다.
                if(nResult == 1)
                {
                    AlarmUtil.setAlarm(this, AlarmId, name, phone, birthday, message, dateAlarm.getYear(), dateAlarm.getMonth(), dateAlarm.getDayOfMonth(), timeAlarm.getCurrentHour(), timeAlarm.getCurrentMinute());
                    Toast.makeText(this, "DB 등록 완료!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if(nResult == 100)
                {
                    Toast.makeText(this, "DB 등록 실패(이름 중복)!", Toast.LENGTH_SHORT).show();
                }
                else if(nResult == 0)
                {
                    Toast.makeText(this, "DB 등록 실패!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
