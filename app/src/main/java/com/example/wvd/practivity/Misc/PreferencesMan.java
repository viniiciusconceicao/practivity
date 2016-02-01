package com.example.wvd.practivity.Misc;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

/**
 * Created by walterjgsp on 11/01/16.
 */
public class PreferencesMan {

    private static final String TAG ="PreferencesMan";

    public static final String MyPREFERENCES = "PractivityPreferences" ;
    public static final String JSON_version_key ="JSONVersion";
    public static final String LastLatitude="LastLatitude";
    public static final String LastLongitude="LastLongitude";

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

    public Location getLocation(){
        Location lastLocation = new Location("userLocation");
        lastLocation.setLatitude(sharedpreferences.getFloat(LastLatitude, 0));
        lastLocation.setLongitude(sharedpreferences.getFloat(LastLongitude, 0));
        return lastLocation;
    }

    public void setLocation(Location loc){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putFloat(LastLatitude,(float)loc.getLatitude());
        editor.putFloat(LastLongitude,(float)loc.getLongitude());
        editor.commit();
    }
}
