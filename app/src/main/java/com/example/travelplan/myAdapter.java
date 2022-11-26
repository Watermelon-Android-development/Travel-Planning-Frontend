package com.example.travelplan;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {

  private List<TravelDatabaseHelper.Site> data;
  public Context context;

  public myAdapter(List<TravelDatabaseHelper.Site> data, Context context) {
    this.data = data;
    this.context = context;
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view=View.inflate(context,R.layout.fragment_home_item,null);

      return  new MyViewHolder(view);
  }

//    private Resources getResources() {
//// TODO Auto-generated method stub
//        Resources mResources = null;
//        mResources = getResources();
//        return mResources;
//    }
  @Override
  public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      holder.tv.setText(data.get(position).getName());
      int image_index= data.get(position).getId_img();

      holder.iv.setImageDrawable(context.getResources().getDrawable( R.drawable.k4k_2878019));
//      String path= Environment.getExternalStorageDirectory()+ File.separator+"k4k_2878019";
//      String path2=".../res/drawable/k4k_2878019.jpg";
//      Log.e("test", "onBindViewHolder: "+image_index);
//      R.drawable.k4k_2878019
//      Bitmapbm = BitmapFactory.decodeResource(getResources(),R.drawable.img);
//      Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
      Bitmap bm = BitmapFactory.decodeResource(context.getResources(),  R.drawable.k4k_2878019);
      holder.iv.setImageBitmap(bm);
      holder.iv.setImageResource( R.drawable.k4k_2878019);


  }

  @Override
  public int getItemCount() {
    return data==null ? 0 : data.size();
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {
      private TextView tv;
      private ImageView iv;
      public MyViewHolder(@NonNull View itemView) {
          super(itemView);
          tv=itemView.findViewById(R.id.tv);
          iv=itemView.findViewById(R.id.iv);

          itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if (mOnItemClickListListener !=null){
                      mOnItemClickListListener.onRecyclerItemClick(getBindingAdapterPosition());
                  }
              }
          });

    }
  }
  private OnRecyclerItemClickListener mOnItemClickListListener;

  public void setRecyclerItemClickListener (OnRecyclerItemClickListener listener){
      mOnItemClickListListener = listener;
  }
  public interface OnRecyclerItemClickListener{
      void onRecyclerItemClick(int position);

  }
}
