package com.example.wvd.practivity.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.wvd.practivity.Data.Activities;
import com.example.wvd.practivity.Data.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by walter on 08/01/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG ="DataBaseHelper";

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "practivitydb";

    //Table Names
    public static final String TABLE_CATEGORIES = "categories";
    public static final String TABLE_ACTIVITIES = "activities";
    public static final String TABLE_ACTIVITIES_CATEGORIES = "activities_categories";

    //Commom column names
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";

    //ACTIVITIES_CATEGORIES table - column names
    public static final String KEY_CATEGORIES_ID = "categories_id";
    public static final String KEY_ACTIVITIES_ID = "activities_id";

    // Table Create Statements
    // Categories table create statement
    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE "
            + TABLE_CATEGORIES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT )";

    // Activities table create statement
    private static final String CREATE_TABLE_ACTIVITIES = "CREATE TABLE " + TABLE_ACTIVITIES
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT)";

    // Categories_activities table create statement
    private static final String CREATE_TABLE_ACTIVITIES_CATEGORIES = "CREATE TABLE "
            + TABLE_ACTIVITIES_CATEGORIES + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_ACTIVITIES_ID + " INTEGER," + KEY_CATEGORIES_ID + " INTEGER)";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_ACTIVITIES);
        db.execSQL(CREATE_TABLE_ACTIVITIES_CATEGORIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITIES_CATEGORIES);

        // create new tables
        onCreate(db);
    }

    /*
    * Creating a activity
    */
    public long createActivities(Activities act, long[] categories_ids) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, act.getName());
        // insert row
        long activitie_id = db.insert(TABLE_ACTIVITIES, null, values);

        // assigning activities to categories
        for (long categorie_id : categories_ids) {
            createActivitiesCategories(activitie_id, categorie_id);
        }

        return activitie_id;
    }

    /*
    * get single activity
     */
    public Activities getActivities(long todo_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_ACTIVITIES + " WHERE "
                + KEY_ID + " = " + todo_id;

        Log.e(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Activities act = new Activities();
        act.setActivities_id(c.getInt(c.getColumnIndex(KEY_ID)));
        act.setName(c.getString(c.getColumnIndex(KEY_NAME)));

        return act;
    }

    /*
    * getting all activities
    * */
    public List<Activities> getAllActivities() {
        List<Activities> activitiesArrayList = new ArrayList<Activities>();
        String selectQuery = "SELECT  * FROM " + TABLE_ACTIVITIES;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Activities act = new Activities();
                act.setActivities_id(c.getInt(c.getColumnIndex(KEY_ID)));
                act.setName(c.getString(c.getColumnIndex(KEY_NAME)));

                // adding to todo list
                activitiesArrayList.add(act);
            } while (c.moveToNext());
        }

        return activitiesArrayList;
    }

    /*
    * getting all activities under single category
    * */
    public List<Activities> getAllActivitiesByCategory(String category_name) {
        List<Activities> activitiesArrayList = new ArrayList<Activities>();

        String selectQuery = "SELECT  * FROM " + TABLE_ACTIVITIES + " td, "
                + TABLE_CATEGORIES + " tg, " + TABLE_ACTIVITIES_CATEGORIES + " tt WHERE tg."
                + KEY_NAME + " = '" + category_name + "'" + " AND tg." + KEY_ID
                + " = " + "tt." + KEY_CATEGORIES_ID + " AND td." + KEY_ID + " = "
                + "tt." + KEY_ACTIVITIES_ID;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Activities act = new Activities();
                act.setActivities_id(c.getInt(c.getColumnIndex(KEY_ID)));
                act.setName(c.getString(c.getColumnIndex(KEY_NAME)));

                // adding to todo list
                activitiesArrayList.add(act);
            } while (c.moveToNext());
        }

        return activitiesArrayList;
    }


    /*
    * Updating a activity
    */
    public int updateActivities(Activities act) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, act.getName());

        // updating row
        return db.update(TABLE_ACTIVITIES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(act.getActivities_id()) });
    }

    /*
    * Deleting a activity
    */
    public void deleteActivities(long activities_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACTIVITIES, KEY_ID + " = ?",
                new String[] { String.valueOf(activities_id) });
    }

    /*
    * Creating category
    */
    public long createCategory(Category cat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, cat.getName());;

        // insert row
        long cat_id = db.insert(TABLE_CATEGORIES, null, values);

        return cat_id;
    }

    /**
     * getting all categories
     * */
    public List<Category> getAllCategories() {
        List<Category> cats = new ArrayList<Category>();
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORIES;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Category cat = new Category();
                cat.setCategory_id(c.getInt((c.getColumnIndex(KEY_ID))));
                cat.setName(c.getString(c.getColumnIndex(KEY_NAME)));

                // adding to tags list
                cats.add(cat);
            } while (c.moveToNext());
        }
        return cats;
    }

    /*
    * Updating a categorie
    */
    public int updateCategory(Category cat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, cat.getName());

        // updating row
        return db.update(TABLE_CATEGORIES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(cat.getCategory_id()) });
    }

    /*
     * Creating activities_categories
     */
    public long createActivitiesCategories(long activities_id, long categories_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ACTIVITIES_ID, activities_id);
        values.put(KEY_CATEGORIES_ID, categories_id);

        long id = db.insert(TABLE_ACTIVITIES_CATEGORIES, null, values);

        return id;
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
