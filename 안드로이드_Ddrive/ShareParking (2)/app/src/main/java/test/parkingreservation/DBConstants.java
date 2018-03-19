package test.parkingreservation;

import android.provider.BaseColumns;

//DB 테이블 구성
public class DBConstants implements BaseColumns {

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