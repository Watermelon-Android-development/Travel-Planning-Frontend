package com.example.travelplan.ui.favorite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.travelplan.TravelDatabaseHelper;
import com.example.travelplan.myAdapter;
import com.example.travelplan.R;


import java.util.List;


public class FavoriteAdapter extends BaseAdapter {

    private Context mContext;

    private List<TravelDatabaseHelper.Site> mDatas;

    private LayoutInflater mInflater;

    public boolean flage_edit = false;
    public boolean flage_visible = false;



    public FavoriteAdapter(Context mContext, List<TravelDatabaseHelper.Site> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;

        mInflater = LayoutInflater.from(this.mContext);

    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public TravelDatabaseHelper.Site getItem(int i) {
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
            //
            convertView = mInflater.inflate(R.layout.activity_favorite_list_item, null);

            holder = new ViewHolder();

            holder.checkboxOperateData = (CheckBox) convertView.findViewById(R.id.checkbox_operate_data);
            holder.title = (TextView) convertView.findViewById(R.id.text_title);
            holder.rank = (TextView) convertView.findViewById(R.id.rank);
            holder.kind = (TextView) convertView.findViewById(R.id.kind);
            holder.favorite_iamge=(ImageView) convertView.findViewById(R.id.favorite_iamge);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        final TravelDatabaseHelper.Site dataBean = mDatas.get(position);
        if (dataBean != null) {
            String mark = String.valueOf(dataBean.getMark());
            holder.title.setText(dataBean.getName());
            holder.rank.setText("Mark: "+mark);
            holder.kind.setText("Type: "+dataBean.getType());

            int image_index= dataBean.getImgID();
            holder.favorite_iamge.setImageResource( image_index);





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

        public TextView title;
        public TextView rank;
        public TextView kind;
        public ImageView favorite_iamge;


    }



}
