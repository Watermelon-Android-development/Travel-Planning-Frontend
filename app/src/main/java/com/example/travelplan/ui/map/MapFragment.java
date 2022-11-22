package com.example.travelplan.ui.map;

import static com.amap.api.maps2d.AMap.MAP_TYPE_NORMAL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.example.travelplan.R;
import com.example.travelplan.databinding.FragmentMapBinding;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {

    private FragmentMapBinding binding;
    private MapView mapView = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MapViewModel mapViewModel =
                new ViewModelProvider(this).get(MapViewModel.class);

        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        mapView = (MapView)root.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        //move camera position
        AMap aMap = mapView.getMap();//get aMap
        LatLng latLng = new LatLng(31.288477,120.617335);//construct a location; location can be searched on https://lbs.amap.com/tools/picker
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11)); //zoom rate
        aMap.setMapLanguage(AMap.ENGLISH);





//        //随便标了两个点
//        LatLng latLng1 = new LatLng(31.27,120.74);
//        final Marker marker1 = aMap.addMarker(new MarkerOptions().position(latLng1).title("loc1").snippet("this is A"));
//
//        MarkerOptions markerOption = new MarkerOptions();
//        markerOption.position(latLng);
//        markerOption.title("标题").snippet("今天考完试了很开心");
//        markerOption.draggable(false);//设置Marker可拖动
////      markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon1));
//        aMap.addMarker(markerOption);//非常重要

        List<List<Double>> list1=new ArrayList<List<Double>>();

        List<Double> list_1=new ArrayList<Double>();
        double loc1_w=31.270827;
        double loc1_j=120.720819;
        list_1.add(loc1_w);
        list_1.add(loc1_j);
        list1.add(list_1);
        List<Double> list_2=new ArrayList<Double>();
        double loc2_w=31.301202;
        double loc2_j=120.701151;
        list_2.add(loc2_w);
        list_2.add(loc2_j);
        list1.add(list_2);
        List<Double> list_3=new ArrayList<Double>();
        double loc3_w=31.238321;
        double loc3_j=120.582076;
        list_3.add(loc3_w);
        list_3.add(loc3_j);
        list1.add(list_3);
        List<Double> list_4=new ArrayList<Double>();
        double loc4_w=31.323242;
        double loc4_j=120.477035;
        list_4.add(loc4_w);
        list_4.add(loc4_j);
        list1.add(list_4);
        List<Double> list_5=new ArrayList<Double>();
        double loc5_w=31.287304;
        double loc5_j=120.504155;
        list_5.add(loc5_w);
        list_5.add(loc5_j);
        list1.add(list_5);


        List<String> lst_title= new ArrayList<>();
        lst_title.add("park");
        lst_title.add("lake");
        lst_title.add("zoo");
        lst_title.add("Playground");
        lst_title.add("mountain");

        List<String> lst2= new ArrayList<>();
        lst2.add("白鹭园");
        lst2.add("金鸡湖景区");
        lst2.add("上方山森林动物世界");
        lst2.add("苏州乐园森林世界");
        lst2.add("天平山风景名胜区");

        for (int i = 0; i < 5;i++){
            Double lat =list1.get(i).get(0); //latitude
            Double lng =list1.get(i).get(1); //longitude
            LatLng latLng3  = new LatLng(lat,lng);
            //定义Marker样式
            MarkerOptions options = new MarkerOptions();


//            BitmapFactory.Options bpoptions = new BitmapFactory.Options();
//            bpoptions.outHeight = 5;
//            bpoptions.outWidth = 5;
//            Bitmap bit = BitmapFactory.decodeResource(getResources(), R
//                    .mipmap.jingse, bpoptions);
//
//
//            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
//                    .fromBitmap(bit);
//
//            options.icon(bitmapDescriptor);
//            options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_round));//自定义样式
            options.position(latLng3);//定位设置
            options.title(lst_title.get(i)).snippet(lst2.get(i));//标题内容设置
            aMap.addMarker(options);
        }
//        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),15)); //更新地图

        //添加一个marker的点击事件
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                //当点击的时候添加生长动画
//                marker.setAnimation(animation);
//                marker.startAnimation();
                if (marker.isInfoWindowShown()){
                    marker.hideInfoWindow();
                }
                else {
                    marker.showInfoWindow();}

//                Log.e("test", "onMarkerClick: test");
//                //这里在添加点击监听事件后，原来的InfoWindow被取消了，可以在回调方法中手动实现
//                if (marker.isInfoWindowShown()) {
//                    marker.hideInfoWindow();
//                } else {
//                    marker.showInfoWindow();
//                }

                return true;
            }
        });


        //添加一个InfoWindow的点击事件
        aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                Log.e("Test", "onInfoWindowClick: 标题为：" + marker.getTitle() + "  的InfoWindow被点击了");
            }
        });

//        aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
//            @Override
//            public View getInfoWindow(Marker marker) {
//                View infowindow = LayoutInflater.from(getContext())
//                        .inflate(R.layout.inforwindow_layout, null);//InfoWindow的的背景布局文件.
//
//
//
//
//                return infowindow;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//                return null;
//            }
//        });






//        LatLng latLng2 = new LatLng(31.26,120.75);
//        final Marker marker2 = aMap.addMarker(new MarkerOptions().position(latLng2).title("loc2").snippet("this is B"));



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
//
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




    public interface InfoWindowAdapter {
        View getInfoWindow(Marker marker);
        View getInfoContents(Marker marker);
    }

    /**
     * set map language
     *
     * @param language AMap.CHINESE, AMap.ENGLISH
     * @since 5.5.0
     */
    public void setMapLanguage(String language){}

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

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }


}
