package com.example.travelplan.ui.map;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelplan.R;
import com.example.travelplan.TravelDatabaseHelper;

import java.util.List;


public class display_window_Adapter extends RecyclerView.Adapter<display_window_Adapter.ViewHolder> {
    //private Context mContext;

    private List<TravelDatabaseHelper.Site> mDatas;
    public Context context;
    private List<Integer> rout_list;

    //private LayoutInflater mInflater;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View userView;
        TextView number;
        TextView image_name;
        ImageView image;
        public ViewHolder(View view){
            super(view);
            userView=view;
            number=view.findViewById(R.id.display_item_number);
            image_name=view.findViewById(R.id.display_item_name);
            image=view.findViewById(R.id.display_item_image);
        }
    }
    public display_window_Adapter(List<TravelDatabaseHelper.Site> data, Context context,List<Integer> rout_list){
        this.mDatas=data;
        this.context=context;
        this.rout_list=rout_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        //View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.map_displaywindow_item,parent,false);
        View view=View.inflate(context,R.layout.map_displaywindow_item,null);
        //final ViewHolder viewHolder=new ViewHolder(view);
        //return viewHolder;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
            holder.number.setText(String.valueOf(position+1));
            holder.image.setImageResource(mDatas.get(rout_list.get(position)-1).getImgID());
            holder.image_name.setText(mDatas.get(rout_list.get(position)-1).getName());

        }


    @Override
    public int getItemCount(){
                return rout_list.size();

    }
}


