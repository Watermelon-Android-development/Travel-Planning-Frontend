package com.example.travelplan;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelplan.ui.plandetail.PlandetailAdapter;

import java.util.List;

public class PlanDetailActivity extends AppCompatActivity {

    private ListView listView;
    private PlandetailAdapter mAdapter;
    private final TravelDatabaseHelper travelDatabaseHelper = new TravelDatabaseHelper(this);

    private class GetPlanDetailTask extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute(){
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                Intent myintent = getIntent();
                String planTitle = myintent.getStringExtra("title");
                List<TravelDatabaseHelper.Site> mDatas = travelDatabaseHelper.getPlanDetails(planTitle);
                mAdapter = new PlandetailAdapter(PlanDetailActivity.this, mDatas);
            } catch (SQLiteException e){
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success){
            listView.setAdapter(mAdapter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plandetail);
        listView = (ListView) findViewById(R.id.plandetailView);

        GetPlanDetailTask getPlanDetail = new GetPlanDetailTask();
        getPlanDetail.execute();
    }
}
