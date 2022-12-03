package com.example.travelplan;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class description_page extends AppCompatActivity {
    private int position;

    private final TravelDatabaseHelper travelDatabaseHelper=new TravelDatabaseHelper(this);

    private List<TravelDatabaseHelper.Site> data;

    private boolean btn_flag;


    //详情页面渲染
    private class page_render extends AsyncTask<Void,Void,Boolean> {
        @Override
        protected void onPreExecute(){

        }

        @Override
        protected Boolean doInBackground(Void... voids){
            data=travelDatabaseHelper.getAllSites();

            return true;
        }

        @Override
        protected void onPostExecute(Boolean success){
            //String name
            String rl_name=data.get(position).getName();
            Log.e("description","Name: " + rl_name);
            TextView name=findViewById(R.id.item_name);
            name.setText(rl_name);

            //int imgID
            int image_index= data.get(position).getImgID();
            ImageView image=findViewById(R.id.description_image);
            image.setImageResource(image_index);



            //int mark
            int rl_mark=data.get(position).getMark();
            RatingBar mark=findViewById(R.id.item_rating);
            mark.setRating(rl_mark);

            //String type
            String rl_type=data.get(position).getType();
            TextView type=findViewById(R.id.item_kind);
            type.setText(rl_type);

            //String place
            String rl_place=data.get(position).getPlace();
            TextView place=findViewById(R.id.item_location);
            place.setText(rl_place);

            //String phone
            String rl_phone=data.get(position).getPhone();
            TextView phone=findViewById(R.id.item_phone);
            phone.setText(rl_phone);

            //String openTime
            String rl_openTime=data.get(position).getOpenTime();
            TextView openTime=findViewById(R.id.item_time);
            openTime.setText(rl_openTime);

            //String description
            int rl_description=data.get(position).getDescription();
            TextView description=findViewById(R.id.description);
            description.setText(rl_description);

            //boolean isFavorite
            Boolean favouriteflag=data.get(position).getIsFavorite();
            Button favorite_btn=findViewById(R.id.lovebutton);
            if(favouriteflag){
                favorite_btn.setActivated(true);
            }
            else{
                favorite_btn.setActivated(false);
            }


        }



    }

    //favorite按钮
    private class btn_click extends AsyncTask<Void,Void,Boolean> {
        @Override
        protected void onPreExecute(){

        }

        @Override
        protected Boolean doInBackground(Void... voids){
            data=travelDatabaseHelper.getAllSites();
            Boolean favouriteflag_1=data.get(position).getIsFavorite(); //默认是false，灰色

            return favouriteflag_1;
        }

        @Override
        protected void onPostExecute(Boolean favouriteflag_1){
            Button favorite_btn=findViewById(R.id.lovebutton);
            if(favouriteflag_1){
                favorite_btn.setActivated(false); //按钮变灰色
                favorite_btn.setText("FAVORITE");
                //Log.e("取消收藏","取消前的flag = "+favouriteflag_1);
                travelDatabaseHelper.deleteFavoriteItem(position+1);
            }
            else{
                favorite_btn.setActivated(true); //按钮变紫色
                favorite_btn.setText("FAVORITED !");
                // Log.e("增加收藏","增加前的flag = "+favouriteflag_1);
                travelDatabaseHelper.addFavoriteItem(position+1);
            }
        }



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_page);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent i=getIntent();
        position=i.getIntExtra("Position",-1);
        Log.e("description","position:" + position);

        new page_render().execute();




       Button bt_click=findViewById(R.id.lovebutton);
        bt_click.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new btn_click().execute();
            }
        });




    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case  android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}