package com.example.wvd.practivity.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vi on 1/5/2016.
 */
public class Category {

    String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private List<Category> persons;

    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
    private void initializeData() {
        persons = new ArrayList<>();
        persons.add(new Category("Languages"));
        persons.add(new Category("Martial Arts"));
        persons.add(new Category("Dance"));
        persons.add(new Category("Handicraft"));

    }
}
