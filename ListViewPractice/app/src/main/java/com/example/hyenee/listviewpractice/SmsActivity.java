package com.example.hyenee.listviewpractice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SmsActivity extends Activity implements View.OnClickListener{

    private EditText edtName, edtPhone, edtSms;

    private Button btnSend, btnCancel;

    private int intentAlarmId = 0;
    private String intentName = "";
    private String intentPhone = "";
    private String intentSms = "";

    private AlertDialog mDlg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        edtName = (EditText) findViewById(R.id.as_edt_name);
        edtPhone = (EditText) findViewById(R.id.as_edt_phone);
        edtSms = (EditText) findViewById(R.id.as_edt_sms);

        btnSend = (Button) findViewById(R.id.as_btn_send);
        btnSend.setOnClickListener(this);

        btnCancel = (Button) findViewById(R.id.as_btn_cancel);
        btnCancel.setOnClickListener(this);

        intentAlarmId = getIntent().getIntExtra("alarmId", 0);
        intentName = getIntent().getStringExtra("name");
        intentPhone = getIntent().getStringExtra("phone");
        intentSms = getIntent().getStringExtra("message");

        if(intentName != null)
            edtName.setText(intentName);

        if(intentPhone != null)
            edtPhone.setText(intentPhone);

        if(intentSms != null)
            edtSms.setText(intentSms);

        Vibrator vibrator;
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

        AlarmUtil.unregisterSms(this, intentAlarmId);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void sendSMS(String smsNumber, String smsText){
        PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT_ACTION"), 0);
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED_ACTION"), 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode()){
                    case Activity.RESULT_OK:
                        // 전송 성공
                        Toast.makeText(SmsActivity.this, "전송 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        // 전송 실패
                        Toast.makeText(SmsActivity.this, "전송 실패", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        // 서비스 지역 아님
                        Toast.makeText(SmsActivity.this, "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        // 무선 꺼짐
                        Toast.makeText(SmsActivity.this, "무선(Radio)가 꺼져있습니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        // PDU 실패
                        Toast.makeText(SmsActivity.this, "PDU Null", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_SENT_ACTION"));

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        // 도착 완료
                        Toast.makeText(SmsActivity.this, "SMS 도착 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        // 도착 안됨
                        Toast.makeText(SmsActivity.this, "SMS 도착 실패", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_DELIVERED_ACTION"));

        SmsManager mSmsManager = SmsManager.getDefault();
        mSmsManager.sendTextMessage(smsNumber, null, smsText, sentIntent, deliveredIntent);
    }

    public void cancelSendSmsDialog() {

        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);

        alt_bld.setTitle("전송 취소");
        alt_bld.setMessage("SMS 메시지 전송을 취소 하시겠습니까?.");

        alt_bld.setCancelable(false)
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                        finish();
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'NO' Button
                        dialog.cancel();
                    }
                });

        // 에러 메시지 다이아로그를 화면에 출력한다.
        mDlg = alt_bld.create();
        mDlg.show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.as_btn_send:
                sendSMS(intentPhone, intentSms);
                break;

            case R.id.as_btn_cancel:
                cancelSendSmsDialog();
                break;
        }
    }
}
