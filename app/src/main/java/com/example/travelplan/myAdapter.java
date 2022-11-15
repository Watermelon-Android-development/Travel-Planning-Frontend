package com.example.travelplan;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {

  private List<collection_item> data;
  public Context context;

  public myAdapter(List<collection_item> data, Context context) {
    this.data = data;
    this.context = context;
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view=View.inflate(context,R.layout.homepage_item,null);

      return  new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      holder.tv.setText(data.get(position).getName());
  }

  @Override
  public int getItemCount() {
    return data==null ? 0 : data.size();
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {
      private TextView tv;
      public MyViewHolder(@NonNull View itemView) {
          super(itemView);
          tv=itemView.findViewById(R.id.tv);

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
