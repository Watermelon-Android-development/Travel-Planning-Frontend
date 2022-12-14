package com.example.travelplan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class Helper extends AppCompatActivity {
    private TextView h1, h2;
    private Context mContext;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);

        h1 = (TextView) findViewById(R.id.h1);
        h2 = (TextView) findViewById(R.id.h2);
        mContext = Helper.this;
        h1 = (TextView) findViewById(R.id.h1);
        h2 = (TextView) findViewById(R.id.h2);

        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert = null;
                builder = new AlertDialog.Builder(mContext);
                alert = builder
                        .setTitle("What is this?")
                        .setMessage("This is a app used to find the resorts and recommend the best route for you!")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(mContext, "OK", Toast.LENGTH_SHORT).show();
                            }
                        }).create();
                alert.show();
            }
        });


        h2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert = null;
                builder = new AlertDialog.Builder(mContext);
                alert = builder
                        .setTitle("How to check map?")
                        .setMessage("You can tap the map button on the bottom navigation bar. Then you can choose the place you want to visit and see the route.")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(mContext, "OK", Toast.LENGTH_SHORT).show();
                            }
                        }).create();
                alert.show();
            }
        });

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
