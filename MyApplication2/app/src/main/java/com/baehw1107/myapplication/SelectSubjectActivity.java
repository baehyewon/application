package com.baehw1107.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by win10_pc on 2016-07-07.
 */
public class SelectSubjectActivity extends AppCompatActivity {
    static final String[] LIST_MENU = {"LIST1", "LIST2", "LIST3"} ;

    ImageButton addButton;
    Button lsearchbtn;
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String msg = data.getStringExtra("subject")+","+data.getIntExtra("subject_id",0);
            Toast.makeText(getApplication(), msg, Toast.LENGTH_LONG).show();
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.tableview_select1) ;

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU) ;

        ListView listview = (ListView) findViewById(R.id.local_search) ;
        listview.setAdapter(adapter) ;
        lsearchbtn = (Button) findViewById(R.id.local_search);
        lsearchbtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), PostActivity.class);
                startActivity(it);
            }
        });

        TextView tv_result = (TextView)findViewById(R.id.add_address);

        Intent intent = getIntent();
        String result = intent.getStringExtra("post_result");

        tv_result.setText(result);

/*
        // 빈 데이터 리스트 생성.
        final ArrayList<String> items = new ArrayList<String>() ;
        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items);

*/

        // add button에 대한 이벤트 처리.
        addButton = (ImageButton) findViewById(R.id.add);
        final ArrayAdapter finalAdapter = adapter;
        addButton.setOnClickListener(new Button.OnClickListener() {
                                         public void onClick (View v){
                                             int count;
                                             count = finalAdapter.getCount();

                                             // 아이템 추가.
                                             //items.add("책이름명" + Integer.toString(count + 1));

                                             // listview 갱신
                                             finalAdapter.notifyDataSetChanged();
                                         }
                                     }

        );


    }


    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id)
        {
            if(position > 0){
                // Intent intent = new Intent(getApplicationContext(), SelectDetailActivity.class);
                // startActivityForResult(intent, 1);
            }

        }


    };

}