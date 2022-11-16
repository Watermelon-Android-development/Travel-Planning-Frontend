package com.example.travelplan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        boxAllClick = (Button) findViewById(R.id.boxAllClick);
        itemDelete = (Button) findViewById(R.id.itemDelete);

        itemDelete.setVisibility(View.INVISIBLE);
        boxAllClick.setVisibility(View.INVISIBLE);
        listView = (ListView) findViewById(R.id.listView);

        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {

            SaveCheckBox dataBean = new SaveCheckBox("" + i, "title", "describetion");
            mDatas.add(dataBean);
        }

        mAdapter = new FavoriteAdapter(this, mDatas);
        listView.setAdapter(mAdapter);

    }

    public void btnEditList1(View view) {

        btnEditList();

    }

    /**
     * 编辑、取消编辑
//     * @param view
     */
    public void btnEditList() {

        mAdapter. flage_edit= !mAdapter.flage_edit;

        if (mAdapter.flage_edit) {
            buttonEdit.setText("取消");
            itemDelete.setVisibility(View.VISIBLE);
            boxAllClick.setVisibility(View.VISIBLE);

        } else {
            buttonEdit.setText("编辑");
            itemDelete.setVisibility(View.INVISIBLE);
            boxAllClick.setVisibility(View.INVISIBLE);
    }

        mAdapter.notifyDataSetChanged();
    }

    /**
     * 全选，取消全选
     * @param view
     */
    public void btnSelectAllList(View view) {

        mAdapter.flage_visible = !mAdapter.flage_visible;
        if (mAdapter.flage_visible) {
            boxAllClick.setText("全选");
            for (int i = 0; i < mDatas.size(); i++) {
                mDatas.get(i).isCheck = true;
            }}
        else{
            boxAllClick.setText("取消全选");
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
        builder.setTitle("删除");
        builder.setMessage("确认删除吗？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

}




