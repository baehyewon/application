package com.baehw1107.androidphp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by baehw_000 on 2017-05-23.
 */

public class LoginRequest extends StringRequest{

    final static private String URL = "http://baehw1107.cafe24.com/UserLogin.php";
    private Map<String, String> parameters;

    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener) {
        //해당 URL에 parameter들을 POST방식으로 보내주어라.
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
