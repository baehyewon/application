package com.baehw1107.share_content5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    //TextView tv_park;
    String p_result, p_am_pm1, p_am_pm2, p_time_gap1, p_time_gap2, all, email;
    ListView list;
    ImageButton searchbtn;
    EditText editsearch;

    private SharedPreferences mSharedPreferences;
    //private String mToken;
    //private String mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        editsearch = (EditText) findViewById(R.id.edit_search);
        editsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //검색버튼
        searchbtn = (ImageButton)findViewById(search);
        searchbtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });





        //등록하기 버튼 선언
        Button tv_main_nextbutton = (Button) findViewById(R.id.tv_main_nextbutton);
        //등록하기 버튼 리스너
        tv_main_nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //1. 등록하기 버튼을 클릭하면 SelectSubjectActivity.JAVA클래스로간다.
                Intent intent = new Intent(getApplicationContext(), SelectSubjectActivity.class);
                startActivity(intent);
            }
        });

        //ListView listview_main;

        Intent it = getIntent();
        p_result = it.getStringExtra("post_result");
        p_am_pm1 = it.getStringExtra("post_am_pm1");
        p_am_pm2 = it.getStringExtra("post_am_pm2");
        p_time_gap1 = it.getStringExtra("post_time_gap1");
        p_time_gap2 = it.getStringExtra("post_time_gap2");
        email = it.getStringExtra("email");

        all = new String(p_result + "\n" + "시작시간: " + p_am_pm1 + p_time_gap1 + "\n" + "종료시간: " + p_am_pm2 + p_time_gap2);

        LinearLayout layout = (LinearLayout)findViewById(R.id.photos);

        LinearLayout layout_list = new LinearLayout(this);
        layout_list.setOrientation(LinearLayout.HORIZONTAL);
        layout_list.setPadding(30, 0, 20, 10); //0, 0, 20, 10
        //layout_list.setId(i);
        layout_list.setTag(p_result);

        int strokeWidth = 1; // 3px not dp
        int roundRadius = 15; // 8px not dp
        int strokeColor = Color.parseColor("#b6c8df");
        int fillColor = Color.parseColor("#f6feff");

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        layout_list.setBackground(gd);

        ////////////
        LinearLayout layout_item = new LinearLayout(this);
        layout_item.setOrientation(LinearLayout.VERTICAL);
        layout_item.setPadding(10, 0, 10, 10); // ,  , , 밑

        TextView tv_list = new TextView(this);
        tv_list.setText(email);
        tv_list.setTextSize(15);
        tv_list.setPadding(5, 5, 5, 5);
        layout_item.addView(tv_list);

        TextView tv_list2 = new TextView(this);
        tv_list2.setText("주소: " + p_result  + "\n");
        tv_list2.append("시작시간: " + p_am_pm1 + p_time_gap1 + "\n" + "종료시간: " + p_am_pm2 + p_time_gap2);
        layout_item.addView(tv_list2);

        layout_list.addView(layout_item);

        layout.addView(layout_list);
        layout_list.setOnClickListener(this);

    /*
        //tv_park = (TextView) findViewById(R.id.tv_park);
        //tv_park.setText(all);

        ArrayList<String> item = new ArrayList<String>();
        // 데이터를 저장하고 세팅하는 곳, 스트링형식의 배열어뎁터 만들기
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, item);
        item.add(all);

        list = (ListView) findViewById(R.id.listview_main);
        list.setAdapter(adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        adapter.notifyDataSetChanged();
    /*
        //postapi -> 우편번호 주소 텍스트로 받아옴.
        TextView tv_result = (TextView)findViewById(R.id.add_address);
        Intent intent = getIntent();
        String result = intent.getStringExtra("post_result");
        tv_result.setText(result);
    */

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_page) {
            // Handle the camera action
        } else if (id == R.id.nav_parking_lot) {

        } else if (id == R.id.nav_reserve) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
