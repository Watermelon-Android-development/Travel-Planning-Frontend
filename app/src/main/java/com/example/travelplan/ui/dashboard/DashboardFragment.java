package com.example.travelplan.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.travelplan.Favorite;
import com.example.travelplan.R;
import com.example.travelplan.databinding.FragmentDashboardBinding;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private MapView mapView = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        mapView = (MapView)root.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        mapView.onSaveInstanceState(outstate);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}