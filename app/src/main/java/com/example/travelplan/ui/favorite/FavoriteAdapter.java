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
import com.example.travelplan.ui.favorite.SaveCheckBox;
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
            holder.textTitle = (TextView) convertView.findViewById(R.id.text_title);
            holder.textDesc = (TextView) convertView.findViewById(R.id.text_desc);
            holder.material_item_img=(ImageView) convertView.findViewById(R.id.material_item_img);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        final TravelDatabaseHelper.Site dataBean = mDatas.get(position);
        if (dataBean != null) {
            holder.textTitle.setText(dataBean.getName());
            holder.textDesc.setText(dataBean.getPlace());

            int image_index= dataBean.getImgID();
            holder.material_item_img.setImageResource( image_index);





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
        public ImageView material_item_img;


    }



}
