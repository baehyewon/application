package parkingreservation;

import android.os.Parcel;
import android.os.Parcelable;

//주차장 정보
public class ParkingInfo implements Parcelable {

    protected ParkingInfo(Parcel in) {
//        readFromParcel(in);
    }

    public static final Creator<ParkingInfo> CREATOR = new Creator<ParkingInfo>() {
        @Override
        public ParkingInfo createFromParcel(Parcel in) {
            return new ParkingInfo(in);
        }

        @Override
        public ParkingInfo[] newArray(int size) {
            return new ParkingInfo[size];
        }
    };

    private String id;

    private String name;

    private String addr;

    private String totalspace;

    private String emptyspace;

    private byte[] image;

    public ParkingInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public String getAddr() {
        return addr;
    }

    public String getTotalspace() {
        return totalspace;
    }

    public String getEmptyspace() {
        return emptyspace;
    }

    public byte[] getImage() {
        return image;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setTotalspace(String totalspace) {
        this.totalspace = totalspace;
    }

    public void setEmptyspace(String emptyspace) {
        this.emptyspace = emptyspace;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ParkingInfo [name=" + name
                +", addr=" + addr
                +", totalspace=" + totalspace
                +", emptyspace=" + emptyspace
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