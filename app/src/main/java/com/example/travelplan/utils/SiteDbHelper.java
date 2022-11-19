package com.example.travelplan.utils;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.travelplan.bean.Site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class SiteDbHelper extends SQLiteOpenHelper {


    public static final String DB_NAME = "tb_site";

    private static final String CREATE_PLAN_DB = "create table tb_Site(" +
            "siteName text," +
            "siteImage int," +
            "siteDesc text," +
            "siteRating int," +
            "siteType text," +
            "siteLocation text," +
            "siteContact char(11)," +
            "siteOpentime text," +
            "siteFav bool)";

    public SiteDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PLAN_DB);

        db.execSQL("insert into tb_Site Values('Park', ,'A park', 4, 'Park', 'Suzhou', 12345678901, '12:00 - 20:00', False)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // fav or undo fav
    public void favOrUnfavSite(Site site, boolean fav) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("siteFav", fav);
        db.update("tb_Site",values,"siteName=?",new String[]{String.valueOf(site.getSiteName())});
        db.insert(DB_NAME,null,values);
        values.clear();
    }


    // all sites
    @SuppressLint("Range")
    public ArrayList<Map<String, Object>> getAllSites() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Map<String, Object>> listPlans = new ArrayList<Map<String, Object>>();
        Cursor cursor = db.query("tb_Site", null, null, null, null, null,null);

        int resultCounts = cursor.getCount();
        if (resultCounts == 0 ) {
            return null;
        } else {
            while (cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("siteName", cursor.getString(cursor.getColumnIndex("siteName")));
                map.put("siteImage", cursor.getString(cursor.getColumnIndex("siteImage")));
                listPlans.add(map);
            }
            return listPlans;
        }
    }

    // site desc
    public Cursor searchData(String siteName){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ "tb_Site" +" WHERE siteName="+ siteName,null);
        return cursor;
    }

    @SuppressLint("Range")
    public ArrayList<Map<String, Object>> getSiteInfo(String siteName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Map<String, Object>> listPlans = new ArrayList<Map<String, Object>>();
        Cursor cursor = searchData(siteName);
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 ) {
            return null;
        } else {
            while (cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("siteName", cursor.getString(cursor.getColumnIndex("siteName")));
                map.put("siteImage", cursor.getString(cursor.getColumnIndex("siteImage")));
                map.put("siteDesc", cursor.getString(cursor.getColumnIndex("siteDesc")));
                map.put("siteRating", cursor.getString(cursor.getColumnIndex("siteRating")));
                map.put("siteType", cursor.getString(cursor.getColumnIndex("siteType")));
                map.put("siteLocation", cursor.getString(cursor.getColumnIndex("siteLocation")));
                map.put("siteContact", cursor.getString(cursor.getColumnIndex("siteContact")));
                map.put("siteOpentime", cursor.getString(cursor.getColumnIndex("siteOpentime")));
                map.put("siteFav", cursor.getString(cursor.getColumnIndex("siteFav")));
                listPlans.add(map);
            }
            return listPlans;
        }
    }


    // all fav sites
    @SuppressLint("Range")
    public ArrayList<Map<String, Object>> getAllFavSites() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Map<String, Object>> listPlans = new ArrayList<Map<String, Object>>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+"tb_Site"+" WHERE siteFav="+ "True",null);

        int resultCounts = cursor.getCount();
        if (resultCounts == 0 ) {
            return null;
        } else {
            while (cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("siteName", cursor.getString(cursor.getColumnIndex("siteName")));
                map.put("siteImage", cursor.getString(cursor.getColumnIndex("siteImage")));
                map.put("siteLocation", cursor.getString(cursor.getColumnIndex("siteLocation")));
                map.put("siteFav", cursor.getString(cursor.getColumnIndex("siteFav")));
                listPlans.add(map);
            }
            return listPlans;
        }
    }
}


