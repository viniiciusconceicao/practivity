package com.example.wvd.practivity.Data;

/**
 * Created by walterjgsp on 06/01/16.
 */
public class Activities {

    private int activities_id;
    private String name;

    public Activities(){}

    public Activities(String name){
        this.name=name;
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
}
