package com.example.travelplan.ui.planlist;


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

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_planlist_item, null);

            holder = new ViewHolder();

            holder.checkboxOperateData = convertView.findViewById(R.id.plan_checkbox_operate_data);
            holder.textTitle = convertView.findViewById(R.id.plan_text_title);
            holder.textDesc = convertView.findViewById(R.id.plan_text_desc);
            holder.textDetail = convertView.findViewById(R.id.plan_go_to_detail);;

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        final TravelDatabaseHelper.Plan dataBean = mDatas.get(position);
        if (dataBean != null) {
            holder.textTitle.setText(dataBean.getTitle());
            List<Integer> pos = dataBean.getRoute();
            String route2 = pos.get(0).toString() + "-->";
            for (int i = 1; i<pos.size();i++){
                if (i == pos.size() - 1){
                    route2 = route2 + pos.get(i).toString();
                }
                else{
                    route2 = route2 + pos.get(i).toString() + "-->";
                }
            }
            holder.textDesc.setText(route2);
            View finalConvertView = convertView;
            holder.textDetail.setOnClickListener(v -> {

                Intent intent=new Intent(finalConvertView.getContext(), PlanDetailActivity.class);
                intent.putExtra("title", dataBean.getTitle());
                finalConvertView.getContext().startActivity(intent);
            });


            if (flage_edit) {
                holder.checkboxOperateData.setVisibility(View.VISIBLE);
            } else {
                holder.checkboxOperateData.setVisibility(View.GONE);
            }

            holder.checkboxOperateData.setChecked(dataBean.isCheck);


            holder.checkboxOperateData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dataBean.isCheck) {
                        dataBean.isCheck = false;
                    } else {
                        dataBean.isCheck = true;
                    }
                }
            });
        }
        return convertView;
    }

    class ViewHolder {

        public CheckBox checkboxOperateData;

        public TextView textTitle;

        public TextView textDesc;
        
        public Button textDetail;
    }

}
