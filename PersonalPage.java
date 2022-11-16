package com.example.travelplan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalPage extends AppCompatActivity{
    private TextView setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_page);
        setting = findViewById(R.id.setting);

    }




//这个是跳转到collection的
//    public void ToCollection(View view) {
//        Intent intent = new Intent(this, Collection.class);
//        this.startActivity(intent);
//    }
    //这是跳转到planlist的
//public void ToPlanList(View view) {
//        Intent intent = new Intent(this, Planlist.class);
//        this.startActivity(intent);
//    }
}