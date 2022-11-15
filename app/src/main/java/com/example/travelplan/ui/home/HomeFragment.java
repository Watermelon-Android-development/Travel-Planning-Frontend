package com.example.travelplan.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelplan.HomePage;
import com.example.travelplan.R;
import com.example.travelplan.collection_item;
import com.example.travelplan.databinding.FragmentHomeBinding;
import com.example.travelplan.myAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private List<collection_item> data;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        data= new ArrayList<>();
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        for (int i=0;i<100;i++){
            collection_item item =new collection_item();
            item.setName("index"+i);
            data.add(item);
        }

        RecyclerView recyclerView= root.findViewById(R.id.rv);

        GridLayoutManager gridLayoutManager= new GridLayoutManager(this.getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        myAdapter myAdapter= new  myAdapter(data,this.getContext());
        recyclerView.setAdapter(myAdapter);

        myAdapter.setRecyclerItemClickListener(new myAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClick(int position) {
                Log.e("test", "onRecyclerItemClick: "+position);

            }
        });

        return root;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}