package com.example.hyenee.listviewpractice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//sqlLite DB
public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    static boolean mPrint_Log = false;

    public DBHelper(Context context, String name) {
        super(context, name, null, DBConstants.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "DB onCreate!\n");

        db.execSQL("CREATE TABLE " + DBConstants.TasteTable.TblNAME + " ( "
                + DBConstants.TasteTable.Column.ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBConstants.TasteTable.Column.NAME + " text, "
                + DBConstants.TasteTable.Column.PHONE + " text,"
                + DBConstants.TasteTable.Column.IS_HONEY + " text,"
                + DBConstants.TasteTable.Column.BIRTHDAY + " text, "
                + DBConstants.TasteTable.Column.ALARM_DATE + " text,"
                + DBConstants.TasteTable.Column.ALARM_HOUR + " text,"
                + DBConstants.TasteTable.Column.ALARM_MINUTE + " text,"
                + DBConstants.TasteTable.Column.ALARM_ID + " text,"
                + DBConstants.TasteTable.Column.GAVEN_GIFT + " text,"
                + DBConstants.TasteTable.Column.TAKEN_GIFT + " text,"
                + DBConstants.TasteTable.Column.STYLE + " text,"
                + DBConstants.TasteTable.Column.LIKE + " text,"
                + DBConstants.TasteTable.Column.DISLIKE + " text,"
                + DBConstants.TasteTable.Column.WILL_GIVE_GIFT + " text,"
                + DBConstants.TasteTable.Column.ALARM_MESSAGE + " text) ");
  }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TasteTable.TblNAME);

        onCreate(db);
    }

    public int insertDB(
            String name,
            String phone,
            String is_honey,
            String birthday,
            String alarm_date,
            String alarm_hour,
            String alarm_minute,
            String alarm_id,
            String gaven_gift,
            String taken_gift,
            String style,
            String like,
            String dislike,
            String will_give_gift,
            String alarm_msg
    )
    {
        long row_id = -1;

        SQLiteDatabase database = getWritableDatabase();

        String columns[] = { DBConstants.TasteTable.Column.ID,
                DBConstants.TasteTable.Column.NAME,
                DBConstants.TasteTable.Column.PHONE,
                DBConstants.TasteTable.Column.IS_HONEY,
                DBConstants.TasteTable.Column.BIRTHDAY,
                DBConstants.TasteTable.Column.ALARM_DATE,
                DBConstants.TasteTable.Column.ALARM_HOUR,
                DBConstants.TasteTable.Column.ALARM_MINUTE,
                DBConstants.TasteTable.Column.ALARM_ID,
                DBConstants.TasteTable.Column.GAVEN_GIFT,
                DBConstants.TasteTable.Column.TAKEN_GIFT,
                DBConstants.TasteTable.Column.STYLE,
                DBConstants.TasteTable.Column.LIKE,
                DBConstants.TasteTable.Column.DISLIKE,
                DBConstants.TasteTable.Column.WILL_GIVE_GIFT,
                DBConstants.TasteTable.Column.ALARM_MESSAGE
        };

        String order_by = DBConstants.TasteTable.Column.ID;

        Cursor cursor = null;

        cursor = database.query(DBConstants.TasteTable.TblNAME, columns,
                null, null, null, null, order_by, null);

        cursor.moveToFirst();

        String table = DBConstants.TasteTable.TblNAME;

        for(int i = 0 ; i < cursor.getCount() ; i++){

            int IdColumn = cursor.getColumnIndex(DBConstants.TasteTable.Column.ID);
            int NamdColumn = cursor.getColumnIndex(DBConstants.TasteTable.Column.NAME);

            String whereClause = DBConstants.TasteTable.Column.ID + " = ? ";
            String[] whereArgs = new String[] { cursor.getString(IdColumn) };

            //Name이 일치할 경우 DB추가 실패 처리
            if(name.equals(cursor.getString(NamdColumn)))
            {
                return 100;
            }

            cursor.moveToNext();
        }

        ContentValues values = new ContentValues();
        values.put(DBConstants.TasteTable.Column.NAME, name);
        values.put(DBConstants.TasteTable.Column.PHONE, phone);
        values.put(DBConstants.TasteTable.Column.IS_HONEY, is_honey);
        values.put(DBConstants.TasteTable.Column.BIRTHDAY, birthday);
        values.put(DBConstants.TasteTable.Column.ALARM_DATE, alarm_date);
        values.put(DBConstants.TasteTable.Column.ALARM_HOUR, alarm_hour);
        values.put(DBConstants.TasteTable.Column.ALARM_MINUTE, alarm_minute);
        values.put(DBConstants.TasteTable.Column.ALARM_ID, alarm_id);
        values.put(DBConstants.TasteTable.Column.GAVEN_GIFT, gaven_gift);
        values.put(DBConstants.TasteTable.Column.TAKEN_GIFT, taken_gift);
        values.put(DBConstants.TasteTable.Column.STYLE, style);
        values.put(DBConstants.TasteTable.Column.LIKE, like);
        values.put(DBConstants.TasteTable.Column.DISLIKE, dislike);
        values.put(DBConstants.TasteTable.Column.WILL_GIVE_GIFT, will_give_gift);
        values.put(DBConstants.TasteTable.Column.ALARM_MESSAGE, alarm_msg);
        row_id = database.insert(DBConstants.TasteTable.TblNAME, null,
                values);

        cursor.close();
        database.close();

        if (row_id == -1)
            return 0;

        return 1;
    }

    //DB 전체 삭제
    public void deleteDB() {

        SQLiteDatabase database = getWritableDatabase();

        database.delete(DBConstants.TasteTable.TblNAME, null, null);

        database.close();
    }

    //name 기준으로 컬럼 삭제
    public boolean deleteDB(String name/*, String phone*/) {

        SQLiteDatabase database = getWritableDatabase();

        String table = DBConstants.TasteTable.TblNAME;

        /*
        String whereClause = DBConstants.TasteTable.Column.NAME + " = ? " + "AND " + DBConstants.TasteTable.Column.PHONE + " = ? ";
        String[] whereArgs = new String[] { name, phone};
        */
        String whereClause = DBConstants.TasteTable.Column.NAME + " = ? ";
        String[] whereArgs = new String[] { name };

        if (database.delete(table, whereClause, whereArgs) == 1) {
            database.close();
            return true;
        } else {
            database.close();
            return false;
        }
    }

    public List<FriendInfo> getFriendList() {

        String columns[] = { DBConstants.TasteTable.Column.ID,
                DBConstants.TasteTable.Column.NAME,
                DBConstants.TasteTable.Column.PHONE,
                DBConstants.TasteTable.Column.IS_HONEY,
                DBConstants.TasteTable.Column.BIRTHDAY,
                DBConstants.TasteTable.Column.ALARM_DATE,
                DBConstants.TasteTable.Column.ALARM_HOUR,
                DBConstants.TasteTable.Column.ALARM_MINUTE,
                DBConstants.TasteTable.Column.ALARM_ID,
                DBConstants.TasteTable.Column.GAVEN_GIFT,
                DBConstants.TasteTable.Column.TAKEN_GIFT,
                DBConstants.TasteTable.Column.STYLE,
                DBConstants.TasteTable.Column.LIKE,
                DBConstants.TasteTable.Column.DISLIKE,
                DBConstants.TasteTable.Column.WILL_GIVE_GIFT,
                DBConstants.TasteTable.Column.ALARM_MESSAGE
        };

        String order_by = DBConstants.TasteTable.Column.ID;

        Cursor cursor = null;

        SQLiteDatabase database = getWritableDatabase();

        List<FriendInfo> TasteList = new ArrayList<FriendInfo>();

        try {
            cursor = database.query(DBConstants.TasteTable.TblNAME, columns,
                    "", new String[] {}, null, null, order_by, null);

            int IdColumn = cursor.getColumnIndex(DBConstants.TasteTable.Column.ID);
            int NameColumn = cursor.getColumnIndex(DBConstants.TasteTable.Column.NAME);
            int PhoneColumn = cursor.getColumnIndex(DBConstants.TasteTable.Column.PHONE);
            int IsHoneyColumn = cursor.getColumnIndex(DBConstants.TasteTable.Column.IS_HONEY);
            int BirthdayColumn = cursor.getColumnIndex(DBConstants.TasteTable.Column.BIRTHDAY);
            int AlarmDateColumn = cursor.getColumnIndex(DBConstants.TasteTable.Column.ALARM_DATE);
            int AlarmHourColumn = cursor.getColumnIndex(DBConstants.TasteTable.Column.ALARM_HOUR);
            int AlarmMinuteColumn = cursor.getColumnIndex(DBConstants.TasteTable.Column.ALARM_MINUTE);
            int AlarmIdColumn = cursor.getColumnIndex(DBConstants.TasteTable.Column.ALARM_ID);

            int GavenGiftColumn = cursor.getColumnIndex(DBConstants.TasteTable.Column.GAVEN_GIFT);
            int TakenGiftColumn = cursor.getColumnIndex(DBConstants.TasteTable.Column.TAKEN_GIFT);
            int StyleColumn = cursor.getColumnIndex(DBConstants.TasteTable.Column.STYLE);
            int LikeColumn = cursor.getColumnIndex(DBConstants.TasteTable.Column.LIKE);
            int DislikeColumn = cursor.getColumnIndex(DBConstants.TasteTable.Column.DISLIKE);
            int WillGiveGiftColumn = cursor.getColumnIndex(DBConstants.TasteTable.Column.WILL_GIVE_GIFT);

            int AlarmMsgColumn = cursor.getColumnIndex(DBConstants.TasteTable.Column.ALARM_MESSAGE);

            while (cursor.moveToNext()) {
                FriendInfo item = new FriendInfo();

                item.setId(cursor.getString(IdColumn));
                item.setName(cursor.getString(NameColumn));
                item.setPhone(cursor.getString(PhoneColumn));
                item.setIsHoney(cursor.getString(IsHoneyColumn));

                item.setBirthday(cursor.getString(BirthdayColumn));
                item.setAlarmDate(cursor.getString(AlarmDateColumn));
                item.setAlarmHour(cursor.getString(AlarmHourColumn));
                item.setAlarmMinute(cursor.getString(AlarmMinuteColumn));
                item.setAlarmId(cursor.getString(AlarmIdColumn));

                item.setGavenGift(cursor.getString(GavenGiftColumn));
                item.setTakenGift(cursor.getString(TakenGiftColumn));
                item.setStyle(cursor.getString(StyleColumn));
                item.setLike(cursor.getString(LikeColumn));
                item.setDislike(cursor.getString(DislikeColumn));
                item.setWillGiveGift(cursor.getString(WillGiveGiftColumn));
                item.setAlarmMsg(cursor.getString(AlarmMsgColumn));

                TasteList.add(item);
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }

        return TasteList;
    }

    public int getLastAlarmId() {

        int AlarmId = 0;

        String columns[] = { DBConstants.TasteTable.Column.ID,
                DBConstants.TasteTable.Column.NAME,
                DBConstants.TasteTable.Column.PHONE,
                DBConstants.TasteTable.Column.IS_HONEY,
                DBConstants.TasteTable.Column.BIRTHDAY,
                DBConstants.TasteTable.Column.ALARM_DATE,
                DBConstants.TasteTable.Column.ALARM_HOUR,
                DBConstants.TasteTable.Column.ALARM_MINUTE,
                DBConstants.TasteTable.Column.ALARM_ID,
                DBConstants.TasteTable.Column.GAVEN_GIFT,
                DBConstants.TasteTable.Column.TAKEN_GIFT,
                DBConstants.TasteTable.Column.STYLE,
                DBConstants.TasteTable.Column.LIKE,
                DBConstants.TasteTable.Column.DISLIKE,
                DBConstants.TasteTable.Column.WILL_GIVE_GIFT,
                DBConstants.TasteTable.Column.ALARM_MESSAGE
        };

        String order_by = DBConstants.TasteTable.Column.ID;

        Cursor cursor = null;

        SQLiteDatabase database = getWritableDatabase();

        try {
            cursor = database.query(DBConstants.TasteTable.TblNAME, columns,
                    "", new String[] {}, null, null, order_by, null);

            int alarmIdColumn = cursor.getColumnIndex(DBConstants.TasteTable.Column.ALARM_ID);

            while (cursor.moveToNext()) {

                AlarmId = Integer.parseInt(cursor.getString(alarmIdColumn));
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }

        return AlarmId;
    }
}