package com.example.travelplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.os.Handler;
import java.util.List;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;
import com.example.travelplan.ui.planlist.PlanlistAdapter;

public class PlanlistActivity extends AppCompatActivity{


    private Button boxAllClick;
    private Button itemDelete;
    private ListView listView;

    private List<TravelDatabaseHelper.Plan> mDatas;

    private final TravelDatabaseHelper travelDatabaseHelper = new TravelDatabaseHelper(this);
    private PlanlistAdapter mAdapter;
    private Handler handler;


    private class GetPlanTask extends AsyncTask<TravelDatabaseHelper.Plan, Void, Boolean> {
        @Override
        protected void onPreExecute(){
        }

        @Override
        protected Boolean doInBackground(TravelDatabaseHelper.Plan... plans) {
            try {
                mDatas = travelDatabaseHelper.getAllPlans();
                mAdapter = new PlanlistAdapter(PlanlistActivity.this, mDatas);
            } catch (SQLiteException e){
                return false;
            }
            return true;
        }
        @Override
        protected void onPostExecute(Boolean success){
            if(success){
                listView = findViewById(R.id.planlistView);
                listView.setDivider(null);
                listView.setAdapter(mAdapter);
                Toast.makeText(PlanlistActivity.this, "All plans", Toast.LENGTH_SHORT);
            }
            else Toast.makeText(PlanlistActivity.this, "Database unavailable", Toast.LENGTH_SHORT);
        }
    }

    private class DeletePlanTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute(){
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                for (int i = 0,len=mDatas.size(); i < len; i++) {
                    if (len==0)return true;
                    if (mDatas.get(i).isCheck){
                        travelDatabaseHelper.deletePlans(mDatas.get(i).getTitle());
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
                listView.setAdapter(mAdapter);
                Toast.makeText(PlanlistActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(PlanlistActivity.this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planlist);


        boxAllClick = (Button) findViewById(R.id.planboxAllClick);
        itemDelete = (Button) findViewById(R.id.planitemDelete);
        handler = new Handler();

        itemDelete.setVisibility(View.INVISIBLE);
        boxAllClick.setVisibility(View.INVISIBLE);


        new GetPlanTask().execute();
        
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.right_top_planlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit) {

            mAdapter.flage_edit= !mAdapter.flage_edit;

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
     * Select_All and Unselect_all
     * @param view
     */
    public void btnSelectAllList(View view) {

        mAdapter.flage_visible = !mAdapter.flage_visible;
        if (mAdapter.flage_visible) {
            boxAllClick.setText("UNSELECT ALL");
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
     * Deletion
     * @param view
     */
    public void btnDeleteList(View view) {

        showDialog(view);

    }

    public void showDialog(View view){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("DELETE");
        builder.setMessage("Are you sure to delete all the options?");
        builder.setPositiveButton("CONFIRM", (dialog, which) -> {
            new DeletePlanTask().execute();
            mAdapter.notifyDataSetChanged();
            if (dialog != null) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("CANCEL", (dialog, which) -> {
            if (dialog != null) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }


}
