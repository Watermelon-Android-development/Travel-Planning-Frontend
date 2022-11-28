package com.example.travelplan;


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
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TravelDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Travel";
    private static final int DB_VERSION = 1;

    private final ReadWriteLock lock;

    public TravelDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        lock = new ReentrantReadWriteLock(true);
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
                + "TYPE TEXT,"
                + "OPEN_TIME TEXT,"
                + "PLACE TEXT,"
                + "MARK INTEGER,"
                + "PHONE TEXT,"
                + "FAVORITE INTEGER,"
                + "CHECK(MARK >=0 AND MARK <=5));");
        this.insertSite(db, "Bailu Park" , R.drawable.site0, 120.720819, 31.270827,
                "This is a description","scenery","0:00-24:00",
                "No.101, Cuiwei Street, Suzhou Industrial Park", 5,"None", false);
        this.insertSite(db, "Jinji Lake Scenic Area" , R.drawable.site1, 120.701151, 31.301202,
                "This is a description","scenery","0:00-24:00",
                "Jinjihu Ring Lake, Suzhou Industrial Park", 5,"4007558558", false);
        this.insertSite(db, "Shangfangshan Forest Animal World" , R.drawable.site2, 120.582076, 31.238321,
                "This is a description","zoo","7:30-16:30",
                "NWuyue Road South 200 Meters, Huqiu District", 5, "0512-68230800",false);
        this.insertSite(db, "Suzhou Paradise Forest World" , R.drawable.site3, 120.477035, 31.323242,
                "This is a description","playground","9:15-17:15",
                "No.99, Xiangshan Road, Huqiu District", 4, "0512-68717155",false);
        this.insertSite(db, "Tianpingshan Scenery Spot" , R.drawable.site4, 120.504155, 31.287304,
                "This is a description","scenery","8:00-17:00",
                "Lingtian Road, Muduzhen, Wuzhong District", 5, "0512-66387422",false);
        this.insertSite(db, "Panmen Scenic Spots" , R.drawable.site5, 120.617335, 31.288477,
                "This is a description","scenery","8:30-17:00",
                "No.49, East Street, GUsu District", 5, "0512-65260004",false);
        this.insertSite(db, "Suzhou Museum" , R.drawable.site6, 120.627856, 31.322948,
                "This is a description","museum","9:00-17:00",
                "No.204, Dongbei Street, Gusu District", 5, "0512-67575666",false);
        this.insertSite(db, "Suzhou Center" , R.drawable.site7, 120.585300, 31.298930,
                "This is a description","mall","10:00-22:00",
                "Intersection of Xinggang Street and Suxiu Road, Wuzhong District", 5, "0512-69881111",false);
        this.insertSite(db, "Huqiu Wetland Park" , R.drawable.site8, 120.571201, 31.365051,
                "This is a description","scenery","6:00-20:00",
                "No.1800, Yangchenghu West Road, Xiangcheng District", 4, "0512-65292021",false);
        this.insertSite(db, "H.Brothers Film Paradise" , R.drawable.site9, 120.786430, 31.375192,
                "This is a description","playground","9:30-21:30",
                "No.188, Yangchenghu Avenue, Wuzhong District", 5, "0512-67990518",false);
        this.insertSite(db, "Moon Bay" , R.drawable.site10, 120.718439, 31.262379,
                "This is a description","scenery","0:00-24:00",
                "Intersection of Yueliangwan Road and Wanshou Street, Suzhou Industrial Park", 5, "0512-69995999",false);
        this.insertSite(db, "Suzhou Dushu Lake Christian Church" , R.drawable.site11, 120.719354, 31.268678,
                "This is a description","church","8:00-16:00",
                "No.99, Cuiwei Street, Huqiu District", 5, "0512-69566001",false);
        this.insertSite(db, "Longhu Shishan Raradise Walk" , R.drawable.site12, 120.556139, 31.294375,
                "This is a description","mall","10:00-22:00",
                "No.181, Tayuan Road, Huqiu District ", 5, "0512-83800288",false);
        this.insertSite(db, "Xietang Old Steetk" , R.drawable.site13, 120.738641, 31.303093,
                "This is a description","block","0:00-24:00",
                "No.1088, Songtao Street, Wuzhong DIstrict", 5, "0512-62587188",false);
        this.insertSite(db, "Yuanrong Times Square" , R.drawable.site14, 120.712640, 31.320805,
                "This is a description","mall","9:00-22:00",
                "No.268, Wangdun Road, Suzhou Industrial Park", 5, "0512-66966666",false);
        this.insertSite(db, "Suzhou True Color Museum" , R.drawable.site15, 120.671562, 31.253693,
                "This is a description","museum","10:00-17:00",
                "No.219, Tongda Road, Wuzhong District", 5, "0512-65968890",false);
        this.insertSite(db, "Guanqian Street" , R.drawable.site16, 120.625637, 31.310624,
                "This is a description","block","0:00-24:00",
                "Guanqian Street, Gusu District", 5, "None",false);
        this.insertSite(db, "Hanshan Temple" , R.drawable.site17, 120.568391, 31.310469,
                "This is a description","temple","9:00-16:30",
                "No.24, Hanshangsi Long, Gusu District", 5, "0512-67236213",false);
        this.insertSite(db, "Xiyuan Temple" , R.drawable.site18, 120.587334, 31.314823,
                "This is a description","temple","8:00-17:00",
                "No.18, Xiyuan Long, Liuyuan Road, Gusu District", 5, "0512-65529219",false);
        this.insertSite(db, "Suzhou Yuyao Relic Site Park" , R.drawable.site19, 120.612421, 31.361302,
                "This is a description","scenery","8:00-18:00",
                "No.95, Yangchenghu West Road, Xiangcheng District", 4, "0512-66182178",false);

        db.execSQL("CREATE TABLE IF NOT EXISTS PLANS (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ROUTE TEXT, " +
                "TITLE TEXT UNIQUE)");
    }

    private void insertSite(SQLiteDatabase db, String name, int resourceID, double xLocation, double yLocation,
                                   String description, String type, String openTime, String place, int mark, String phone, boolean favorite) {
        ContentValues siteValues = new ContentValues();
        siteValues.put("NAME", name);
        siteValues.put("IMAGE_RESOURCE_ID", resourceID);
        siteValues.put("X_LOCATION", xLocation);
        siteValues.put("Y_LOCATION", yLocation);
        siteValues.put("DESCRIPTION", description);
        siteValues.put("TYPE", type);
        siteValues.put("OPEN_TIME", openTime);
        siteValues.put("PLACE", place);
        siteValues.put("MARK", mark);
        siteValues.put("PHONE", phone);
        int favouriteFlag = (favorite)? 1: 0;
        siteValues.put("FAVORITE", favouriteFlag);
        db.insert("SITES", null, siteValues);
    }

    public class Site {
        private final String name, type, description, openTime, place, phone;
        private final int imgID, mark;
        private final double xCoor, yCoor;
        private final boolean isFavorite;

        public Site(String name, int imgID, double xCoor, double yCoor,
                    String description, String type, String openTime, String place, int mark, String phone, boolean isFavorite) {
            this.name = name;
            this.type = type;
            this.description = description;
            this.imgID = imgID;
            this.xCoor = xCoor;
            this.yCoor = yCoor;
            this.openTime = openTime;
            this.place = place;
            this.phone = phone;
            this.mark = mark;
            this.isFavorite = isFavorite;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public int getImgID() {
            return imgID;
        }

        public double getxCoor() {
            return xCoor;
        }

        public String getPhone() {
            return phone;
        }

        public boolean getIsFavorite() {
            return isFavorite;
        }

        public int getMark() {
            return mark;
        }

        public String getOpenTime() {
            return openTime;
        }

        public String getPlace() {
            return place;
        }

        public double getyCoor() {
            return yCoor;
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
                new String[]{"NAME", "IMAGE_RESOURCE_ID", "X_LOCATION", "Y_LOCATION", "DESCRIPTION", "TYPE", "OPEN_TIME", "PLACE", "MARK", "PHONE", "FAVORITE"}
                ,null, null, null, null, null);
        while (cursor.moveToNext()) {
            boolean isFavorite = cursor.getInt(10) == 1;
            site = new Site(cursor.getString(0), cursor.getInt(1), cursor.getDouble(2), cursor.getDouble(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getString(9), isFavorite);
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
                new String[]{"NAME", "IMAGE_RESOURCE_ID", "X_LOCATION", "Y_LOCATION", "DESCRIPTION", "TYPE", "OPEN_TIME", "PLACE", "MARK", "PHONE", "FAVORITE"}
                ,"FAVORITE = ?", new String[]{String.valueOf(1)}, null, null, null);
        while (cursor.moveToNext()) {
            boolean isFavorite = cursor.getInt(10) == 1;
            site = new Site(cursor.getString(0), cursor.getInt(1), cursor.getDouble(2), cursor.getDouble(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getString(9), isFavorite);
            list.add(site);
        }
        cursor.close();
        db.close();
        lock.readLock().unlock();
        return list;
    }

    public List<Site> getSitesByType(String type) {
        lock.readLock().lock();
        SQLiteDatabase db;
        db = getReadableDatabase();
        List<Site> list = new ArrayList<>();
        Site site;
        Cursor cursor = db.query("SITES",
                new String[]{"NAME", "IMAGE_RESOURCE_ID", "X_LOCATION", "Y_LOCATION", "DESCRIPTION", "TYPE", "OPEN_TIME", "PLACE", "MARK", "PHONE", "FAVORITE"}
                ,"TYPE = ?", new String[] {type}, null, null, null);
        while (cursor.moveToNext()) {
            boolean isFavorite = cursor.getInt(10) == 1;
            site = new Site(cursor.getString(0), cursor.getInt(1), cursor.getDouble(2), cursor.getDouble(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getString(9), isFavorite);
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
        db.update("SITES", favValues, "_id = ?", new String[] {Integer.toString(id+1)});
        db.close();
        lock.writeLock().unlock();
    }

    public void deleteFavoriteItem(int id) {
        lock.writeLock().lock();
        SQLiteDatabase db;
        db = getWritableDatabase();
        ContentValues favValues = new ContentValues();
        favValues.put("FAVORITE", 0);
        db.update("SITES", favValues, "_id = ?", new String[] {Integer.toString(id+1)});
        db.close();
        lock.writeLock().unlock();
    }

    public void insertPlan(List<Integer> plan, String title) {
        if (plan.isEmpty()) return;
        lock.writeLock().lock();
        SQLiteDatabase db;
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
            db.insertOrThrow("PLANS", null, planValues);
        } catch (SQLiteConstraintException e) {
            System.out.println("TITLE can't be same!!!");
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
            routeString = routeString.substring(0, routeString.length()-1);
            Site site;
            Cursor cursorSites = db.query("SITES",
                    new String[]{"NAME", "IMAGE_RESOURCE_ID", "X_LOCATION", "Y_LOCATION", "DESCRIPTION", "TYPE", "OPEN_TIME", "PLACE", "MARK", "PHONE", "FAVORITE"}
                    ,"_id in (" + routeString +")",
                    null, null, null, null);
            while (cursorSites.moveToNext()) {
                boolean isFavorite = cursorSites.getInt(10) == 1;
                site = new Site(cursorSites.getString(0), cursorSites.getInt(1), cursorSites.getFloat(2), cursorSites.getFloat(3),
                        cursorSites.getString(4), cursorSites.getString(5), cursorSites.getString(6), cursorSites.getString(7), cursorSites.getInt(8), cursorSites.getString(9), isFavorite);
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

    private void deleteTables() {
        lock.writeLock().lock();
        SQLiteDatabase db;
        db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS PLANS");
        db.execSQL("DROP TABLE IF EXISTS SITES");
        db.close();
        lock.writeLock().unlock();
    }
}
