package test.parkingreservation;

import android.os.Parcel;
import android.os.Parcelable;

//주차장 정보
public class UserInfo implements Parcelable {

    protected UserInfo(Parcel in) {
//        readFromParcel(in);
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    private String id;

    private String userid;

    private String password;

    private String mode;

    public UserInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getUserid() {
        return userid;
    }

    public String getPassword() {
        return password;
    }

    public String getMode() {
        return mode;
    }

    /*
    private void readFromParcel(Parcel in) {
        id = in.readString();
        name = in.readString();
        height = in.readString();
        weigh = in.readString();
        image = in.readByteArray();
    }
    */

    public void setId(String id) {
        this.id = id;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "UserInfo [userid=" + userid
                +", password=" + password
                +", mode=" + mode
                +"]";
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        /*
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(birthday);
        dest.writeString(sex);
        dest.writeString(color);
        dest.writeString(alarm);
        */
    }
}