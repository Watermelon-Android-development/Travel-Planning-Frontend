package com.example.travelplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;

import android.os.Handler;

import java.util.List;

import android.widget.ListView;

import android.widget.Button;

import com.example.travelplan.ui.favorite.SaveCheckBox;
import com.example.travelplan.ui.favorite.FavoriteAdapter;

public class Favorite extends AppCompatActivity{


    private Button buttonEdit;
    private Button boxAllClick;
    private Button itemDelete;


    private ListView listView;

    private List<SaveCheckBox> mDatas;

    private FavoriteAdapter mAdapter;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


//        buttonEdit = findViewById(R.id.edit);
        boxAllClick = (Button) findViewById(R.id.boxAllClick);
        itemDelete = (Button) findViewById(R.id.itemDelete);
        handler = new Handler();

        itemDelete.setVisibility(View.INVISIBLE);
        boxAllClick.setVisibility(View.INVISIBLE);
        listView = (ListView) findViewById(R.id.listView);

        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {

            SaveCheckBox dataBean = new SaveCheckBox("" + i, "title"+ i, "describetion"+ i);
            mDatas.add(dataBean);
        }


        mAdapter = new FavoriteAdapter(this, mDatas);
        listView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.right_top_favorite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit) {

            mAdapter. flage_edit= !mAdapter.flage_edit;

            if (mAdapter.flage_edit) {
                item.setTitle("CANCEL");
                itemDelete.setVisibility(View.VISIBLE);
                boxAllClick.setVisibility(View.VISIBLE);

            } else {
                item.setTitle("EDIT");
                itemDelete.setVisibility(View.INVISIBLE);
                boxAllClick.setVisibility(View.INVISIBLE);
            }

            mAdapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * 全选，取消全选
     * @param view
     */
    public void btnSelectAllList(View view) {

        mAdapter.flage_visible = !mAdapter.flage_visible;
        if (mAdapter.flage_visible) {
            boxAllClick.setText("DESELECT ALL");
            for (int i = 0; i < mDatas.size(); i++) {
                mDatas.get(i).isCheck = true;
            }}
        else{
            boxAllClick.setText("SELECT ALL");
            for (int i = 0; i < mDatas.size(); i++) {
                mDatas.get(i).isCheck = false;
            }
        }

        mAdapter.notifyDataSetChanged();

    }

    /**
     * 删除---没有实现，只是先写在这里
     * @param view
     */
    public void btnDeleteList(View view) {

        showDialog(view);

    }

    public void showDialog(View view){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("DELETE");
        builder.setMessage("Are you sure to delete all the options?");
        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                for (int i = 0,len=mDatas.size(); i < len; i++) {
                    if (mDatas.get(i).isCheck==true){
                        mDatas.remove(i);
                        len--;
                        i--;
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

}




