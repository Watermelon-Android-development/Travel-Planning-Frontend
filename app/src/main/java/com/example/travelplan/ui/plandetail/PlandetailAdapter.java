package com.example.travelplan.ui.plandetail;
//
//public class FavoriteAdapter {
//}

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.travelplan.R;

import java.util.List;


public class PlandetailAdapter extends BaseAdapter {

    private Context mContext;

    private List<PlanDetail> mDatas;

    private LayoutInflater mInflater;



    public PlandetailAdapter(Context mContext, List<PlanDetail> mDatas) {
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

        ViewHolder holder = null;

        if (convertView == null) {
            // 下拉项布局
            convertView = mInflater.inflate(R.layout.activity_plandetail_item, null);

            holder = new ViewHolder();

            holder.siteName = (TextView) convertView.findViewById(R.id.site_name);
            holder.siteLocation = (TextView) convertView.findViewById(R.id.site_location);
            holder.siteOpentime = (TextView) convertView.findViewById(R.id.site_opentime);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        final PlanDetail dataBean = mDatas.get(position);
        if (dataBean != null) {
            holder.siteName.setText(dataBean.getName());
            holder.siteLocation.setText(dataBean.getLocation());
            holder.siteOpentime.setText(dataBean.getOpentime());
        }
        return convertView;
    }

    class ViewHolder {


        public TextView siteName;
        public TextView siteLocation;
        public TextView siteOpentime;
    }
}
