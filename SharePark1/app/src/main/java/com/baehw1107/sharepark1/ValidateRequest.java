package com.baehw1107.sharepark1;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by baehw_000 on 2017-05-02.
 */

public class ValidateRequest extends StringRequest{

    final static private String URL = "";
    private Map<String, String>parameters;

    public ValidateRequest(String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
