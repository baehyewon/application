package parkingreservation;

/**
 * Created by thkang on 2017-05-31.
 */

public class Define {

    public static final int FINISHED = 100;

    public static final String USER_MODE = "UserMode";
    public static final String MANAGER_MODE = "ManagerMode";

    public static final int ALARM_PLACE_FRONT           = 0;    //서울여대 정문
    public static final int ALARM_PLACE_REAR            = 1;    //서울여대 후문

    public static final int PLACE_PARKING1 = 0;     //정문
    public static final int PLACE_PARKING2 = 1;     //제2과학관
    public static final int PLACE_PARKING3 = 2;     //기숙사
    public static final int PLACE_PARKING4 = 3;     //행정관
    public static final int PLACE_PARKING5 = 4;     //도서관

    public static final int REQ_INTENT_CHANGE_MODE = 100;

    public static final String INTENT_MODE = "Mode";

    //푸쉬 알람이 울릴 위치 이름
    public static final String [] ALARM_PLACE_NAME = {"서울여대 정문", "서울여대 후문"};

    //푸쉬 알람이 울릴 위치 주소
    public static final String [] ALARM_PLACE_ADDR = {"서울특별시 노원구 공릉2동 산223-8", "서울특별시 노원구 공릉2동 126-8"};

    //서울여대의 각 주차장 이름
    public static final String [] PARKING_PLACE_NAME = {"정문 주차장", "제2과학관 주차장", "기숙사 주차장", "행정관 주차장", "도서관 주차장"};

    //서울여대의 각 주차장 주소
    public static final String [] PARKING_PLACE_ADDR = {"서울특별시 노원구 공릉2동 27-1", "서울특별시 노원구 공릉2동 126", "서울특별시 노원구 공릉2동 126-3",
    "서울특별시 노원구 공릉2동 126-1", "서울특별시 노원구 공릉2동 117-1"};

    //서울여대의 각 주차장 총 공간
    public static final int [] PARKING_PLACE_TOTAL_SPACE = {40, 50, 60, 70, 80};

    //서울여대의 각 주차장 가용 공간
    public static final int [] PARKING_PLACE_EMPTY_SPACE = {1, 2, 0, 4, 5};

    /*
    정문: 서울특별시 노원구 공릉2동 27-1 주변
    제2과학관: 서울특별시 노원구 공릉2동 126 주변
    기숙사: 서울특별시 노원구 공릉2동 126-3 주변
    행정관: 서울특별시 노원구 공릉2동 126-1 주변
    도서관: 서울특별시 노원구 공릉2동 117-1 주변
    */
}
