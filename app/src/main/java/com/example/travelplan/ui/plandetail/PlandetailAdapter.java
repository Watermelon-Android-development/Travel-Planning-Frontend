package com.example.travelplan.ui.plandetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelplan.R;
import com.example.travelplan.TravelDatabaseHelper;

import java.util.List;


public class PlandetailAdapter extends BaseAdapter {

    private Context mContext;

    private List<TravelDatabaseHelper.Site> mDatas;

    private LayoutInflater mInflater;



    public PlandetailAdapter(Context mContext, List<TravelDatabaseHelper.Site> mDatas) {
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

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_plandetail_item, null);

            holder = new ViewHolder();
            holder.siteNumber = convertView.findViewById(R.id.number);
            holder.siteImage = convertView.findViewById(R.id.detail_image);
            holder.siteName = convertView.findViewById(R.id.site_name);
            holder.siteLocation = convertView.findViewById(R.id.site_location);
            holder.siteOpentime = convertView.findViewById(R.id.site_opentime);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        final TravelDatabaseHelper.Site dataBean = mDatas.get(position);
        if (dataBean != null) {
            holder.siteNumber.setText(mDatas.indexOf(dataBean)+1+"");
            holder.siteImage.setImageResource(dataBean.getImgID());
            holder.siteName.setText(dataBean.getName());
            holder.siteLocation.setText(dataBean.getPlace());
            holder.siteOpentime.setText(dataBean.getOpenTime());
        }
        return convertView;
    }

    class ViewHolder {

        public TextView siteNumber;
        public ImageView siteImage;
        public TextView siteName;
        public TextView siteLocation;
        public TextView siteOpentime;
    }
}
