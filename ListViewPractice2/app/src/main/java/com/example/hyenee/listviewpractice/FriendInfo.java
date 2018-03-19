package com.example.hyenee.listviewpractice;

import android.os.Parcel;
import android.os.Parcelable;

public class FriendInfo implements Parcelable {

    protected FriendInfo(Parcel in) {
//        readFromParcel(in);
    }

    public static final Creator<FriendInfo> CREATOR = new Creator<FriendInfo>() {
        @Override
        public FriendInfo createFromParcel(Parcel in) {
            return new FriendInfo(in);
        }

        @Override
        public FriendInfo[] newArray(int size) {
            return new FriendInfo[size];
        }
    };

    private String id;

    private String name;

    private String phone;

    private String is_honey;

    private String birthday;

    private String alarm_date;

    private String alarm_hour;

    private String alarm_minute;

    private String alarm_id;

    private String gaven_gift;
    private String taken_gift;
    private String style;
    private String like;
    private String dislike;
    private String will_give_gift;

    private String alarm_msg;

    public FriendInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getIsHoney() {
        return is_honey;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getAlarmDate() {
        return alarm_date;
    }

    public String getAlarmHour() {
        return alarm_hour;
    }

    public String getAlarmMinute() {
        return alarm_minute;
    }

    public String getAlarmId() {
        return alarm_id;
    }

    public String getGavenGift() {
        return gaven_gift;
    }
    public String getTakenGift() {
        return taken_gift;
    }
    public String getStyle() {
        return style;
    }
    public String getLike() {
        return like;
    }
    public String getDislike() {
        return dislike;
    }
    public String getWillGiveGift() {
        return will_give_gift;
    }


    public String getAlarmMsg() {
        return alarm_msg;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setIsHoney(String is_honey) {
        this.is_honey = is_honey;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setAlarmDate(String alarm_date) {
        this.alarm_date = alarm_date;
    }

    public void setAlarmHour(String alarm_hour) {
        this.alarm_hour= alarm_hour;
    }

    public void setAlarmMinute(String alarm_minute) {
        this.alarm_minute = alarm_minute;
    }

    public void setAlarmId(String alarm_id) {
        this.alarm_id = alarm_id;
    }

    public void setGavenGift(String gaven_gift) {
        this.gaven_gift = gaven_gift;
    }
    public void setTakenGift(String taken_gift) {
        this.taken_gift = taken_gift;
    }
    public void setStyle(String style) {
        this.style = style;
    }
    public void setLike(String like) {
        this.like = like;
    }
    public void setDislike(String dislike) {
        this.dislike = dislike;
    }
    public void setWillGiveGift(String will_give_gift) {
        this.will_give_gift = will_give_gift;
    }

    public void setAlarmMsg(String alarm_msg) {
        this.alarm_msg = alarm_msg;
    }

    @Override
    public String toString() {
        return "";
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