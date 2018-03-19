package com.baehw1107.callmyfriend;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void call(View v) {
        int id = v.getId();
        LinearLayout layout = (LinearLayout) v.findViewById(id);
        String tag = (String) layout.getTag();

        Resources res = getResources();

        int id_tel = res.getIdentifier("tel" + tag, "string", getPackageName());

        String tel = res.getString(id_tel);

        Uri number = Uri.parse("tel:" + tel);
        Intent intent = new Intent(Intent.ACTION_CALL, number);
        startActivity(intent);
    }
}
