package com.example.travelplan.ui.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelplan.HomePage;
import com.example.travelplan.R;
import com.example.travelplan.TravelDatabaseHelper;
import com.example.travelplan.collection_item;
import com.example.travelplan.databinding.FragmentHomeBinding;
import com.example.travelplan.description_page;
import com.example.travelplan.myAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
//    private List<collection_item> data;

    private TravelDatabaseHelper travelDatabaseHelper;
//    travelDatabaseHelper.()

    ProgressBar progressBar; // 进度条
//    private FragmentHomeBinding binding;
    private List<TravelDatabaseHelper.Site> data;
    RecyclerView recyclerView;
    myAdapter myAdapter;
    View root;
    private String kindResult;


    class getAllSites extends AsyncTask<Void, Void, Boolean> {

        // 方法1：onPreExecute（）
        // 作用：执行 线程任务前的操作
        @Override
        protected void onPreExecute() {
//            text.setText("加载中");
            // 执行前显示提示
        }

        // 方法2：doInBackground（）
        // 作用：接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
        // 此处通过计算从而模拟“加载进度”的情况
        @Override
        protected Boolean doInBackground(Void... voids) {
            data = travelDatabaseHelper.getAllSites();
            myAdapter= new myAdapter(data,getContext());
            Log.e("test","test_home"+ data.get(0).getImgID());
            Log.e("test","test_home"+ data.get(1).getImgID());

            myAdapter.setRecyclerItemClickListener(new myAdapter.OnRecyclerItemClickListener() {
                @Override
                public void onRecyclerItemClick(int position) {
                    Log.e("test", "onRecyclerItemClick: "+position);
                    startActivity2(root,position);
                }
            });

            return true;
        }



//         方法4：onPostExecute（）
//         作用：接收线程任务执行结果、将执行结果显示到UI组件
        @Override
        protected void onPostExecute(Boolean success) {
            // 执行完毕后，则更新UI
//            text.setText("加载完毕");
//            data = travelDatabaseHelper.getAllSites();

            recyclerView.setAdapter(myAdapter);
//            recyclerView.postInvalidate();

        }

        // 方法5：onCancelled()
        // 作用：将异步任务设置为：取消状态
        @Override
        protected void onCancelled() {

            progressBar.setProgress(0);

        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        travelDatabaseHelper= new TravelDatabaseHelper(this.getActivity());

//        data= new ArrayList<>();
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);

//        View root = binding.getRoot();
        root = binding.getRoot();
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


//        for (int i=0;i<100;i++){
//            collection_item item =new collection_item();
//            item.setName("index"+i);
//            data.add(item);e
//        }


//        RecyclerView recyclerView= root.findViewById(R.id.rv);
        recyclerView= root.findViewById(R.id.rv);

        GridLayoutManager gridLayoutManager= new GridLayoutManager(this.getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);


//        myAdapter= new myAdapter(data,this.getContext());

        new getAllSites().execute();

//        myAdapter myAdapter= new  myAdapter(data,this.getContext());
//        myAdapter= new  myAdapter(data,this.getContext());
//        recyclerView.setAdapter(myAdapter);

//        myAdapter.setRecyclerItemClickListener(new myAdapter.OnRecyclerItemClickListener() {
//            @Override
//            public void onRecyclerItemClick(int position) {
//                Log.e("test", "onRecyclerItemClick: "+position);
//                startActivity2(root);
//            }
//        });

        return root;


    }
    
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.right_top_home,menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case  R.id.all:
//                kindResult="all";
                new getAllSites().execute();
                Toast.makeText(this.getContext(),"There are all location",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.zoo:
                kindResult="zoo";
                new ShowKind().execute();
                Toast.makeText(this.getContext(),"The selected location category is zoo",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.scenery:
                kindResult="scenery";
                new ShowKind().execute();
                Toast.makeText(this.getContext(),"The selected location category is scenery",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.playground:
                kindResult="playground";
                new ShowKind().execute();
                Toast.makeText(this.getContext(),"The selected location category is playground",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.museum:
                kindResult="museum";
                new ShowKind().execute();
                Toast.makeText(this.getContext(),"The selected location category is museum",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.temple:
                kindResult="temple";
                new ShowKind().execute();
                Toast.makeText(this.getContext(),"The selected location category is temple",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.mall:
                kindResult="mall";
                new ShowKind().execute();
                Toast.makeText(this.getContext(),"The selected location category is mall",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.church:
                kindResult="church";
                new ShowKind().execute();
                Toast.makeText(this.getContext(),"The selected location category is church",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.block:
                kindResult="block";
                new ShowKind().execute();
                Toast.makeText(this.getContext(),"The selected location category is block",Toast.LENGTH_SHORT).show();
                break;

        }


        return super.onOptionsItemSelected(item);
    }


    class ShowKind extends AsyncTask<Void,Void,Boolean> {
//        String title;
        @Override
        protected void onPreExecute(){
//        AllSite=travelDatabaseHelper.getAllFavoriteSites();

        }
        @Override
        protected Boolean doInBackground(Void... voids){
            data = travelDatabaseHelper.getSitesByType(kindResult);
            myAdapter= new myAdapter(data,getContext());

            myAdapter.setRecyclerItemClickListener(new myAdapter.OnRecyclerItemClickListener() {
                @Override
                public void onRecyclerItemClick(int position) {
                    startActivity2(root,position);
                }
            });
            return true;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute (Boolean success){

            recyclerView.setAdapter(myAdapter);
        }



    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void startActivity2(View view,int position){
        Intent inte=new Intent(this.getContext(), description_page.class);
        inte.putExtra("Position",position);
        startActivity(inte);
    }
}
