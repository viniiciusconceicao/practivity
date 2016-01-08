package com.example.wvd.practivity.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vi on 1/5/2016.
 */
public class Category implements Serializable {

    int category_id;
    String name;
    ArrayList<Activities> activities;

    public Category(){
        activities = new ArrayList<>();
    }

    public Category(String name) {
        activities = new ArrayList<>();
        this.name = name;
    }

    public ArrayList<Activities> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Activities> activities) {
        this.activities = activities;
    }

    public void addActivities(Activities act){
        activities.add(act) ;
    }

    public String getName() {
        return name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
