package com.example.travelplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class description_page extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_page);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        /**
         * 只实现了点击爱心图标切换的功能
         * 现有问题：点击切换后，退出子页面重进会重新加载初始图标，不会保留之前切换后的内容
         * */
       Button bt_click=(Button) findViewById(R.id.lovebutton);
        bt_click.setOnClickListener(new View.OnClickListener() {
            int bt_flag=0;
            @Override
            public void onClick(View view) {
                switch (bt_flag){
                    case 0:
                        bt_click.setActivated(false);
                        bt_flag=1;
                        break;

                    case 1:
                        bt_click.setActivated(true);
                        bt_flag=0;
                        break;
                }
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