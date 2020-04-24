package com.tancheng.carbonchain.utils;

import android.content.Context;

import java.util.Set;

/**
 * Created by Administrator on 2017/11/11.
 */
public class UserPreference extends BaseSharePreference {
    private static volatile UserPreference userPreference;
    public static final String USER = "user";
    public static final String PINCODE = "PINCODE";

    protected UserPreference(Context context)
    {
        super(context, USER);
    }

    public static UserPreference getUserPreference(Context context) {

            if (userPreference == null) {
                synchronized (UserPreference.class) {
                    if (userPreference==null) {
                        userPreference = new UserPreference(context);
                    }
                }
            }
        return userPreference;
    }


    public void setPincode(String content) {
        putString(PINCODE, content);
    }
    public String getPincode()
    {
        return getString(PINCODE, "");
    }

}
