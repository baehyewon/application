package com.example.hyenee.listviewpractice;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener{

    private String LOG_TAG = "MainActivity";

    private TextView txtResult;

    private Button /*btnLogout,*/ btnModify, btnDelete, btnAdd, btnRecommend;

    private ScrollView mScrollView;
    private ListView mListView;

    private ArrayList<FriendListViewItem> mFriendArrayList;
    private FriendListViewAdapter mFriendListAdapter = null;
    private FriendListViewItem mSelItem = null;
    private int mSelAlarmId = 0;
    private List<FriendInfo> mFriendList = null;

    private DBHelper mDBHelper;

    private Dialog RecommendDlg;

    boolean [] RecommendResult;

    private final int MY_PERMISSION_REQUEST_CODE = 100;

    private AlertDialog mDlg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelper = new DBHelper(this, DBConstants.DATABASE_NAME);

        txtResult = findViewById(R.id.am_txt_result);

        String Nickname = getIntent().getStringExtra("nickname");

        txtResult.setText(Nickname + "님 로그인");

        /*
        btnLogout = findViewById(R.id.am_btn_logout);
        btnLogout.setOnClickListener(this);
        */

        btnModify = findViewById(R.id.am_btn_modify);
        btnModify.setOnClickListener(this);

        btnDelete = findViewById(R.id.am_btn_delete);
        btnDelete.setOnClickListener(this);

        btnAdd = findViewById(R.id.am_btn_add);
        btnAdd.setOnClickListener(this);

        btnRecommend = findViewById(R.id.am_btn_recommend);
        btnRecommend.setOnClickListener(this);

        mScrollView = (ScrollView) findViewById(R.id.am_scroll);

        mListView = (ListView)findViewById(R.id.am_listview);
        mListView.setOnItemClickListener( new ListViewItemClickListener() );
        mListView.requestFocusFromTouch();
        mListView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                mScrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        if(checkFilePermission())
        {

        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_PHONE_STATE}, MY_PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkFilePermission() {
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_SMS);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE);

        if ( result1 != 0 || result2 != 0 || result3 != 0)
            return false;

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if ( grantResults.length > 0 ) {

                    boolean result1 = (grantResults[0] == PackageManager.PERMISSION_GRANTED);
                    boolean result2 = (grantResults[1] == PackageManager.PERMISSION_GRANTED);
                    boolean result3 = (grantResults[1] == PackageManager.PERMISSION_GRANTED);

                    // 권한이 허락되면 어플리케이션을 시작함.
                    if ( result1 && result2 && result3) {

//                        startApplication();
                    } else {

                    }
                } else {
                    // 아닐 경우 종료.
                    //                  Toast.makeText(getApplicationContext(), getString(R.string.toast_require_file), Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateListView();
    }

    public void updateListView()
    {
        mFriendArrayList = new ArrayList<FriendListViewItem>();
        ArrayList<FriendListViewItem> mReOrderList = new ArrayList<FriendListViewItem>();

        mFriendList = mDBHelper.getFriendList();

        for(int i=0; i<mFriendList.size(); i++) {

            FriendInfo info = mFriendList.get(i);

            FriendListViewItem item = new FriendListViewItem();

            item.setId(info.getId());
            item.setIsHoney(info.getIsHoney());
            item.setName(info.getName());
            item.setBirthday(info.getBirthday());

            mFriendArrayList.add(item);
        }

        Collections.sort(mFriendArrayList, myComparator);
        Collections.reverse(mFriendArrayList);

        mFriendListAdapter = new FriendListViewAdapter(this, mFriendArrayList);

        mListView.setAdapter(mFriendListAdapter);
        mFriendListAdapter.notifyDataSetChanged();
    }

    private Comparator<FriendListViewItem> myComparator = new Comparator<FriendListViewItem>() {
        private final Collator collator = Collator.getInstance();

        @Override
        public int compare(FriendListViewItem object1, FriendListViewItem object2) {
            return collator.compare(object1.getIsHoney(), object2.getIsHoney());
        }
    };

    private class ListViewItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            mFriendListAdapter.setSelectedIndex(position);
            mFriendListAdapter.notifyDataSetChanged();

            mFriendList = mDBHelper.getFriendList();
            FriendInfo dbInfo = null;

            mSelItem = (FriendListViewItem) parent.getItemAtPosition(position);

            for(int i=0; i<mFriendList.size(); i++) {

                dbInfo = mFriendList.get(i);

                if(dbInfo.getName().equals(mSelItem.getName()))
                {
                    mSelAlarmId = Integer.parseInt(dbInfo.getAlarmId());
                    break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
/*
    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
*/

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Define.INTENT_REQ_RECOMMEND) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");

                RecommendResult = data.getBooleanArrayExtra("checkResult");

                showRecommendDialog();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //만약 반환값이 없을 경우의 코드를 여기에 작성하세요.
            }
        }
    }
    */

    /*
        public void showRecommendDialog() {

            RecommendDlg = new Dialog(this);

            RecommendDlg.setContentView(R.layout.dialog_recommend);

            Button btnOk = RecommendDlg.findViewById(R.id.dlg_ok);
            btnOk.setOnClickListener(this);

            TextView txtRecommend = RecommendDlg.findViewById(R.id.dlg_txt_recommend);

            String strRecommend = "";

            for (int i = 0; i < 11; i++)
            {
                if(RecommendResult[i] == true)
                    strRecommend += Define.Recommend_List[i] + "\n";
            }

            txtRecommend.setText(strRecommend);

            RecommendDlg.show();
        }
    */
    public void delFriendListDialog() {

        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);

        alt_bld.setTitle("친구 삭제");
        alt_bld.setMessage("선택하신 친구를 DB에서 삭제하시겠습니까?.");

        alt_bld.setCancelable(false)
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                        mDBHelper.deleteDB(mSelItem.getName());
                        AlarmUtil.unregisterAlarm(MainActivity.this, mSelAlarmId);
                        updateListView();
                        mSelItem = null;
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'NO' Button
                        dialog.cancel();
                    }
                });

        // 에러 메시지 다이아로그를 화면에 출력한다.
        mDlg = alt_bld.create();
        mDlg.show();
    }

    @Override
    public void onClick(View view) {

        Intent intent = null;

        switch (view.getId())
        {

            /*
            case R.id.dlg_ok:
                RecommendDlg.dismiss();
                break;
*/
            /*
            case R.id.am_btn_logout:
                UserManagement.requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        redirectLoginActivity();
                    }
                });
                break;
                */

            case R.id.am_btn_modify:

                if(mSelItem == null)
                {
                    Toast.makeText(this, "수정할 친구를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                intent = new Intent(this, AddFriendActivity.class);

                mFriendList = mDBHelper.getFriendList();
                FriendInfo modifyInfo = null;

                for(int i=0; i<mFriendList.size(); i++) {

                    modifyInfo = mFriendList.get(i);

                    if(modifyInfo.getName().equals(mSelItem.getName()))
                    {
                        break;
                    }
                }

                intent.putExtra(Define.INTENT_INFO_IS_MODIFY, true);
                intent.putExtra(Define.INTENT_INFO_IS_HONEY, modifyInfo.getIsHoney());
                intent.putExtra(Define.INTENT_INFO_NAME, modifyInfo.getName());
                intent.putExtra(Define.INTENT_INFO_PHONE, modifyInfo.getPhone());
                intent.putExtra(Define.INTENT_INFO_BIRTHDAY, modifyInfo.getBirthday());
                intent.putExtra(Define.INTENT_INFO_ALARM_DATE, modifyInfo.getAlarmDate());
                intent.putExtra(Define.INTENT_INFO_ALARM_HOUR, modifyInfo.getAlarmHour());
                intent.putExtra(Define.INTENT_INFO_ALARM_MINUTE, modifyInfo.getAlarmMinute());
                intent.putExtra(Define.INTENT_INFO_GAVEN_GIFT, modifyInfo.getGavenGift());
                intent.putExtra(Define.INTENT_INFO_TAKEN_GIFT, modifyInfo.getTakenGift());
                intent.putExtra(Define.INTENT_INFO_STYLE, modifyInfo.getStyle());
                intent.putExtra(Define.INTENT_INFO_LIKE, modifyInfo.getLike());
                intent.putExtra(Define.INTENT_INFO_DISLIKE, modifyInfo.getDislike());
                intent.putExtra(Define.INTENT_INFO_WILL_GIVE_GIFT, modifyInfo.getWillGiveGift());
                intent.putExtra(Define.INTENT_INFO_MESSAGE, modifyInfo.getAlarmMsg());
                startActivity(intent);
                break;

            case R.id.am_btn_delete:
                if(mSelItem == null)
                {
                    Toast.makeText(this, "삭제할 친구를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                delFriendListDialog();
                break;

            case R.id.am_btn_add:
                intent = new Intent(this, AddFriendActivity.class);
                startActivity(intent);
                break;
/*
            case R.id.am_btn_recommend:
                intent = new Intent(this, RecommendListActivity.class);
                startActivityForResult(intent, Define.INTENT_REQ_RECOMMEND);
                break;
                */
        }
    }
}