package com.example.travelplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;

import android.os.Handler;

import java.util.List;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelplan.ui.favorite.SaveCheckBox;
import com.example.travelplan.ui.favorite.FavoriteAdapter;
import com.example.travelplan.ui.planlist.PlanlistAdapter;

public class Favorite extends AppCompatActivity{


    private Button buttonEdit;
    private Button boxAllClick;
    private Button itemDelete;


    private ListView listView;

    private List<TravelDatabaseHelper.Site> mDatas;
    private final TravelDatabaseHelper travelDatabaseHelper = new TravelDatabaseHelper(this);

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



        itemDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btnDeleteList();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Favorite.this,"XXX",Toast.LENGTH_SHORT).show();
            }
        });
//        mDatas = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//
//            SaveCheckBox dataBean = new SaveCheckBox("" + i, "title"+ i, "describetion"+ i);
//            mDatas.add(dataBean);
//        }


//        mAdapter = new FavoriteAdapter(this, mDatas);
//        listView.setAdapter(mAdapter);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        new ShowFavorite().execute();
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

        switch (item.getItemId()){
            case  android.R.id.home:
                this.finish();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }


    /**
     * DESELECT ALL,SELECT ALL
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
     * deletion
     *
     */
    public void btnDeleteList() {

        showDialog();

    }

    public void showDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("DELETE");
        builder.setMessage("Are you sure to delete all the options?");
        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//                for (int i = 0,len=mDatas.size(); i < len; i++) {
//                    if (mDatas.get(i).isCheck==true){
//                        mDatas.remove(i);
//                        len--;
//                        i--;
//                    }
//                }
                new DeleteFavoriteItem().execute();
                mAdapter.notifyDataSetChanged();
                if (dialog != null) {
                    dialog.dismiss();
                }
            }

        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public void jumpTodesc(View view,int position){
        Intent inte=new Intent(this.getApplicationContext(), description_page.class);
        inte.putExtra("Position",position);
        startActivity(inte);
    }

    private class ShowFavorite extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute(){
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                mDatas = travelDatabaseHelper.getAllFavoriteSites();
                mAdapter = new FavoriteAdapter(Favorite.this, mDatas);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                        Toast.makeText(Favorite.this,"Jump to"+mDatas.get(i).getName(),Toast.LENGTH_SHORT).show();

//                        Log.e("favorite ","get  "+(mDatas.get((i)).getId()-1));
                        jumpTodesc(view,mDatas.get((i)).getId()-1);
                    }
                });

            } catch (SQLiteException e){
                return false;
            }
            return true;
        }
        @Override
        protected void onPostExecute(Boolean success){

            if(success){

                listView.setAdapter(mAdapter);
                Toast.makeText(Favorite.this, "All Favorites Shows", Toast.LENGTH_SHORT);
            }
            else Toast.makeText(Favorite.this, "Database unavailable", Toast.LENGTH_SHORT);
        }
    }

    private class DeleteFavoriteItem extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute(){
        }

        @Override
        protected Boolean doInBackground(String... strings) {


            try {

                for (int i = 0,len=mDatas.size(); i < len; i++) {
//                    Log.e("GET"," "+i);
//                    Log.e("LEN"," "+len);
                    if (len==0)return true;
                    if (mDatas.get(i).isCheck){
                        travelDatabaseHelper.deleteFavoriteItem(mDatas.get(i).getId());
                        mDatas.remove(i);
                        len--;
                        i--;
                    }

                }
            } catch (SQLiteException e){
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success){
            if (success) {
//                btnDeleteList();
                listView.setAdapter(mAdapter);
                Toast.makeText(Favorite.this, "Delete successfully", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(Favorite.this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }

    }


}




