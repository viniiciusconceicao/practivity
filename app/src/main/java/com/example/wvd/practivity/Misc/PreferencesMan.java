package com.example.wvd.practivity.Misc;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by walterjgsp on 11/01/16.
 */
public class PreferencesMan {

    private static final String TAG ="PreferencesMan";

    public static final String MyPREFERENCES = "PractivityPreferences" ;
    public static final String JSON_version_key ="JSONVersion";

    private SharedPreferences sharedpreferences;
    private Context mContext;

    public PreferencesMan(Context mContext){
        this.mContext=mContext;
        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
    }

    public int getJSON_version(){
        return sharedpreferences.getInt(JSON_version_key,1);
    }

    public void setJSON_version(int value){
        SharedPreferences.Editor editor =sharedpreferences.edit();
        editor.putInt(JSON_version_key,value);
        editor.commit();
    }
}
