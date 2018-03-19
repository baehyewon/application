package com.baehw1107.androidphp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by baehw_000 on 2017-05-02.
 */

public class RegisterRequest extends StringRequest{

    final static private String URL = "http://baehw1107.cafe24.com/UserRegister.php";
    private Map<String, String>parameters;

    public RegisterRequest(String userID, String userPassword, String userName, String userEmail, Response.Listener<String> listener) {
        //해당 URL에 parameter들을 POST방식으로 보내주어라.
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
        parameters.put("userEmail", userEmail);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
