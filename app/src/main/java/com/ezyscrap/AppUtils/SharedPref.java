package com.ezyscrap.AppUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * This class for Store data to locally in Shared Preferences.
 *
 */
public class SharedPref {
    public static String REG_FEE = "regfee";
    public static String WALLET_BAL = "wallet";
    public static String USER_FLAG = "flag_user";
    public static String USER_ID = "user_id";
    public static String ORG_NAME = "org_name";
    public static String EMAIL = "email";
    public static String COMPANY_REG_NO = "company_reg_no";
    public static String PHONE_NO = "phone_no";
    public static String ADDRESS = "address";
    public static String CITY = "city";
    public static String ZIP = "zip";
    public static String TOKEN = "token";
    public static String BUYER_NAME = "name";
    public static String BUYER_TOKEN = "buyer_token";
    private static Context context;
    public static final String PREF_NAME = "scrappreference";
    public static String GCMREGID = "rec_id";
    public static String NOTIFICATION_COUNTER = "counter";

    public SharedPref(Context c) {
        context = c;
    }

    public static SharedPreferences getPreferences() {

        return context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
    }

    public  void writeString(String key, String value) {
        getEditor().putString(key, value).commit();
    }

    public String getString(String key, String value){
        return getPreferences().getString(key,value);
    }

    public Editor getEditor() {

        return getPreferences().edit();
    }

    public  boolean getBoolean(String pREF_KEY_TWITTER_LOGIN, boolean b) {
        // TODO Auto-generated method stub
        return getEditor().putBoolean(pREF_KEY_TWITTER_LOGIN, b).commit();
    }


}
