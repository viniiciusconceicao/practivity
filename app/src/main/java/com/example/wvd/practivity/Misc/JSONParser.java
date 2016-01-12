package com.example.wvd.practivity.Misc;


import android.content.Context;

import com.example.wvd.practivity.Data.Activities;
import com.example.wvd.practivity.Data.Category;
import com.example.wvd.practivity.Data.Entities;
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

    public int getJSONVersion() {
        InputStream inputStream = mContext.getResources().openRawResource(R.raw.menu_activities);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int version =-1;

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
            version = jObject.getInt("version");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return version;
    }

    public int countEntitiesActivity(int id_atividade){
        InputStream inputStream = mContext.getResources().openRawResource(R.raw.entities);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int count =0;

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
            JSONArray jsonArrayEntities = jObject.getJSONArray("entities");

            for(int i=0;i<jsonArrayEntities.length();i++){

                JSONArray jsonArrayActivities = jsonArrayEntities.getJSONObject(i).getJSONArray("atividades");
                for(int j=0;j<jsonArrayActivities.length();j++){
                    if (jsonArrayActivities.getInt(j) == id_atividade){
                        count++;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    public ArrayList<Entities> readEntities(int id_atividade){
        InputStream inputStream = mContext.getResources().openRawResource(R.raw.entities);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ArrayList<Entities> entitiesArrayList = new ArrayList<>();

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
            JSONArray jsonArrayEntities = jObject.getJSONArray("entities");

            for(int i=0;i<jsonArrayEntities.length();i++){

                Entities entity_temp = new Entities();

                JSONArray jsonArrayActivities = jsonArrayEntities.getJSONObject(i).getJSONArray("atividades");
                for(int j=0;j<jsonArrayActivities.length();j++){
                    if (jsonArrayActivities.getInt(j) == id_atividade){
                        entity_temp.setId(jsonArrayEntities.getJSONObject(i).getInt("id"));
                        entity_temp.setNome(jsonArrayEntities.getJSONObject(i).getString("nome"));
                        entity_temp.setEmail(jsonArrayEntities.getJSONObject(i).getString("email"));
                        entity_temp.setRatio(jsonArrayEntities.getJSONObject(i).getDouble("ratio"));
                        entity_temp.setEndereco(jsonArrayEntities.getJSONObject(i).getString("endereco"));
                        entity_temp.setSite(jsonArrayEntities.getJSONObject(i).getString("site"));
                        entity_temp.setTelefone(jsonArrayEntities.getJSONObject(i).getString("telefone"));

                        entitiesArrayList.add(entity_temp);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entitiesArrayList;
    }

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
                category_temp.setIcon(jsonArrayCategory.getJSONObject(i).getString("icon"));

                JSONArray jsonArrayActivities = jsonArrayCategory.getJSONObject(i).getJSONArray("menuitems");
                ArrayList<Activities> activitiesArrayList = new ArrayList<>();
                for(int j=0;j<jsonArrayActivities.length();j++){
                    Activities activities_temp = new Activities();
                    activities_temp.setActivities_id(jsonArrayActivities.getJSONObject(j).getInt("id"));
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
