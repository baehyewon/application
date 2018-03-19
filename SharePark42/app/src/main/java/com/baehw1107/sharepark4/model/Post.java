package com.baehw1107.sharepark4.model;

/**
 * Created by baehw_000 on 2017-06-06.
 */

public class Post {
    private String email;
    private String post_address;
    private String post_start;
    private String post_end;


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPost_address(String post_address) {
        this.post_address = post_address;
    }

    public void setPost_start(String post_start) {
        this.post_start = post_start;
    }

    public void setPost_end(String post_end) {
        this.post_end = post_end;
    }

    public String getPost_address() { return post_address;  }

    public String getPost_start() {
        return post_start;
    }

    public String getPost_end() {
        return post_end;
    }


    /*public String getCreated_at() {
       return created_at;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setToken(String token) {
        this.token = token;
    }*/
}
