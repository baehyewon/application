package com.baehw1107.picturealbum2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("명화 목록");
   }

   public void  onClick(View view){
       Intent it = new Intent(this, PictureActivity.class);
       startActivity(it);
   }


   public void displayPicture(View v){
       int id = v.getId();
       LinearLayout layout = (LinearLayout)v.findViewById(id);
       String tag = (String)layout.getTag();

      // Intent it = new Intent(this, Picture.class);
      // it.putExtra("it_tag", tag);
      // startActivity(it);

    //   finish();
   }
}
