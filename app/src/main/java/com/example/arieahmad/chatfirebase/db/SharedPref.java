package com.example.arieahmad.chatfirebase.db;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Arie Ahmad on 7/14/2017.
 */

public class SharedPref {
    private static SharedPref mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "chatfb_sharedpref";
    private static final String KEY_NAMA = "chatfb_nama";

    private SharedPref(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPref(context);
        }
        return mInstance;
    }

    public boolean saveNama(String nama){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_NAMA, nama);

        editor.apply();
        return true;
    }

    public boolean savedName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_NAMA, null) != null){
            return true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }


    public String getNama(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAMA, null);
    }
}
