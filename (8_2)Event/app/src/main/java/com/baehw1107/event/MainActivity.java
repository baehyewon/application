package com.baehw1107.event;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void displayPoem(View v){
        int id;
        Resources res = getResources();

        id = v. getId();
        LinearLayout layout = (LinearLayout)v.findViewById(id);
        String tag = (String)layout.getTag();

        int id_title = res.getIdentifier("title" + tag, "string", getPackageName());
        String title = res.getString(id_title);

        int id_author = res.getIdentifier("author" + tag, "string", getPackageName());
        String author = res.getString(id_author);

        int id_body = res.getIdentifier("body" + tag, "string", getPackageName());
        String body = res.getString(id_body);

        Toast toast = Toast.makeText(this, title + "/" + author + "\n\n" + body,
                Toast.LENGTH_LONG);

        toast.show();



    }
}
