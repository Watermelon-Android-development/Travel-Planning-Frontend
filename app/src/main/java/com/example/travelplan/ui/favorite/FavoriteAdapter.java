package com.example.travelplan.ui.favorite;
//
//public class FavoriteAdapter {
//}
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.travelplan.ui.favorite.SaveCheckBox;
import com.example.travelplan.R;


import java.util.List;


public class FavoriteAdapter extends BaseAdapter {

    private Context mContext;

    private List<SaveCheckBox> mDatas;

    private LayoutInflater mInflater;

    public boolean flage_edit = false;
    public boolean flage_visible = false;



    public FavoriteAdapter(Context mContext, List<SaveCheckBox> mDatas) {
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
            convertView = mInflater.inflate(R.layout.activity_favorite_list_item, null);

            holder = new ViewHolder();

            holder.checkboxOperateData = (CheckBox) convertView.findViewById(R.id.checkbox_operate_data);
            holder.textTitle = (TextView) convertView.findViewById(R.id.text_title);
            holder.textDesc = (TextView) convertView.findViewById(R.id.text_desc);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        final SaveCheckBox dataBean = mDatas.get(position);
        if (dataBean != null) {
            holder.textTitle.setText(dataBean.title);
            holder.textDesc.setText(dataBean.desc);


            // 根据isSelected来设置checkbox的显示状况
            if (flage_edit) {
                holder.checkboxOperateData.setVisibility(View.VISIBLE);
            } else {
                holder.checkboxOperateData.setVisibility(View.GONE);
            }

            holder.checkboxOperateData.setChecked(dataBean.isCheck);

            //注意这里设置的不是onCheckedChangListener，还是值得思考一下的
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
    }
}
