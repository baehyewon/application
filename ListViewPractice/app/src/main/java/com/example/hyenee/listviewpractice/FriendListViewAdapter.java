package com.example.hyenee.listviewpractice;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FriendListViewAdapter extends BaseAdapter /*implements CompoundButton.OnCheckedChangeListener*/ /*View.OnClickListener*/{

    private ArrayList<FriendListViewItem> FriendArrayList = null;
    private ListViewHolder viewHolder = null;

//    private MemoInfoActivity mActivity;
    private Context mContext = null;
    private LayoutInflater inflater = null;

    //리스트에서 선택한 Position
    private int mSelectedPosition = -1;

    private int mPos = 0;

    private DBHelper mDBHelper;

    private class ListViewHolder {

        //ID(인덱스)
//        public TextView mTxtId;

        //이름
        public TextView mTxtName;

        //연인 유무
        public ImageView mImgHoney;

        //생일
        public TextView mTxtBirthday;

    }

    public FriendListViewAdapter(Context context, ArrayList<FriendListViewItem> arrays){

        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.FriendArrayList = arrays;

        mDBHelper = new DBHelper(context, DBConstants.DATABASE_NAME);
    }

    @Override
    public int getCount() {
        return FriendArrayList.size();
    }

    @Override
    public FriendListViewItem getItem(int position) {
        return FriendArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setSelectedIndex(int index) {
        mSelectedPosition = index;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            viewHolder = new ListViewHolder();
            v = inflater.inflate(R.layout.listview_item_friend, null);

//            viewHolder.mTxtId = (TextView) v.findViewById(R.id.item_txt_id);
            viewHolder.mTxtName = (TextView) v.findViewById(R.id.item_txt_name);
            viewHolder.mImgHoney = (ImageView) v.findViewById(R.id.item_img_honey);
            viewHolder.mTxtBirthday = (TextView)v.findViewById(R.id.item_txt_birthday);

            v.setTag(viewHolder);

        }else {
            viewHolder = (ListViewHolder)v.getTag();
        }

        if(mSelectedPosition == position)
        {
//            viewHolder.mTxtId.setTextColor(Color.parseColor("#ff0000"));
            viewHolder.mTxtName.setTextColor(Color.parseColor("#ff0000"));
            viewHolder.mTxtBirthday.setTextColor(Color.parseColor("#ff0000"));
        }
        else
        {
  //          viewHolder.mTxtId.setTextColor(Color.parseColor("#000000"));
            viewHolder.mTxtName.setTextColor(Color.parseColor("#000000"));
            viewHolder.mTxtBirthday.setTextColor(Color.parseColor("#000000"));
        }

        /*
        viewHolder.mTxtId.setTag(position);
        viewHolder.mTxtId.setText(getItem(position).getId());
        */

        viewHolder.mTxtName.setTag(position);
        viewHolder.mTxtName.setText(getItem(position).getName());

        viewHolder.mTxtBirthday.setTag(position);
        viewHolder.mTxtBirthday.setText(getItem(position).getBirthday());

        if(FriendArrayList.get(position).getIsHoney().equals("T"))
            viewHolder.mImgHoney.setVisibility(View.VISIBLE);
        else
            viewHolder.mImgHoney.setVisibility(View.INVISIBLE);

        return v;
    }

    /*
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        int nPos = (int) buttonView.getTag();

        AlarmInfoListViewItem getItem = AlarmInfoArrayList.get(nPos);

        //어댑터와 연결된 토글 상태도 변경해줘야 한다.
        AlarmInfoArrayList.get(nPos).setAlarm(isChecked);

        String curTitle, curHour, curMinute;

        curTitle = getItem.getTitle();
        curHour = getItem.getHour();
        curMinute = getItem.getMinute();

        List<AlarmInfo> AlarmInfoList = mDBHelper.getAlarmInfoList();

        for(int i=0; i<AlarmInfoList.size(); i++) {

            AlarmInfo item = AlarmInfoList.get(i);

            if(item.getTitle().equals(curTitle) &&
                    item.getHour().equals(curHour) &&
                    item.getMinute().equals(curMinute))
            {
                String bAlarm = "";
                if(isChecked == true)
                    bAlarm = "T";
                else
                    bAlarm = "F";

                mDBHelper.deleteAlarmInfo(item.getTitle(), item.getHour(), item.getMinute());

                if(mDBHelper.insertAlarmInfo(item.getTitle(), item.getContents(), item.getHour(), item.getMinute(), item.getAlarmId(), bAlarm))
                {
                    if(isChecked == false)
                    {
                        int AlarmId = Integer.parseInt(item.getAlarmId());
                        AlarmUtil.unregisterAlarm(mContext, AlarmId);
                    }
                    Toast.makeText(mContext, "알람 설정 변경", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    */
}
