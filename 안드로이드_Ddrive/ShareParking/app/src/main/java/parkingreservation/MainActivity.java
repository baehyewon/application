package test.parkingreservation;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.BaseColumns;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import parkingreservation.DBHelper;
import parkingreservation.Define;
import parkingreservation.MainPopupActivity;
import parkingreservation.ParkingInfo;
import parkingreservation.UserInfo;
import test.parkingreservation.R;

public class MainActivity extends FragmentActivity implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private DBHelper mDBHelper;

    private Button btnMenu, btnModify1, btnModify2, btnModify3, btnModify4, btnModify5;
    private TextView txtInfo, txtParkingName, txtParkingAddr, txtParkingSpace, txtAlarm;
    private ImageView imgParking;
    private EditText edtParkingSpace1, edtParkingSpace2, edtParkingSpace3, edtParkingSpace4, edtParkingSpace5;

    private LocationManager locationManager;

    private LatLng[] AlarmPlaceLatLng = null;       //푸쉬 알람이 울릴 위치 정보(서울여대 정문/후문)
    private LatLng   CurrentLatLng = null;

    private LatLng[] ParkingPlaceLatLng = null;          //실제 주차장 위치 정보
    private Marker[] ParkingPlaceMarker = null;          //주차장 마커

    private int DistanceFront = 0;     //서울여대 정문까지의 거리
    private int DistanceRear = 0;      //서울여대 후문까지의 거리
    private boolean EnableGps = false;

    private String CurMode;    //현재 모드(사용자 / 관리자)

    private FrameLayout mMainViewer;

    private LayoutInflater mInflater;

    private LinearLayout mUserLayout, mDetailUserLayout, mManagerLayout, mAlarmLayout;

    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;

    private final int VIEW_MODE_USER             = 0;
    private final int VIEW_MODE_USER_DETAIL     = 1;
    private final int VIEW_MODE_MANAGER          = 2;
    private final int VIEW_MODE_ALARM           = 3;

    private int CurViewMode = VIEW_MODE_USER;

    private boolean mIsInitialized = false;

    private Marker CurMarker = null;

    private String strAlarmMsg = "";

    private Timer mTimer;
    private Handler mHandler;
    private String mStrAddr = "";

    private boolean bAlarm = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelper = new DBHelper(this, DBConstants.DATABASE_NAME);

        List<UserInfo> UserInfoList = mDBHelper.getUserInfoList("", new String[] {});

        if(UserInfoList.size() == 0) {
            //사용자 모드 계정 추가
            mDBHelper.insertUserInfo("aaa", "1", Define.USER_MODE);

            //관리자 모드 계정 추가
            mDBHelper.insertUserInfo("bbb", "ab1234", Define.MANAGER_MODE);
        }
        List<ParkingInfo> ParkingInfoList = mDBHelper.getParkingInfoList("", new String[] {});

        if(ParkingInfoList.size() == 0) {
            for (int i = 0; i < Define.PARKING_PLACE_NAME.length; i++)
                mDBHelper.insertParkingInfo(Define.PARKING_PLACE_NAME[i], Define.PARKING_PLACE_ADDR[i], Integer.toString(Define.PARKING_PLACE_TOTAL_SPACE[i]), Integer.toString(Define.PARKING_PLACE_EMPTY_SPACE[i]), null);
        }

        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.am_map);
//        mMapFragment.getMapAsync(this);

        mMainViewer = (FrameLayout) findViewById(R.id.am_framelayout);

        mInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //일반 사용자 모드 프레임 레이아웃
        mUserLayout = (LinearLayout) mInflater.inflate(R.layout.frame_view_user, null);

        //일반 사용자 디테일 모드 프레임 레이아웃(마커 클릭시)
        mDetailUserLayout = (LinearLayout) mInflater.inflate(R.layout.frame_view_user_detail, null);

        //매니저 모드 프레임 레이아웃
        mManagerLayout = (LinearLayout) mInflater.inflate(R.layout.frame_view_manager, null);

        //서울여대 정문/후문 근처 왔을경우 알람메시지 레이아웃
        mAlarmLayout = (LinearLayout) mInflater.inflate(R.layout.frame_view_alarm, null);

        btnMenu = (Button)findViewById(R.id.am_btn_menu);
        btnMenu.setOnClickListener(this);

        CurMode = Define.USER_MODE;

        AlarmPlaceLatLng = new LatLng[Define.ALARM_PLACE_NAME.length];
        ParkingPlaceLatLng = new LatLng[Define.PARKING_PLACE_ADDR.length];
        ParkingPlaceMarker = new Marker[Define.PARKING_PLACE_NAME.length];

        // Location 제공자에서 정보를 얻어오기(GPS)
        // 1. Location을 사용하기 위한 권한을 얻어와야한다 AndroidManifest.xml
        //     ACCESS_FINE_LOCATION : NETWORK_PROVIDER, GPS_PROVIDER
        //     ACCESS_COARSE_LOCATION : NETWORK_PROVIDER
        // 2. LocationManager 를 통해서 원하는 제공자의 리스너 등록
        // 3. GPS 는 에뮬레이터에서는 기본적으로 동작하지 않는다
        // 4. 실내에서는 GPS_PROVIDER 를 요청해도 응답이 없다.  특별한 처리를 안하면 아무리 시간이 지나도
        //    응답이 없다.
        //    해결방법은
        //     ① 타이머를 설정하여 GPS_PROVIDER 에서 일정시간 응답이 없는 경우 NETWORK_PROVIDER로 전환
        //     ② 혹은, 둘다 한꺼번헤 호출하여 들어오는 값을 사용하는 방식.

        // LocationManager 객체를 얻어온다
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                100, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                mLocationListener);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                100, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                mLocationListener);

        CurViewMode = VIEW_MODE_USER;
        setViewMode(CurViewMode);

        MapReadyThread threadMap = new MapReadyThread();
        threadMap.setDaemon(true);
        threadMap.start();

//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivityForResult(intent, Define.REQ_INTENT_LOGIN);


//        txtGps = (TextView) findViewById(R.id.am_txt_gps);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setViewMode(int mode)
    {
        List<ParkingInfo> ParkingInfoList;
        String strName, strAddr, strTotalSpace, strEmptySpace;

        switch (mode)
        {
            case VIEW_MODE_USER:
                mMainViewer.removeAllViews();
                mMainViewer.addView(mUserLayout);

                txtInfo = (TextView)findViewById(R.id.fvu_txt_info);
                break;

            case VIEW_MODE_USER_DETAIL:
                mMainViewer.removeAllViews();
                mMainViewer.addView(mDetailUserLayout);

                txtParkingName = (TextView)findViewById(R.id.fvud_txt_name);
                txtParkingAddr = (TextView)findViewById(R.id.fvud_txt_addr);
                txtParkingSpace= (TextView)findViewById(R.id.fvud_txt_space);
                imgParking = (ImageView)findViewById(R.id.fvud_img_parking);

                txtParkingName.setTextSize(15);
                txtParkingAddr.setTextSize(15);
                txtParkingSpace.setTextSize(15);

                ParkingInfoList = mDBHelper.getParkingInfoList("", new String[] {});

                for(int i=0; i<ParkingInfoList.size(); i++)
                {
                    ParkingInfo item = ParkingInfoList.get(i);
                    strName = item.getName();
                    strAddr = new String("주소: "+ item.getAddr());
                    strTotalSpace = item.getTotalspace();
                    strEmptySpace = item.getEmptyspace();

                    String strSpace = "주차장 가용공간: " + strEmptySpace + "/" + strTotalSpace;

                    if(CurMarker != null)
                    {
                        //클릭된 마커의 타이틀과 DB의 주차장 이름이 같을 경우
                        if(CurMarker.getTitle().equals(strName))
                        {
                            txtParkingName.setText(strName);
                            txtParkingAddr.setText(strAddr);
                            txtParkingSpace.setText(strSpace);

                            //정문 주차장
                            if(strName.equals(Define.PARKING_PLACE_NAME[Define.PLACE_PARKING1]))
                                imgParking.setBackgroundResource(R.drawable.parking1);
                                //제2과학관
                            else if(strName.equals(Define.PARKING_PLACE_NAME[Define.PLACE_PARKING2]))
                                imgParking.setBackgroundResource(R.drawable.parking2);
                                //기숙사
                            else if(strName.equals(Define.PARKING_PLACE_NAME[Define.PLACE_PARKING3]))
                                imgParking.setBackgroundResource(R.drawable.parking3);
                                //행정관
                            else if(strName.equals(Define.PARKING_PLACE_NAME[Define.PLACE_PARKING4]))
                                imgParking.setBackgroundResource(R.drawable.parking4);
                                //도서관
                            else if(strName.equals(Define.PARKING_PLACE_NAME[Define.PLACE_PARKING5]))
                                imgParking.setBackgroundResource(R.drawable.parking5);

                            return;
                        }
                    }
                }
                break;

            case VIEW_MODE_MANAGER:
                mMainViewer.removeAllViews();
                mMainViewer.addView(mManagerLayout);

                edtParkingSpace1 = (EditText)findViewById(R.id.fvm_edt_space1);
                edtParkingSpace2 = (EditText)findViewById(R.id.fvm_edt_space2);
                edtParkingSpace3 = (EditText)findViewById(R.id.fvm_edt_space3);
                edtParkingSpace4 = (EditText)findViewById(R.id.fvm_edt_space4);
                edtParkingSpace5 = (EditText)findViewById(R.id.fvm_edt_space5);

                btnModify1 = (Button)findViewById(R.id.fvm_btn1);
                btnModify1.setOnClickListener(this);

                btnModify2 = (Button)findViewById(R.id.fvm_btn2);
                btnModify2.setOnClickListener(this);

                btnModify3 = (Button)findViewById(R.id.fvm_btn3);
                btnModify3.setOnClickListener(this);

                btnModify4 = (Button)findViewById(R.id.fvm_btn4);
                btnModify4.setOnClickListener(this);

                btnModify5 = (Button)findViewById(R.id.fvm_btn5);
                btnModify5.setOnClickListener(this);

                ParkingInfoList = mDBHelper.getParkingInfoList("", new String[] {});

                for(int i=0; i<ParkingInfoList.size(); i++)
                {
                    ParkingInfo item = ParkingInfoList.get(i);

                    strName = item.getName();
                    strEmptySpace = item.getEmptyspace();

                    if(strName .equals(Define.PARKING_PLACE_NAME[Define.PLACE_PARKING1]))
                    {
                        edtParkingSpace1.setText(strEmptySpace);
                    }
                    if(strName .equals(Define.PARKING_PLACE_NAME[Define.PLACE_PARKING2]))
                    {
                        edtParkingSpace2.setText(strEmptySpace);
                    }
                    if(strName .equals(Define.PARKING_PLACE_NAME[Define.PLACE_PARKING3]))
                    {
                        edtParkingSpace3.setText(strEmptySpace);
                    }
                    if(strName .equals(Define.PARKING_PLACE_NAME[Define.PLACE_PARKING4]))
                    {
                        edtParkingSpace4.setText(strEmptySpace);
                    }
                    if(strName .equals(Define.PARKING_PLACE_NAME[Define.PLACE_PARKING5]))
                    {
                        edtParkingSpace5.setText(strEmptySpace);
                    }
                }
                break;

            case VIEW_MODE_ALARM:
                mMainViewer.removeAllViews();
                mMainViewer.addView(mAlarmLayout);

                txtAlarm = (TextView)findViewById(R.id.fva_txt_alarm);
                txtAlarm.setText(strAlarmMsg);
                txtAlarm.setTextSize(18);
//                txtAlarm
                break;
        }
    }

    class MapReadyThread extends Thread
    {
        public void run()
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    mMapFragment.getMapAsync(MainActivity.this);
                }
            });
        }
    }

    public int getDistance(String StoreName, LatLng LatLng1, LatLng LatLng2) {

        String strDistance = StoreName + "까지 ";

        if(LatLng1 == null)
            return -1;

        if(LatLng2 == null)
            return -1;

        int distance = 0;
        Location locationA = new Location("A");
        locationA.setLatitude(LatLng1.latitude);
        locationA.setLongitude(LatLng1.longitude);
        Location locationB = new Location("B");
        locationB.setLatitude(LatLng2.latitude);
        locationB.setLongitude(LatLng2.longitude);
        distance = (int)locationA.distanceTo(locationB);

        strDistance += Integer.toString(distance) + "m";

        return distance;
    }

    public void convertAddrToGPS()
    {
        Geocoder mCoder = new Geocoder(this);
        Double Lat, Lon;
        List<Address> addrList = null;

        for(int i = 0; i< Define.ALARM_PLACE_ADDR.length; i++)
        {
            //주소값을 통하여 로케일을 받아온다
            addrList = null;
            try {
                addrList = mCoder.getFromLocationName(Define.ALARM_PLACE_ADDR[i], 1);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                finish();
                return;
            }

            if(addrList != null && addrList.size() > 0) {
                Lat = addrList.get(0).getLatitude();
                Lon = addrList.get(0).getLongitude();

                //해당 로케일로 좌표를 구성한다
                AlarmPlaceLatLng[i] = new LatLng(Lat, Lon);
            }
        }

        for(int i = 0; i< Define.PARKING_PLACE_ADDR.length; i++)
        {
            //주소값을 통하여 로케일을 받아온다
            addrList = null;
            try {
                addrList = mCoder.getFromLocationName(Define.PARKING_PLACE_ADDR[i], 1);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                finish();
                return;
            }

            if(addrList != null && addrList.size() > 0) {
                Lat = addrList.get(0).getLatitude();
                Lon = addrList.get(0).getLongitude();

                //해당 로케일로 좌표를 구성한다
                ParkingPlaceLatLng[i] = new LatLng(Lat, Lon);

                ParkingPlaceMarker[i] = mMap.addMarker(new MarkerOptions()
                        .position(ParkingPlaceLatLng[i])
                        .title(Define.PARKING_PLACE_NAME[i]));
            }
        }
    }

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.

            Geocoder mCoder = new Geocoder(MainActivity.this);
            List<Address> addrList = null;

            double longitude = location.getLongitude(); //경도
            double latitude = location.getLatitude();   //위도
            double altitude = location.getAltitude();   //고도
            float accuracy = location.getAccuracy();    //정확도
            String provider = location.getProvider();   //위치제공자

//            if (!mIsInitialized) {
            if(mMap != null)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18));
            //              mIsInitialized = true;
            //        }

            CurrentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

            if(latitude != 0 && longitude != 0) {
                try {
                    addrList = mCoder.getFromLocation(latitude, longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                if ( addrList != null && addrList.size() > 0 ) {
                    mStrAddr = addrList.get(0).getAddressLine(0);
                }
            }
            for (int i = 0; i < Define.ALARM_PLACE_NAME.length; i++) {

                if(i == Define.ALARM_PLACE_FRONT)
                    DistanceFront = getDistance(Define.ALARM_PLACE_NAME[i], AlarmPlaceLatLng[i], CurrentLatLng);
                else
                    DistanceRear = getDistance(Define.ALARM_PLACE_NAME[i], AlarmPlaceLatLng[i], CurrentLatLng);
            }

            Vibrator vibrator;
            /**
             * 테스트 코드
             *
             if(bAlarm == false) {
             CurViewMode = VIEW_MODE_ALARM;
             strAlarmMsg = "서울여대 정문근처에 도착하셨습니다!!";
             setViewMode(CurViewMode);
             vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
             vibrator.vibrate(2000);

             if ( mTimer != null ) {
             mTimer.cancel();
             mTimer = null;
             }

             mTimer = new Timer();

             if ( mHandler != null ) {
             mHandler.removeCallbacks(null);
             mHandler = null;
             }

             mHandler = new Handler();
             bAlarm = true;
             mTimer.schedule(
             new TimerTask(){

            @Override
            public void run(){
            mHandler.post(new Runnable(){
            public void run(){

            runOnUiThread(new Runnable() {
            @Override
            public void run() {

            if(mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            }
            if(mHandler != null)
            {
            mHandler.removeCallbacks(null);
            mHandler = null;
            }
            CurViewMode = VIEW_MODE_USER;
            setViewMode(CurViewMode);
            }
            });
            }
            });
            }
            }, 3000
             );
             }
             */

            if(DistanceFront > 0 && DistanceFront < 50) {
                if ( bAlarm == false ) {

                    bAlarm = true;
                    CurViewMode = VIEW_MODE_ALARM;
                    strAlarmMsg = "서울여대 정문근처에 도착하셨습니다!!";
                    setViewMode(CurViewMode);
                    vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(2000);

                    if ( mTimer != null ) {
                        mTimer.cancel();
                        mTimer = null;
                    }

                    mTimer = new Timer();

                    if ( mHandler != null ) {
                        mHandler.removeCallbacks(null);
                        mHandler = null;
                    }

                    mHandler = new Handler();

                    mTimer.schedule(
                            new TimerTask() {

                                @Override
                                public void run() {
                                    mHandler.post(new Runnable() {
                                        public void run() {

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    if ( mTimer != null ) {
                                                        mTimer.cancel();
                                                        mTimer = null;
                                                    }
                                                    if(mHandler != null) {
                                                        mHandler.removeCallbacks(null);
                                                        mHandler = null;
                                                    }

                                                    bAlarm = false;
                                                    CurViewMode = VIEW_MODE_USER;
                                                    setViewMode(CurViewMode);
                                                }
                                            });
                                        }
                                    });
                                }
                            }, 1000
                    );
                }
            }
            if(DistanceRear  > 0 && DistanceRear < 50) {
                if ( bAlarm == false ) {

                    bAlarm = true;
                    CurViewMode = VIEW_MODE_ALARM;
                    strAlarmMsg = "서울여대 후문근처에 도착하셨습니다!!";
                    setViewMode(CurViewMode);
                    vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(2000);

                    if ( mTimer != null ) {
                        mTimer.cancel();
                        mTimer = null;
                    }

                    mTimer = new Timer();

                    if ( mHandler != null ) {
                        mHandler.removeCallbacks(null);
                        mHandler = null;
                    }

                    mHandler = new Handler();

                    mTimer.schedule(
                            new TimerTask() {

                                @Override
                                public void run() {
                                    mHandler.post(new Runnable() {
                                        public void run() {

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    if ( mTimer != null ) {
                                                        mTimer.cancel();
                                                        mTimer = null;
                                                    }
                                                    if(mHandler != null) {
                                                        mHandler.removeCallbacks(null);
                                                        mHandler = null;
                                                    }

                                                    bAlarm = false;
                                                    CurViewMode = VIEW_MODE_USER;
                                                    setViewMode(CurViewMode);
                                                }
                                            });
                                        }
                                    });
                                }
                            }, 1000
                    );
                }
            }
            txtInfo.setTextSize(15);
            txtInfo.setText("\n현재위치 : " + mStrAddr
                    +  "\n\n             서울여대 정문까지 '" + (int)DistanceFront +  "m' 남았습니다."
                    + "\n             서울여대 후문 까지 '" + (int)DistanceRear + "m' 남았습니다.");
        }
        public void onProviderDisabled(String provider) {
            // Disabled시
            Log.d("test", "onProviderDisabled, provider:" + provider);
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
            Log.d("test", "onProviderEnabled, provider:" + provider);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }
    };

    @Override
    public void onClick(View v) {

        Intent intent = null;

        switch (v.getId())
        {
            case R.id.am_btn_menu:

                intent = new Intent(this, MainPopupActivity.class);
                intent.putExtra(Define.INTENT_MODE, CurMode);
                startActivityForResult(intent, Define.REQ_INTENT_CHANGE_MODE);
                break;

            case R.id.fvm_btn1:
                if(mDBHelper.insertParkingInfo(Define.PARKING_PLACE_NAME[Define.PLACE_PARKING1], Define.PARKING_PLACE_ADDR[Define.PLACE_PARKING1],
                        Integer.toString(Define.PARKING_PLACE_TOTAL_SPACE[Define.PLACE_PARKING1]), edtParkingSpace1.getText().toString(), null))
                {
                    Toast.makeText(this, "DB 업데이트 성공", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fvm_btn2:
                if(mDBHelper.insertParkingInfo(Define.PARKING_PLACE_NAME[Define.PLACE_PARKING2], Define.PARKING_PLACE_ADDR[Define.PLACE_PARKING2],
                        Integer.toString(Define.PARKING_PLACE_TOTAL_SPACE[Define.PLACE_PARKING2]), edtParkingSpace2.getText().toString(), null))
                {
                    Toast.makeText(this, "DB 업데이트 성공", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fvm_btn3:
                if(mDBHelper.insertParkingInfo(Define.PARKING_PLACE_NAME[Define.PLACE_PARKING3], Define.PARKING_PLACE_ADDR[Define.PLACE_PARKING3],
                        Integer.toString(Define.PARKING_PLACE_TOTAL_SPACE[Define.PLACE_PARKING3]), edtParkingSpace3.getText().toString(), null))
                {
                    Toast.makeText(this, "DB 업데이트 성공", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fvm_btn4:
                if(mDBHelper.insertParkingInfo(Define.PARKING_PLACE_NAME[Define.PLACE_PARKING4], Define.PARKING_PLACE_ADDR[Define.PLACE_PARKING4],
                        Integer.toString(Define.PARKING_PLACE_TOTAL_SPACE[Define.PLACE_PARKING4]), edtParkingSpace4.getText().toString(), null))
                {
                    Toast.makeText(this, "DB 업데이트 성공", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fvm_btn5:
                if(mDBHelper.insertParkingInfo(Define.PARKING_PLACE_NAME[Define.PLACE_PARKING5], Define.PARKING_PLACE_ADDR[Define.PLACE_PARKING5],
                        Integer.toString(Define.PARKING_PLACE_TOTAL_SPACE[Define.PLACE_PARKING5]), edtParkingSpace5.getText().toString(), null))
                {
                    Toast.makeText(this, "DB 업데이트 성공", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode != RESULT_OK )
            return;

        switch (requestCode) {
            case Define.REQ_INTENT_CHANGE_MODE:

                CurMode = data.getStringExtra(Define.INTENT_MODE);

                if(CurMode.equals(Define.MANAGER_MODE))
                {
                    CurViewMode = VIEW_MODE_MANAGER;
                    setViewMode(CurViewMode);
                }
                else
                {
                    CurViewMode = VIEW_MODE_USER;
                    setViewMode(CurViewMode);
                }
                break;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if(marker != null) {
            CurMarker = marker;

            CurViewMode = VIEW_MODE_USER_DETAIL;
            setViewMode(CurViewMode);
        }
        return false;
    }

    /*
    @Override
    public void onMyLocationChange(Location location) {

        Geocoder mCoder = new Geocoder(this);
        List<Address> addrList = null;

        double longitude = location.getLongitude(); //경도
        double latitude = location.getLatitude();   //위도
        double altitude = location.getAltitude();   //고도
        float accuracy = location.getAccuracy();    //정확도
        String provider = location.getProvider();   //위치제공자

        if (!mIsInitialized) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18));
            mIsInitialized = true;
        }

        CurrentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        String strAddr = "";

        try {
            addrList = mCoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(addrList != null && addrList.size() > 0) {
            strAddr = addrList.get(0).getAddressLine(0);

        }
        for (int i = 0; i < Define.ALARM_PLACE_NAME.length; i++) {

            if(i == Define.ALARM_PLACE_FRONT)
                DistanceFront = getDistance(Define.ALARM_PLACE_NAME[i], AlarmPlaceLatLng[i], CurrentLatLng);
            else
                DistanceRear = getDistance(Define.ALARM_PLACE_NAME[i], AlarmPlaceLatLng[i], CurrentLatLng);
        }
        txtInfo.setText("\n서울여대 정문 : " + (int)DistanceFront +  "m 남음" + "\n\n서울여대 후문 : " + (int)DistanceRear + "m 남음");
    }
    */

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
//        mMap.setOnMyLocationChangeListener(this);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);

        convertAddrToGPS();
    }

    //DB 테이블 구성
    public static class DBConstants implements BaseColumns {

        public static final class UserInfoTable implements BaseColumns {
            public static final class Column {

                // Column ID
                public static final String ID = "id";

                //사용자 ID
                public static final String UserID = "userid";

                //비밀번호
                public static final String Password = "password";

                //Mode에 따라 사용자(조회만 가능) / 관리자(조회 및 주차공간 변경 가능)
                public static final String Mode = "mode";
            }

            //Table Name
            public static final String TblNAME = "userInfoTbl";

            private UserInfoTable() {

            }
        }

        public static final class ParkingInfoTable implements BaseColumns {
            public static final class Column {

                // Column ID
                public static final String ID = "id";

                //주차장 이름
                public static final String Name = "name";

                //주차장 주소
                public static final String Addr = "addr";

                //주차장 총 공간
                public static final String TotalSpace = "totalspace";

                //주차장 잔여 공간
                public static final String EmptySpace = "emptyspace";

                //주차장 이미지
                public static final String Image = "image";
            }

            //Table Name
            public static final String TblNAME = "parkingInfoTbl";

            private ParkingInfoTable() {

            }
        }

        public static final String DATABASE_NAME = "parking.db";

        public static final int DATABASE_VERSION = 1;

        private DBConstants() {

        }
    }
}
