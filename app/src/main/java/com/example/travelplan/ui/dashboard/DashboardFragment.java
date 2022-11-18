package com.example.travelplan.ui.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;
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

        //move camera position
        AMap aMap = mapView.getMap();//get aMap
        LatLng latLng = new LatLng(31.27,120.74);//construct a location; location can be searched on https://lbs.amap.com/tools/picker
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15)); //zoom rate



//        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(list.get(0), 7));
//        aMap.setMapTextZIndex(2);
//        aMap.addPolyline((new PolylineOptions())
//                //手动数据测试
//                .add(new LatLng(26.57, 120.71),new LatLng(26.14,120.55),new LatLng(26.58, 120.82), new LatLng(30.67, 120.06))
//                //集合数据
////                .addAll(list)
//                //线的宽度
//                .width(10).setDottedLine(true).geodesic(true)
//                //颜色
//                .color(Color.argb(255,255,20,147)));

//        for (int i = 0; i < marketList.size(); i++) {
//
//            final LocationMarketBean locationMarketBean = marketList.get(i);
//
//            markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
//                    .fromView(getBitmapView(LocationMarkerInfoWindowsActivity.this, locationMarketBean)));
//
//            markerOption.position(new LatLng(locationMarketBean.getLatitude(), locationMarketBean.getLongitude()));
//
//            markerOption.setFlat(true);
//
//            markerOption.draggable(false);
//
//            marker = aMap.addMarker(markerOption);
//
//            markerOptionlst.add(markerOption);
//
//        }
//
//        markersOnListen = aMap.addMarkers(markerOptionlst, true);
//
//        aMap.setOnMarkerClickListener(marker -> {
//
//            for (int i = 0; i < markersOnListen.size(); i++) {
//
//                if (marker.equals(markersOnListen.get(i))) {
//
//                    Toast.makeText(LocationMarkerInfoWindowsActivity.this, marketList.get(i).getTitle(), Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
//
//        return false;
//
//        });



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