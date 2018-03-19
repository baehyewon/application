package com.baehw1107.contents_database;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by baehw_000 on 2017-05-29.
 */

public class ContentsItem extends AppCompatActivity{

    SQLiteDatabase sqlitedb;
    DBManager dbmanager;

    String str_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tv_title = (TextView) findViewById(R.id.title);
        TextView tv_orientation = (TextView) findViewById(R.id.orientation);
        TextView tv_background = (TextView) findViewById(R.id.background);
        ImageView iv_photo = (ImageView) findViewById(R.id.photo);

        Intent it = getIntent();
        str_title = it.getStringExtra("it_title");
        String str_orientation = "";
        String str_background = "";
        String str_path = "";

        try {
            dbmanager = new DBManager(this);
            sqlitedb = dbmanager.getReadableDatabase();
            Cursor cursor = sqlitedb.query("photos", null, "title = ?", new String[]{str_title}, null, null, null, null);

            int i = 0;
            if (cursor.moveToNext()) {
                str_orientation = cursor.getString(cursor.getColumnIndex("orientation"));
                str_background = cursor.getString(cursor.getColumnIndex("background"));
                str_path = cursor.getString(cursor.getColumnIndex("path"));
            }

            sqlitedb.close();
            dbmanager.close();
        } catch(SQLiteException e) {
            Toast.makeText(this,  e.getMessage(), Toast.LENGTH_LONG).show();
        }

        tv_title.setText(str_title);
        tv_orientation.setText(str_orientation);
        tv_background.setText(str_background);

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(str_path, bmOptions);

        int WIDTH = 200;
        int width = WIDTH;
        float ratio = (float) bm.getHeight() / bm.getWidth();
        int height = (int) (WIDTH * ratio);

        Bitmap thumb = Bitmap.createScaledBitmap(bm, width, height, true);
        iv_photo.setImageBitmap(thumb);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            // 홈화면으로 이동
            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);
            finish();
            return true;
        }

        if (id == R.id.action_list) {
            // 사진 등록으로 이동
            Intent it = new Intent(this, ContentsList.class);
            startActivity(it);
            finish();
            return true;
        }

        if (id == R.id.action_reg) {
            // 사진 등록으로 이동
            Intent it = new Intent(this, ContentsReg.class);
            startActivity(it);
            finish();
            return true;
        }

        if (id == R.id.action_delete) {

            int del_count = deleteItem(str_title);
            if(del_count > 0) {
                Intent it = new Intent(this, ContentsList.class);
                startActivity(it);
                finish();
            } else {
                Toast.makeText(this, "사진을 삭제하지 못했습니다!", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private int deleteItem(String str_title) {
        int row = 0;
        try {
            dbmanager = new DBManager(this);
            sqlitedb = dbmanager.getReadableDatabase();
            row = sqlitedb.delete("photos", "title=" + "=?", new String[]{str_title});
            sqlitedb.close();
            dbmanager.close();
        } catch(SQLiteException e) {
            Toast.makeText(this,  e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return row;
    }
}
