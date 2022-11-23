package com.example.groupproject;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

public class TravelDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Travel";
    private static final int DB_VERSION = 1;

    private final ReadWriteLock lock;

    TravelDatabaseHelper(Context context, ReadWriteLock lock) {
        super(context, DB_NAME, null, DB_VERSION);
        this.lock = lock;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        initialize(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void initialize(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS SITES (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "IMAGE_RESOURCE_ID INTEGER, "
                + "X_LOCATION FLOAT, "
                + "Y_LOCATION FLOAT, "
                + "DESCRIPTION TEXT, "
                + "FAVORITE INTEGER);");
        for (int i = 0; i < 20; i++) {
            this.insertSite(db, "a" + i, R.drawable.description_sight_1, 0, 0,
                    "This is a description", "park", false);
        }

        db.execSQL("CREATE TABLE IF NOT EXISTS PLANS (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ROUTE TEXT, " +
                "TITLE TEXT)");
        db.close();
    }

    public void initialize() {
        lock.writeLock().lock();
        SQLiteDatabase db;
        db = getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS SITES (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "IMAGE_RESOURCE_ID INTEGER, "
                + "X_LOCATION REAL, "
                + "Y_LOCATION REAL, "
                + "DESCRIPTION TEXT, "
                + "TYPE TEXT,"
                + "FAVORITE INTEGER);");
        for (int i = 0; i < 20; i++) {
            this.insertSite(db, "a" + i, R.drawable.description_sight_1, 0, 0, "This is a description","park",false);
        }

        db.execSQL("CREATE TABLE IF NOT EXISTS PLANS (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ROUTE TEXT, " +
                "TITLE TEXT UNIQUE)");
        db.close();
        lock.writeLock().unlock();
    }

    private void insertSite(SQLiteDatabase db, String name, int resourceID, float xLocation, float yLocation,
                                   String description, String type, boolean favorite) {
        db = getWritableDatabase();
        ContentValues siteValues = new ContentValues();
        siteValues.put("NAME", name);
        siteValues.put("IMAGE_RESOURCE_ID", resourceID);
        siteValues.put("X_LOCATION", xLocation);
        siteValues.put("Y_LOCATION", yLocation);
        siteValues.put("DESCRIPTION", description);
        siteValues.put("TYPE", type);
        int favouriteFlag = (favorite)? 1: 0;
        siteValues.put("FAVORITE", favouriteFlag);
        db.insert("SITES", null, siteValues);
    }

    public class Site {
        private final String name, type, description;
        private final int id_img;
        private final float x_coor, y_coor;

        public Site(String name, int id_img, float x_coor, float y_coor, String description, String type) {
            this.name = name;
            this.type = type;
            this.description = description;
            this.id_img = id_img;
            this.x_coor = x_coor;
            this.y_coor = y_coor;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public int getId_img() {
            return id_img;
        }

        public float getX_coor() {
            return x_coor;
        }

        public float getY_coor() {
            return y_coor;
        }

        public String getDescription() {
            return description;
        }
    }

    public class Plan {
        private final String title;
        private final List<Integer> route;

        public Plan(String title, List<Integer> route) {
            this.title = title;
            this.route = route;
        }

        public List<Integer> getRoute() {
            return route;
        }

        public String getTitle() {
            return title;
        }
    }

    public List<Site> getAllSites() {
        lock.readLock().lock();
        SQLiteDatabase db;
        db = getReadableDatabase();
        List<Site> list = new ArrayList<>();
        Site site;
        Cursor cursor = db.query("SITES",
                new String[]{"NAME", "IMAGE_RESOURCE_ID", "X_LOCATION", "Y_LOCATION", "DESCRIPTION", "TYPE"}
                ,null, null, null, null, null);
        while (cursor.moveToNext()) {
            site = new Site(cursor.getString(0), cursor.getInt(1), cursor.getFloat(2),
                    cursor.getFloat(3), cursor.getString(4), cursor.getString(5));
            list.add(site);
        }
        cursor.close();
        db.close();
        lock.readLock().unlock();
        return list;
    }

    public List<Site> getAllFavoriteSites() {
        lock.readLock().lock();
        SQLiteDatabase db;
        db = getReadableDatabase();
        List<Site> list = new ArrayList<>();
        Site site;
        Cursor cursor = db.query("SITES",
                new String[]{"NAME", "IMAGE_RESOURCE_ID", "X_LOCATION", "Y_LOCATION", "DESCRIPTION", "TYPE"}
                ,"FAVORITE = ?", new String[] {Integer.toString(1)}, null, null, null);
        while (cursor.moveToNext()) {
            site = new Site(cursor.getString(0), cursor.getInt(1), cursor.getFloat(2),
                    cursor.getFloat(3), cursor.getString(4), cursor.getString(5));
            list.add(site);
        }
        cursor.close();
        db.close();
        lock.readLock().unlock();
        return list;
    }

    public void addFavoriteItem(int id) {
        lock.writeLock().lock();
        SQLiteDatabase db;
        db = getWritableDatabase();
        ContentValues favValues = new ContentValues();
        favValues.put("FAVORITE", 1);
        db.update("SITES", favValues, "_id = ?", new String[] {Integer.toString(id)});
        db.close();
        lock.writeLock().unlock();
    }

    public void deleteFavoriteItem(int id) {
        lock.writeLock().lock();
        SQLiteDatabase db;
        db = getWritableDatabase();
        ContentValues favValues = new ContentValues();
        favValues.put("FAVORITE", 0);
        db.update("SITES", favValues, "_id = ?", new String[] {Integer.toString(id)});
        db.close();
        lock.writeLock().unlock();
    }

    public void insertPlan(List<Integer> plan, String title) {
        if (plan.isEmpty()) return;
        SQLiteDatabase db;
        lock.writeLock().lock();
        db = getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        for (Integer i: plan) {
            sb.append(i);
            sb.append(",");
        }
        String planString = sb.toString();
        ContentValues planValues = new ContentValues();
        planValues.put("ROUTE", planString);
        planValues.put("TITLE", title);
        try {
            db.insert("PLANS", null, planValues);
        } catch (SQLiteConstraintException e) {
            throw new SQLiteConstraintException("Plan title can't be same!");
        }
        db.close();
        lock.writeLock().unlock();
    }

    public List<Plan> getAllPlans() {
        lock.readLock().lock();
        SQLiteDatabase db;
        db = getReadableDatabase();
        ArrayList<Plan> list = new ArrayList<>();
        Cursor cursor = db.query("PLANS",
                new String[]{"ROUTE", "TITLE"},
                null,
                null,
                null, null ,null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(1);
            String routeString = cursor.getString(0);
            Plan plan;
            List<Integer> route = parseRoute(routeString);
            plan = new Plan(title, route);
            list.add(plan);
        }
        db.close();
        lock.readLock().unlock();
        return list;
    }

    public void deletePlans(String title) {
        lock.writeLock().lock();
        SQLiteDatabase db;
        db = getWritableDatabase();
        db.delete("PLANS", "TITLE = ?", new String[] {title});
        db.close();
        lock.writeLock().unlock();
    }

    public List<Site> getPlanDetails(String title) {
        lock.readLock().lock();
        SQLiteDatabase db;
        db = getReadableDatabase();
        List<Site> list = new ArrayList<>();
        Cursor cursorPlan = db.query("PLANS", new String[] {"ROUTE"}, "TITLE = ?", new String[] {title},
                null, null, null, null);
        if (cursorPlan.moveToFirst()) {
            String routeString = cursorPlan.getString(0);
            cursorPlan.close();
            List<Integer> route = parseRoute(routeString);
            StringBuilder sb = new StringBuilder();
            for (int i : route) {
                sb.append(i);
            }
            Site site;
            Cursor cursorSites = db.query("SITES",
                    new String[]{"NAME", "IMAGE_RESOURCE_ID", "X_LOCATION", "Y_LOCATION", "DESCRIPTION", "TYPE"}
                    ,"_id in (" + sb.toString() +")",
                    null, null, null, null);
            while (cursorSites.moveToNext()) {
                site = new Site(cursorSites.getString(0), cursorSites.getInt(1), cursorSites.getFloat(2),
                        cursorSites.getFloat(3), cursorSites.getString(4), cursorSites.getString(5));
                list.add(site);
            }
            cursorSites.close();
        }
        db.close();
        lock.readLock().unlock();
        return list;
    }

    private List<Integer> parseRoute(String routeString) {
        String[] routeArr = routeString.split(",");
        ArrayList<Integer> route = new ArrayList<>();
        for (int i = 0; i < routeArr.length; i++) {
            route.add(Integer.parseInt(routeArr[i]));
        }
        return route;
    }

    public void deleteTables() {
        lock.writeLock().lock();
        SQLiteDatabase db;
        db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS PLANS");
        db.execSQL("DROP TABLE IF EXISTS SITES");
        db.close();
        lock.writeLock().unlock();
    }
}
