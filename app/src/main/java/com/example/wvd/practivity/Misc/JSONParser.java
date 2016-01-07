package com.example.wvd.practivity.Misc;


import android.content.Context;
import android.util.Log;

import com.example.wvd.practivity.Data.Activities;
import com.example.wvd.practivity.Data.Category;
import com.example.wvd.practivity.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by walterjgsp on 07/01/16.
 */
public class JSONParser {

    private static final String TAG = "JSONParser";

    private Context mContext;

    public JSONParser(Context context){
        this.mContext=context;
    };

    public ArrayList<Category> readCategories(){
        InputStream inputStream = mContext.getResources().openRawResource(R.raw.menu_activities);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ArrayList<Category> categoryArrayList = new ArrayList<>();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Parse the data into jsonobject to get original data in form of json.
            JSONObject jObject = new JSONObject(byteArrayOutputStream.toString());
            JSONArray jsonArrayCategory = jObject.getJSONArray("categories");

            for(int i=0;i<jsonArrayCategory.length();i++){

                Category category_temp = new Category();
                category_temp.setCategory_id(jsonArrayCategory.getJSONObject(i).getInt("id"));
                category_temp.setName(jsonArrayCategory.getJSONObject(i).getString("value"));

                JSONArray jsonArrayActivities = jsonArrayCategory.getJSONObject(i).getJSONArray("menuitems");
                ArrayList<Activities> activitiesArrayList = new ArrayList<>();
                for(int j=0;j<jsonArrayActivities.length();j++){
                    Activities activities_temp = new Activities();
                    activities_temp.setName(jsonArrayActivities.getJSONObject(j).getString("value"));
                    activitiesArrayList.add(activities_temp);
                }

                category_temp.setActivities(activitiesArrayList);

                categoryArrayList.add(category_temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categoryArrayList;
    }
}
