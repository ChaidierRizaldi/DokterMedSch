package com.example.dokterandroidmedsch.utils;

import android.content.Context;

import java.util.HashMap;

public class SharedPreferences {
    public static final String ID = "ID";
    public static final String LOGIN = "LOGIN";

    public static android.content.SharedPreferences sp_helper;
    public static android.content.SharedPreferences.Editor editor;
    Context getActivity;

    public SharedPreferences(Context context){
        getActivity = context;
        sp_helper = context.getSharedPreferences("SP_HELPER", Context.MODE_PRIVATE);
        editor = sp_helper.edit();
    }

    public void loginSession(String id){
        editor.putBoolean(LOGIN, true);

        editor.putString(ID, id);

        editor.commit();
    }

    public HashMap<String , String >getDetailUser(){
        HashMap<String, String> data_user = new HashMap<>();

        data_user.put(ID, sp_helper.getString(ID, null));

        return  data_user;
    }

    public boolean checkLogin(){
        return sp_helper.getBoolean(LOGIN, false);
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
    }
}
