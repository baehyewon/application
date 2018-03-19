package com.baehw1107.mainframe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by baehw_000 on 2017-05-28.
 */

public class ContentsSelected extends AppCompatActivity {
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            TextView tv_title = (TextView) findViewById(R.id.title);
            TextView tv_orientation = (TextView) findViewById(R.id.orientation);
            TextView tv_background = (TextView) findViewById(R.id.background);
            ImageView iv_photo = (ImageView) findViewById(R.id.photo);

            Intent it = getIntent();
            String str_title = it.getStringExtra("it_title");
            String str_orientation = it.getStringExtra("it_orientation");
            String str_background1 = it.getStringExtra("it_background1");
            String str_background2 = it.getStringExtra("it_background2");
            String str_background3 = it.getStringExtra("it_background3");
            String str_path = it.getStringExtra("it_path");

            tv_title.setText(str_title);
            tv_orientation.setText(str_orientation);
            tv_background.setText(str_background1 + " ");
            tv_background.append(str_background2 + " ");
            tv_background.append(str_background3);

            Bitmap thumb = (Bitmap) getIntent().getExtras().get("it_thumb");
            ImageView it_thumb = (ImageView)findViewById(R.id.photo);
            it_thumb.setImageBitmap(thumb);

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            //Bitmap bm = BitmapFactory.decodeFile(str_path, bmOptions);
            ////Bitmap bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);

            // int WIDTH = 150;
            //int width = WIDTH;
            //  float ratio = (float) bm.getHeight() / bm.getWidth();
            //int height = (int) (WIDTH * ratio);

            // Bitmap thumb = Bitmap.createScaledBitmap(bm, width, height, true);
            //  iv_photo.setImageBitmap(thumb);

        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_detail, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.action_settings2) {
                Intent it = new Intent(this, MainActivity.class);
                startActivity(it);
                finish();
                return true;
            }

            if (id == R.id.action_settings1) {
                Intent it = new Intent(this, ContentsRegForm.class);
                startActivity(it);
                finish();
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    }