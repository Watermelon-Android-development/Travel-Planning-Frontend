package com.example.travelplan.ui.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.travelplan.R;
import com.example.travelplan.TravelDatabaseHelper;
import com.example.travelplan.databinding.FragmentHomeBinding;
import com.example.travelplan.description_page;
import com.example.travelplan.myAdapter;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TravelDatabaseHelper travelDatabaseHelper;
    private List<TravelDatabaseHelper.Site> data;
    RecyclerView recyclerView;
    myAdapter myAdapter;
    View root;
    private String kindResult;


    class getAllSites extends AsyncTask<Void, Void, Boolean> {

        // method 1：onPreExecute（）
        // effect：execute before thread task
        @Override
        protected void onPreExecute() {
        }

        // method 2：doInBackground（）
        // effect：accept input parameter、execute operations、return result
        @Override
        protected Boolean doInBackground(Void... voids) {
            data = travelDatabaseHelper.getAllSites();// load data

            myAdapter= new myAdapter(data,getContext());
            myAdapter.setRecyclerItemClickListener(new myAdapter.OnRecyclerItemClickListener() {
                @Override
                public void onRecyclerItemClick(int position) {
                    startActivity2(root,data.get(position).getId()-1); //jump to specification page
                }
            });

            return true;
        }



//         method 4：onPostExecute（）
//         effect：accept thread task result、return result to UI component
        @Override
        protected void onPostExecute(Boolean success) {
            recyclerView.setAdapter(myAdapter);
        }

        // method 5：onCancelled()
        // effect：cancel async task
        @Override
        protected void onCancelled() {
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        travelDatabaseHelper= new TravelDatabaseHelper(this.getActivity());

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        root = binding.getRoot();
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        recyclerView= root.findViewById(R.id.rv);

        //set layout
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this.getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);

        //get data
        new getAllSites().execute();

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
                    startActivity2(root,data.get(position).getId()-1);
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
