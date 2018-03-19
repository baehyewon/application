package com.baehw1107.share_park5.utils;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by baehw_000 on 2017-06-04.
 */

public class Validation {
    public static boolean validateFields(String name){

        if (TextUtils.isEmpty(name)) {

            return false;

        } else {

            return true;
        }
    }

    public static boolean validateEmail(String string) {

        if (TextUtils.isEmpty(string) || !Patterns.EMAIL_ADDRESS.matcher(string).matches()) {

            return false;

        } else {

            return  true;
        }
    }
}
