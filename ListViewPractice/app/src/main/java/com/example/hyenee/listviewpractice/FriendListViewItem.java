package com.example.hyenee.listviewpractice;

public class FriendListViewItem {

    private String id;
    private String is_honey;
    private String name;
    private String birthday;

    public void setId(String id) {
        this.id = id;
    }
    public void setIsHoney(String is_honey) {
        this.is_honey = is_honey;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getId() { return this.id; }
    public String getIsHoney() { return this.is_honey; }
    public String getName() { return this.name; }
    public String getBirthday() { return this.birthday; }
}
