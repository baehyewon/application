package test.parkingreservation;

import android.provider.BaseColumns;

public class DBConstants implements BaseColumns {

    public static final class UserInfoTable implements BaseColumns {
        public static final class Column {
            public static final String ID = "id";
            public static final String UserID = "userid";
            public static final String Password = "password";
            public static final String Mode = "mode";
        }

        public static final String TblNAME = "userInfoTbl";

        private UserInfoTable() {
        }
    }

    public static final class ParkingInfoTable implements BaseColumns {
        public static final class Column {

            public static final String ID = "id";
            public static final String Name = "name";
            public static final String Addr = "addr";
            public static final String TotalSpace = "totalspace";
            public static final String EmptySpace = "emptyspace";
            public static final String Image = "image";
        }

        public static final String TblNAME = "parkingInfoTbl";

        private ParkingInfoTable() {

        }
    }

    public static final String DATABASE_NAME = "parking.db";
    public static final int DATABASE_VERSION = 1;

    private DBConstants() {

    }
}