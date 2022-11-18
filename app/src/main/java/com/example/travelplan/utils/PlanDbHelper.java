package com.example.travelplan.utils;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.travelplan.bean.Plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class PlanDbHelper extends SQLiteOpenHelper {

    //定义学生表
    public static final String DB_NAME = "tb_plan";
    //创建表
    private static final String CREATE_PLAN_DB = "create table tb_Plan(" +
            "date text," +
            "route text)";

    public PlanDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PLAN_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // save
    public void savePlan(Plan Plan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date",Plan.getDate());
        values.put("route",Plan.getRoute());
        db.insert(DB_NAME,null,values);
        values.clear();
    }

    // deletion
    public int deletePlan(Plan plan) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("tb_Plan", "route=?", new String[]{String.valueOf(plan.getRoute())});
    }

    // all plans
    @SuppressLint("Range")
    public ArrayList<Map<String, Object>> getAllPlans() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Map<String, Object>> listPlans = new ArrayList<Map<String, Object>>();
        Cursor cursor = db.query("tb_Plan", null, null, null, null, null,null);

        int resultCounts = cursor.getCount();
        if (resultCounts == 0 ) {
            return null;
        } else {
            while (cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("date", cursor.getString(cursor.getColumnIndex("date")));
                map.put("route", cursor.getString(cursor.getColumnIndex("route")));

                listPlans.add(map);
            }
            return listPlans;
        }
    }
}

