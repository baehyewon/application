package com.baehw1107.contents_database;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by baehw_000 on 2017-05-29.
 */

public class ContentsList extends AppCompatActivity implements OnClickListener{

    SQLiteDatabase sqlitedb;
    DBManager dbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout layout = (LinearLayout)findViewById(R.id.photos);

        try {
            dbmanager = new DBManager(this);
            sqlitedb = dbmanager.getReadableDatabase();
            Cursor cursor = sqlitedb.query("photos", null, null, null, null, null, "title");

            int i = 0;
            while(cursor.moveToNext()) {
                String str_title       = cursor.getString(cursor.getColumnIndex("title"));
                String str_orientation = cursor.getString(cursor.getColumnIndex("orientation"));
                String str_background  = cursor.getString(cursor.getColumnIndex("background"));
                String str_path        = cursor.getString(cursor.getColumnIndex("path"));

                LinearLayout layout_list = new LinearLayout(this);
                layout_list.setOrientation(LinearLayout.HORIZONTAL);
                layout_list.setPadding(20,  10,  20,  10);
                layout_list.setId(i);
                layout_list.setTag(str_title);

                ImageView iv_photo = new ImageView(this);
                Uri uriFromPath = Uri.fromFile(new File(str_path));
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriFromPath));
                    Bitmap thumb = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
                    iv_photo.setImageBitmap(thumb);
                    layout_list.addView(iv_photo);
                } catch (FileNotFoundException e) {
                    Toast.makeText(this,  "에러: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                int strokeWidth = 5; // 3px not dp
                int roundRadius = 15; // 8px not dp
                int strokeColor = Color.parseColor("#2E3135");
                int fillColor = Color.parseColor("#DFDFE0");

                GradientDrawable gd = new GradientDrawable();
                gd.setColor(fillColor);
                gd.setCornerRadius(roundRadius);
                gd.setStroke(strokeWidth, strokeColor);
                layout_list.setBackground(gd);

                ////////////
                LinearLayout layout_item = new LinearLayout(this);
                layout_item.setOrientation(LinearLayout.VERTICAL);
                layout_item.setPadding(20, 10, 20, 10);

                TextView tv_list = new TextView(this);
                tv_list.setText(str_title);
                tv_list.setTextSize(20);
                tv_list.setPadding(5, 5, 5, 5);
                layout_item.addView(tv_list);

                TextView tv_list2 = new TextView(this);
                tv_list2.setText("방향: " + str_orientation  + "\n");
                tv_list2.append("배경: " + str_background);
                layout_item.addView(tv_list2);

                layout_list.addView(layout_item);

                layout.addView(layout_list);
                layout_list.setOnClickListener(this);

                i++;
            }

            cursor.close();
            sqlitedb.close();
            dbmanager.close();

        } catch(SQLiteException e) {
            Toast.makeText(this,  e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        LinearLayout layout = (LinearLayout)findViewById(id);
        String str_title = (String)layout.getTag();

        Intent it    = new Intent(getApplicationContext(), ContentsItem.class);
        it.putExtra("it_title", str_title);
        startActivity(it);
        finish();
    }
}
