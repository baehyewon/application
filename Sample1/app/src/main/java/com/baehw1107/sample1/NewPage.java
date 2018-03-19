package com.baehw1107.sample1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;

public class NewPage extends AppCompatActivity {

    TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_page);

        mText = (TextView) findViewById(R.id.TTT);

        Intent intent = getIntent();
        String name = intent.getExtras().getString("NAME");
        String color = intent.getExtras().getString("COLOR");
        mText.setText(name + color);

        new AlertDialog.Builder(this)
                .setTitle("축하합니다")
                .setMessage("당첨되신것을 축하합니다 상품을 받으시겠습니까?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .show();


    }

    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        if (KeyCode == KeyEvent.KEYCODE_BACK) {
            mText.setText("BACK");
            return (true);
        }
        else if(KeyCode == KeyEvent.KEYCODE_VOLUME_UP){
            mText.setText("Volume up");
            return (true);
        }
        return super.onKeyDown(KeyCode, event);
    }
}