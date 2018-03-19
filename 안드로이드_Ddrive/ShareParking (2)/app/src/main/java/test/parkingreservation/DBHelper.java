package test.parkingreservation;

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

        //주차장 정보 테이블 생성
        db.execSQL("CREATE TABLE " + DBConstants.ParkingInfoTable.TblNAME + " ( "
                + DBConstants.ParkingInfoTable.Column.ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBConstants.ParkingInfoTable.Column.Name + " text,"
                + DBConstants.ParkingInfoTable.Column.Addr + " text,"
                + DBConstants.ParkingInfoTable.Column.TotalSpace + " text,"
                + DBConstants.ParkingInfoTable.Column.EmptySpace + " text,"
                + DBConstants.ParkingInfoTable.Column.Image + " blob) ");

        //사용자 정보 테이블 생성(모드 설정)
        db.execSQL("CREATE TABLE " + DBConstants.UserInfoTable.TblNAME + " ( "
                + DBConstants.UserInfoTable.Column.ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBConstants.UserInfoTable.Column.UserID + " text,"
                + DBConstants.UserInfoTable.Column.Password + " text,"
                + DBConstants.UserInfoTable.Column.Mode + " text) ");
  }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.ParkingInfoTable.TblNAME);

        onCreate(db);
    }

    //유저 정보 추가
    public boolean insertUserInfo(
            String userid,
            String password,
            String mode
    )
    {
        long row_id = -1;

        Log.d(TAG, "insertUserInfo: Start!");
        SQLiteDatabase database = getWritableDatabase();

        String columns[] = { DBConstants.UserInfoTable.Column.ID,
                DBConstants.UserInfoTable.Column.UserID,
                DBConstants.UserInfoTable.Column.Password,
                DBConstants.UserInfoTable.Column.Mode
        };

        String order_by = DBConstants.UserInfoTable.Column.ID;

        Cursor cursor = null;

        cursor = database.query(DBConstants.UserInfoTable.TblNAME, columns,
                null, null, null, null, order_by, null);

        cursor.moveToFirst();

        String table = DBConstants.UserInfoTable.TblNAME;

        for(int i = 0 ; i < cursor.getCount() ; i++){

            int idColumn = cursor.getColumnIndex(DBConstants.UserInfoTable.Column.ID);
            int UserIDColumn = cursor.getColumnIndex(DBConstants.UserInfoTable.Column.UserID);

            String whereClause = DBConstants.UserInfoTable.Column.ID + " = ? ";
            String[] whereArgs = new String[] { cursor.getString(idColumn) };

            Log.d(TAG,	"userid : "+ userid);
            Log.d(TAG,	"UserIDColumn : "+ cursor.getString(UserIDColumn));

            //userid가 일치할 경우 DB를 update한다.
            if(userid.equals(cursor.getString(UserIDColumn)))
            {
                Log.d(TAG, "insertUserInfo: Update!");

                ContentValues args = new ContentValues();
                args.put(DBConstants.UserInfoTable.Column.UserID, userid);
                args.put(DBConstants.UserInfoTable.Column.Password, password);
                args.put(DBConstants.UserInfoTable.Column.Mode, mode);
                database.update(table, args, whereClause, whereArgs);
                database.close();
                cursor.close();
                return true;
            }

            cursor.moveToNext();
        }

        ContentValues values = new ContentValues();
        values.put(DBConstants.UserInfoTable.Column.UserID, userid);
        values.put(DBConstants.UserInfoTable.Column.Password, password);
        values.put(DBConstants.UserInfoTable.Column.Mode, mode);
        row_id = database.insert(DBConstants.UserInfoTable.TblNAME, null,
                values);

        cursor.close();
        database.close();

        Log.d("UserInfoTable>>", "row_id : " + row_id );

        if (row_id == -1)
            return false;

        return true;
    }

    //주차장 정보 추가
    public boolean insertParkingInfo(
            String name,
            String addr,
            String totalspace,
            String emptyspace,
            byte[] image
    )
    {
        long row_id = -1;

        Log.d(TAG, "insertParkingInfo: Start!");
        SQLiteDatabase database = getWritableDatabase();

        String columns[] = { DBConstants.ParkingInfoTable.Column.ID,
                DBConstants.ParkingInfoTable.Column.Name,
                DBConstants.ParkingInfoTable.Column.Addr,
                DBConstants.ParkingInfoTable.Column.TotalSpace,
                DBConstants.ParkingInfoTable.Column.EmptySpace,
                DBConstants.ParkingInfoTable.Column.Image
        };

        String order_by = DBConstants.ParkingInfoTable.Column.ID;

        Cursor cursor = null;

        cursor = database.query(DBConstants.ParkingInfoTable.TblNAME, columns,
                null, null, null, null, order_by, null);

        cursor.moveToFirst();

        String table = DBConstants.ParkingInfoTable.TblNAME;

        for(int i = 0 ; i < cursor.getCount() ; i++){

            int idColumn = cursor.getColumnIndex(DBConstants.ParkingInfoTable.Column.ID);
            int NameColumn = cursor.getColumnIndex(DBConstants.ParkingInfoTable.Column.Name);

            String whereClause = DBConstants.ParkingInfoTable.Column.ID + " = ? ";
            String[] whereArgs = new String[] { cursor.getString(idColumn) };

            Log.d(TAG,	"name : "+ name);
            Log.d(TAG,	"NameColumn : "+ cursor.getString(NameColumn));

            //name이 일치할 경우 DB를 update한다.
            if(name.equals(cursor.getString(NameColumn)))
            {
                Log.d(TAG, "insertParkingInfo: Update!");

                ContentValues args = new ContentValues();
                args.put(DBConstants.ParkingInfoTable.Column.Name, name);
                args.put(DBConstants.ParkingInfoTable.Column.Addr, addr);
                args.put(DBConstants.ParkingInfoTable.Column.TotalSpace, totalspace);
                args.put(DBConstants.ParkingInfoTable.Column.EmptySpace, emptyspace);
                args.put(DBConstants.ParkingInfoTable.Column.Image, image);
                database.update(table, args, whereClause, whereArgs);
                database.close();
                cursor.close();
                return true;
            }

            cursor.moveToNext();
        }

        ContentValues values = new ContentValues();
        values.put(DBConstants.ParkingInfoTable.Column.Name, name);
        values.put(DBConstants.ParkingInfoTable.Column.Addr, addr);
        values.put(DBConstants.ParkingInfoTable.Column.TotalSpace, totalspace);
        values.put(DBConstants.ParkingInfoTable.Column.EmptySpace, emptyspace);
        values.put(DBConstants.ParkingInfoTable.Column.Image, image);
        row_id = database.insert(DBConstants.ParkingInfoTable.TblNAME, null,
                values);

        cursor.close();
        database.close();

        Log.d("ParkingInfoTable>>", "row_id : " + row_id );

        if (row_id == -1)
            return false;

        return true;
    }

    /*
    //DB에 저장된 사진을 Title-Description 기준으로 가져온다.
    public byte[] getImage(String title, String detail, String qWhere, String[] qWhereArgs)
    {
        Log.d(TAG, "DB getImage!\n");

        String columns[] = { DBConstants.ParkingInfoTable.Column.ID,
                DBConstants.ParkingInfoTable.Column.UserID,
                DBConstants.ParkingInfoTable.Column.Password,
                DBConstants.ParkingInfoTable.Column.Detail,
                DBConstants.ParkingInfoTable.Column.Brand,
                DBConstants.ParkingInfoTable.Column.Image
        };

        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = null;
        byte[] imgage = null;

        try {
            String order_by = DBConstants.ParkingInfoTable.Column.ID;

            cursor = database.query(DBConstants.ParkingInfoTable.TblNAME, columns,
                    null, null, null, null, order_by, null);

            cursor.moveToFirst();

            int idColumn = cursor.getColumnIndex(DBConstants.ParkingInfoTable.Column.ID);
            int UserIDColumn = cursor.getColumnIndex(DBConstants.ParkingInfoTable.Column.UserID);
            int PasswordColumn = cursor.getColumnIndex(DBConstants.ParkingInfoTable.Column.Password);
            int DetailColumn = cursor.getColumnIndex(DBConstants.ParkingInfoTable.Column.Detail);
            int BrandColumn = cursor.getColumnIndex(DBConstants.ParkingInfoTable.Column.Brand);
            int ImageColumn = cursor.getColumnIndex(DBConstants.ParkingInfoTable.Column.Image);

            for(int i = 0 ; i < cursor.getCount() ; i++){

                String whereClause = DBConstants.ParkingInfoTable.Column.ID + " = ? ";
                String[] whereArgs = new String[] { cursor.getString(idColumn) };

                //Title / Detail이 일치할 경우 DB에서 이미지를 얻어온다.
                if(title.equals(cursor.getString(TitleColumn)) && detail.equals(cursor.getString(DetailColumn)))
                {
                    imgage =  cursor.getBlob(ImageColumn);

                    cursor.close();
                    database.close();
                    return imgage;
                }

                cursor.moveToNext();
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }

        return imgage;
    }
    */

    //사용자 정보 DB 전체 삭제
    public void deleteUserInfo() {

        SQLiteDatabase database = getWritableDatabase();

        database.delete(DBConstants.ParkingInfoTable.TblNAME, null, null);

        database.close();
    }

    /*
    //Title 기준으로 컬럼 삭제
    public boolean deleteProductFromTitle(String title) {

        SQLiteDatabase database = getWritableDatabase();

        String table = DBConstants.ParkingInfoTable.TblNAME;
        String whereClause = DBConstants.ParkingInfoTable.Column.Title + " = ? ";
        String[] whereArgs = new String[] { title };

        if (database.delete(table, whereClause, whereArgs) == 1) {
            database.close();
            return true;
        } else {
            database.close();
            return false;
        }
    }
    */
    public List<UserInfo> getUserInfoList(String qWhere, String[] qWhereArgs) {

        Log.d(TAG, "DB getUserInfoList!\n");

        String columns[] = { DBConstants.UserInfoTable.Column.ID,
                DBConstants.UserInfoTable.Column.UserID,
                DBConstants.UserInfoTable.Column.Password,
                DBConstants.UserInfoTable.Column.Mode
        };

        String order_by = DBConstants.UserInfoTable.Column.ID;

        Cursor cursor = null;

        SQLiteDatabase database = getWritableDatabase();

        List<UserInfo> UserInfoList = new ArrayList<UserInfo>();

        try {
            cursor = database.query(DBConstants.UserInfoTable.TblNAME, columns,
                    qWhere, qWhereArgs, null, null, order_by, null);

            int idColumn = cursor.getColumnIndex(DBConstants.UserInfoTable.Column.ID);
            int UserIdColumn = cursor.getColumnIndex(DBConstants.UserInfoTable.Column.UserID);
            int PasswordColumn = cursor.getColumnIndex(DBConstants.UserInfoTable.Column.Password);
            int ModeColumn = cursor.getColumnIndex(DBConstants.UserInfoTable.Column.Mode);

            if(cursor!=null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {
                    UserInfo item = new UserInfo();

                    item.setId(cursor.getString(idColumn));
                    item.setUserid(cursor.getString(UserIdColumn));
                    item.setPassword(cursor.getString(PasswordColumn));
                    item.setMode(cursor.getString(ModeColumn));

                    UserInfoList.add(item);
                }
            }

        }
        catch (Exception e)
        {
            Log.e("DBHelper", e.getMessage());
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }

        return UserInfoList;
    }

    public List<ParkingInfo> getParkingInfoList(String qWhere, String[] qWhereArgs) {

        Log.d(TAG, "DB getParkingInfoList!\n");

        String columns[] = { DBConstants.ParkingInfoTable.Column.ID,
                DBConstants.ParkingInfoTable.Column.Name,
                DBConstants.ParkingInfoTable.Column.Addr,
                DBConstants.ParkingInfoTable.Column.TotalSpace,
                DBConstants.ParkingInfoTable.Column.EmptySpace,
                DBConstants.ParkingInfoTable.Column.Image
        };

        String order_by = DBConstants.ParkingInfoTable.Column.ID;

        Cursor cursor = null;

        SQLiteDatabase database = getWritableDatabase();

        List<ParkingInfo> ParkingInfoList = new ArrayList<ParkingInfo>();

        try {
            cursor = database.query(DBConstants.ParkingInfoTable.TblNAME, columns,
                    qWhere, qWhereArgs, null, null, order_by, null);

            int idColumn = cursor.getColumnIndex(DBConstants.ParkingInfoTable.Column.ID);
            int NameColumn = cursor.getColumnIndex(DBConstants.ParkingInfoTable.Column.Name);
            int AddrColumn = cursor.getColumnIndex(DBConstants.ParkingInfoTable.Column.Addr);
            int TotalSpaceColumn = cursor.getColumnIndex(DBConstants.ParkingInfoTable.Column.TotalSpace);
            int EmptySpaceColumn = cursor.getColumnIndex(DBConstants.ParkingInfoTable.Column.EmptySpace);
            int ImageColumn = cursor.getColumnIndex(DBConstants.ParkingInfoTable.Column.Image);

            if(cursor!=null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {
                    ParkingInfo item = new ParkingInfo();

                    item.setId(cursor.getString(idColumn));
                    item.setName(cursor.getString(NameColumn));
                    item.setAddr(cursor.getString(AddrColumn));
                    item.setTotalspace(cursor.getString(TotalSpaceColumn));
                    item.setEmptyspace(cursor.getString(EmptySpaceColumn));
                    item.setImage(cursor.getBlob(ImageColumn));

                    ParkingInfoList.add(item);
                }
            }

        }
        catch (Exception e)
        {
            Log.e("DBHelper", e.getMessage());
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }

        return ParkingInfoList;
    }

    /*
    //Title으로 검색하여 DB에 저장된 값을 얻어온다.
    public List<ParkingInfo> getProductInfoList(String title, String qWhere, String[] qWhereArgs) {

        Log.d(TAG, "DB getProductInfoList!\n");

        String columns[] = { DBConstants.ParkingInfoTable.Column.ID,
                DBConstants.ParkingInfoTable.Column.Title,
                DBConstants.ParkingInfoTable.Column.Detail,
                DBConstants.ParkingInfoTable.Column.Brand,
                DBConstants.ParkingInfoTable.Column.Image
        };

        String order_by = DBConstants.ParkingInfoTable.Column.ID;

        Cursor cursor = null;

        SQLiteDatabase database = getWritableDatabase();

        List<ParkingInfo> ProductInfoList = new ArrayList<ParkingInfo>();

        try {
            cursor = database.query(DBConstants.ParkingInfoTable.TblNAME, columns,
                    qWhere, qWhereArgs, null, null, order_by, null);

            cursor.moveToFirst();

            int idColumn = cursor.getColumnIndex(DBConstants.ParkingInfoTable.Column.ID);
            int TitleColumn = cursor.getColumnIndex(DBConstants.ParkingInfoTable.Column.Title);
            int DetailColumn = cursor.getColumnIndex(DBConstants.ParkingInfoTable.Column.Detail);
            int BrandColumn = cursor.getColumnIndex(DBConstants.ParkingInfoTable.Column.Brand);
            int ImageColumn = cursor.getColumnIndex(DBConstants.ParkingInfoTable.Column.Image);

            for(int i = 0 ; i < cursor.getCount() ; i++){

                String whereClause = DBConstants.ParkingInfoTable.Column.ID + " = ? ";
                String[] whereArgs = new String[] { cursor.getString(idColumn) };

                //Title이 일치할 경우 DB에서 이미지를 얻어온다.
                if(title.equals(cursor.getString(TitleColumn)))
                {
                    ParkingInfo item = new ParkingInfo();

                    item.setImage(cursor.getBlob(ImageColumn));
                    item.setTitle(cursor.getString(TitleColumn));
                    item.setDetail(cursor.getString(DetailColumn));
                    item.setBrand(cursor.getString(BrandColumn));

                    ProductInfoList.add(item);
                }

                cursor.moveToNext();
            }


        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }

        return ProductInfoList;
    }
    */
}