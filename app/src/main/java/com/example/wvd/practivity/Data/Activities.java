package com.example.wvd.practivity.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by walterjgsp on 06/01/16.
 */
public class Activities implements Serializable {

    private int activities_id;
    private String name;
    private ArrayList<String> equipaments;

    public Activities(){}

    public Activities(String name){
        this.name=name;
    }

    public Activities(String name, int activities_id) {
        this.name = name;
        this.activities_id = activities_id;
    }

    public int getActivities_id() {
        return activities_id;
    }

    public void setActivities_id(int activities_id) {
        this.activities_id = activities_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getEquipaments() {
        return equipaments;
    }

    public void setEquipaments(ArrayList<String> equipaments) {
        this.equipaments = equipaments;
    }
}
