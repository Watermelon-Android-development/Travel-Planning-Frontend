package com.example.travelplan.ui.planlist;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.travelplan.PlanDetailActivity;
import com.example.travelplan.TravelDatabaseHelper;
import com.example.travelplan.R;

import java.util.List;


public class PlanlistAdapter extends BaseAdapter {

    private Context mContext;

    private List<TravelDatabaseHelper.Plan> mDatas;

    private LayoutInflater mInflater;

    public boolean flage_edit = false;
    public boolean flage_visible = false;



    public PlanlistAdapter(Context mContext, List<TravelDatabaseHelper.Plan> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;

        mInflater = LayoutInflater.from(this.mContext);

    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_planlist_item, null);

            holder = new ViewHolder();

            holder.checkboxData = convertView.findViewById(R.id.plan_checkbox_operate_data);
            holder.textTitle = convertView.findViewById(R.id.plan_text_title);
            holder.textDesc = convertView.findViewById(R.id.sitenumber);
            holder.textDetail = convertView.findViewById(R.id.plan_go_to_detail);;

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        final TravelDatabaseHelper.Plan data = mDatas.get(position);
        if (data != null) {
            holder.textTitle.setText(data.getTitle());
            holder.textDesc.setText(data.getRoute().size() + " dest. in total");
            View finalConvertView = convertView;
            holder.textDetail.setOnClickListener(v -> {

                Intent intent=new Intent(finalConvertView.getContext(), PlanDetailActivity.class);
                intent.putExtra("title", data.getTitle());
                finalConvertView.getContext().startActivity(intent);
            });


            if (flage_edit) {
                holder.checkboxData.setVisibility(View.VISIBLE);
            } else {
                holder.checkboxData.setVisibility(View.GONE);
            }

            holder.checkboxData.setChecked(data.isCheck);


            holder.checkboxData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.isCheck) {
                        data.isCheck = false;
                    } else {
                        data.isCheck = true;
                    }
                }
            });
        }
        return convertView;
    }

    class ViewHolder {

        public CheckBox checkboxData;

        public TextView textTitle;

        public TextView textDesc;
        
        public Button textDetail;
    }

}
