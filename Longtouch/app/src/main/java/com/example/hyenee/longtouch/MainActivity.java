package com.example.hyenee.longtouch;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ArrayList<String> items = null;
    private ArrayAdapter<String> adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ListView의 데이타를 저장할 Adapter 생성
        ListView lv = (ListView)findViewById(R.id.lv_data);
        items = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, items);
        lv.setAdapter(adapter);

        // ListView의 이벤트 설정
        lv.setOnItemClickListener( new ListViewItemClickListener() );
        lv.setOnItemLongClickListener( new ListViewItemLongClickListener() );
    }

    /**
     * (추가) 버튼이 클릭되었을 경우.
     * EditText에 입력된 문자열을 ListView에 추가한다.
     * @param v
     */
    public void onBtnAddClick( View v )
    {
        EditText ed = (EditText)findViewById(R.id.ed_input);
        items.add( ed.getText().toString() );

        // 이 method를 호출하지 않을 경우, ListView에 추가된 문자열이 보이지 않는다.
        adapter.notifyDataSetChanged();
        ed.setText("");
    }

    /**
     * ListView의 item을 클릭했을 때.
     * alert로 클릭된 문자열을 보여준다.
     * @author stargatex
     *
     */
    private class ListViewItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            AlertDialog.Builder alertDlg = new AlertDialog.Builder(view.getContext());
            alertDlg.setPositiveButton( R.string.button_ok, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick( DialogInterface dialog, int which ) {
                    dialog.dismiss();  // AlertDialog를 닫는다.
                }
            });

            alertDlg.setMessage( items.get(position) );
            alertDlg.show();
        }
    }

    // Long click된 item의 index(position)을 기록한다.
    int selectedPos = -1;

    /**
     * ListView의 item을 길게 클릭했을 경우.
     * 클릭된 item을 삭제한다.
     * @author stargatex
     *
     */
    private class ListViewItemLongClickListener implements AdapterView.OnItemLongClickListener
    {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
        {
            selectedPos = position;
            AlertDialog.Builder alertDlg = new AlertDialog.Builder(view.getContext());
            alertDlg.setTitle(R.string.alert_title_question);

            // '예' 버튼이 클릭되면
            alertDlg.setPositiveButton( R.string.button_yes, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick( DialogInterface dialog, int which )
                {
                    items.remove(selectedPos);

                    // 아래 method를 호출하지 않을 경우, 삭제된 item이 화면에 계속 보여진다.
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();  // AlertDialog를 닫는다.
                }
            });

            // '아니오' 버튼이 클릭되면
            alertDlg.setNegativeButton( R.string.button_no, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick( DialogInterface dialog, int which ) {
                    dialog.dismiss();  // AlertDialog를 닫는다.
                }
            });


            alertDlg.setMessage( String.format( getString(R.string.alert_msg_delete),
                    items.get(position)) );
            alertDlg.show();
            return false;
        }

    }
}