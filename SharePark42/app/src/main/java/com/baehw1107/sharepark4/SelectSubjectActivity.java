package com.baehw1107.sharepark4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by win10_pc on 2016-07-07.
 */
public class SelectSubjectActivity extends AppCompatActivity {
    static final String[] LIST_MENU = {"LIST1", "LIST2", "LIST3"};

    String time1, time2;

    ImageButton addButton;
    Button lsearchbtn;
    String s_am_pm1, s_am_pm2, s_time_gap1, s_time_gap2;

    Spinner am_pm1, am_pm2, time_gap1, time_gap2;

    TextView text_am_pm1, text_time_gap1, text_am_pm2, text_time_gap2;

    /* @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if (resultCode == RESULT_OK) {
             String msg = data.getStringExtra("subject")+","+data.getIntExtra("subject_id",0);
             Toast.makeText(getApplication(), msg, Toast.LENGTH_LONG).show();
         }
     }
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tableview_select1);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU);

        lsearchbtn = (Button) findViewById(R.id.local_search);
        lsearchbtn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                startActivity(intent);
            }
        });

        //postapi -> 우편번호 주소 텍스트로 받아옴.
        TextView tv_result = (TextView) findViewById(R.id.add_address);
        Intent intent2 = getIntent();
        String result = intent2.getStringExtra("post_result");
        tv_result.setText(result);

        am_pm1 = (Spinner) findViewById(R.id.am_pm1);
        text_am_pm1 = (TextView) findViewById(R.id.text_am_pm1);

        am_pm1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text_am_pm1.setText((CharSequence) parent.getItemAtPosition(position));
                s_am_pm1 = text_am_pm1.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ////
        am_pm2 = (Spinner) findViewById(R.id.am_pm2);
        text_am_pm2 = (TextView) findViewById(R.id.text_am_pm2);

        am_pm2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text_am_pm2.setText((CharSequence) parent.getItemAtPosition(position));
                s_am_pm2 = text_am_pm2.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        /////
        time_gap1 = (Spinner) findViewById(R.id.time_gap1);
        text_time_gap1 = (TextView) findViewById(R.id.text_time_gap1);

        time_gap1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text_time_gap1.setText((CharSequence) parent.getItemAtPosition(position));
                s_time_gap1 = text_time_gap1.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        /////
        time_gap2 = (Spinner) findViewById(R.id.time_gap2);
        text_time_gap2 = (TextView) findViewById(R.id.text_time_gap2);

        time_gap2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text_time_gap2.setText((CharSequence) parent.getItemAtPosition(position));
                s_time_gap2 = text_time_gap2.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        // add button에 대한 이벤트 처리.
        addButton = (ImageButton) findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(getApplicationContext(), Main2Activity.class);
                it.putExtra("post_result", result);
                it.putExtra("post_am_pm1", s_am_pm1);
                it.putExtra("post_am_pm2", s_am_pm2);
                it.putExtra("post_time_gap1", s_time_gap1);
                it.putExtra("post_time_gap2", s_time_gap2);
                startActivity(it);
                finish();
            }
        });

     /*   final ArrayAdapter finalAdapter = adapter;
        addButton.setOnClickListener(new Button.OnClickListener() {
                                         public void onClick (View v){
                                             int count;
                                             count = finalAdapter.getCount();

                                             // 아이템 추가.


                                             // listview 갱신
                                             //메인액티비티로 넘어가기
                                             finalAdapter.notifyDataSetChanged();
                                             Intent it = new Intent(getApplicationContext(), Main2Activity.class);
                                             startActivity(it);
                                         }
                                     }

        );*/


    }


    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            if (position > 0) {
                // Intent intent = new Intent(getApplicationContext(), SelectDetailActivity.class);
                // startActivityForResult(intent, 1);
            }
        }
    };

}