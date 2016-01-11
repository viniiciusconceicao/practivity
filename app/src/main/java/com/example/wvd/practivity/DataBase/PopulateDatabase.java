package com.example.wvd.practivity.DataBase;

import android.content.Context;

import com.example.wvd.practivity.Misc.JSONParser;

/**
 * Created by walter on 09/01/16.
 *
 * This class will populate the database from the JSON file
 */
public class PopulateDatabase {

    private JSONParser jsonParser;
    private Context mContext;

    public PopulateDatabase(Context context){
        mContext = context;
        jsonParser = new JSONParser(context);
    }


}
