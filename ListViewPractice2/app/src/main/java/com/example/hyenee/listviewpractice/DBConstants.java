package com.example.hyenee.listviewpractice;

import android.provider.BaseColumns;

//DB 테이블 구성
public class DBConstants implements BaseColumns {

    public static final class TasteTable implements BaseColumns {
        public static final class Column {

            // Column ID
            public static final String ID = "id";

            //이름
            public static final String NAME = "name";

            //전화번호
            public static final String PHONE = "phone";

            //연인 유무
            public static final String IS_HONEY = "is_honey";

            //생일
            public static final String BIRTHDAY = "birthday";

            //알람 날짜
            public static final String ALARM_DATE = "alarm_date";

            //알람 설정 시간(시)
            public static final String ALARM_HOUR = "alarm_hour";

            //알람 설정 시간(분)
            public static final String ALARM_MINUTE = "alarm_minute";

            //알람 ID
            public static final String ALARM_ID = "alarm_id";

            //준 선물
            public static final String GAVEN_GIFT = "gaven_gift";

            //받은 선물
            public static final String TAKEN_GIFT = "taken_gift";

            //스타일
            public static final String STYLE = "style";

            //좋아하는 것
            public static final String LIKE = "like";

            //싦어하는 것
            public static final String DISLIKE = "dislike";

            //줄 선물
            public static final String WILL_GIVE_GIFT = "will_give_gift";

            //축하 메시지
            public static final String ALARM_MESSAGE = "alarm_msg";
        }

        //Table Name
        public static final String TblNAME = "TasteTable";

        private TasteTable() {

        }
    }

    public static final String DATABASE_NAME = "taste.db";

    public static final int DATABASE_VERSION = 1;

    private DBConstants() {

    }
}