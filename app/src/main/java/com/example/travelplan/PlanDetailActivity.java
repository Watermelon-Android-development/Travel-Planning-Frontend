package com.example.travelplan;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelplan.ui.plandetail.PlanDetail;
import com.example.travelplan.ui.plandetail.PlandetailAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlanDetailActivity extends AppCompatActivity {

    private ListView listView;
    private PlandetailAdapter mAdapter;
    private List<PlanDetail> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plandetail);
        listView = (ListView) findViewById(R.id.plandetailView);
        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {

            PlanDetail dataBean = new PlanDetail();
            dataBean.setName("site" + i);
            dataBean.setLocation("location" + i);
            dataBean.setOpentime("opentime" + i);
            mDatas.add(dataBean);
        }


        mAdapter = new PlandetailAdapter(this, mDatas);
        listView.setAdapter(mAdapter);
    }
}