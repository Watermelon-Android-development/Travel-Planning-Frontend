package com.example.travelplan.ui.map;

import static com.amap.api.maps2d.AMap.MAP_TYPE_NORMAL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.example.travelplan.R;
import com.example.travelplan.TravelDatabaseHelper;
import com.example.travelplan.databinding.FragmentMapBinding;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.example.travelplan.myAdapter;
import com.example.travelplan.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements AMap.OnMarkerClickListener,AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter, AMap.OnMapClickListener{

    private FragmentMapBinding binding;
    private TravelDatabaseHelper travelDatabaseHelper;
    private MapView mapView = null;
    ProgressBar progressBar; // 进度条
    private List<TravelDatabaseHelper.Site> data;
    View infoWindow = null;
    private UiSettings uiSettings;
    private AMap aMap;
    private Marker clickMaker;
    View root;




    class getAllLocs extends AsyncTask<Void, Void, Boolean> {

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
//            myAdapter= new myAdapter(data,getContext());
            Log.e("test","test_map"+ data.get(0).getX_coor());
            Log.e("test","test_map"+ data.get(0).getY_coor());


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
                options.position(latLng3);//定位设置
                options.title(lst_title.get(i)).snippet(lst2.get(i));//标题内容设置
                aMap.addMarker(options);
            }
//

            return true;
        }



        //         方法4：onPostExecute（）
//         作用：接收线程任务执行结果、将执行结果显示到UI组件
        @Override
        protected void onPostExecute(Boolean success) {
            // 执行完毕后，则更新UI
//            text.setText("加载完毕");
//            data = travelDatabaseHelper.getAllSites();

//            recyclerView.setAdapter(myAdapter);
//            recyclerView.postInvalidate();
//            root.findViewById(R.id.bt_1).setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v) {
////                Toast.makeText(getApplicationContext(),"Button 3 clicked",Toast.LENGTH_LONG).show();
//                    Log.e("test", "onClick: 11111");
//                }
//            });

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


        MapViewModel mapViewModel =
                new ViewModelProvider(this).get(MapViewModel.class);

        binding = FragmentMapBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        mapView = (MapView)root.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);



//        aMap = mapView.getMap();//get aMap

        if (aMap == null) {
            aMap = mapView.getMap();
            uiSettings = aMap.getUiSettings();
            //设置地图属性
            setMapAttribute();
        }

//        LatLng latLng = new LatLng(31.288477,120.617335);//construct a location; location can be searched on https://lbs.amap.com/tools/picker
//        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11)); //zoom rate
//        aMap.setMapLanguage(AMap.ENGLISH);




        new getAllLocs().execute();

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


//        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),15)); //更新地图

        //添加一个marker的点击事件
//        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//
//                //当点击的时候打开infoWindow
//                if (marker.isInfoWindowShown()){
//                    marker.hideInfoWindow();
//                }
//                else {
//                    marker.showInfoWindow();}
//
////                Log.e("test", "onMarkerClick: test");
////                //这里在添加点击监听事件后，原来的InfoWindow被取消了，可以在回调方法中手动实现
////
//
//                return true;
//            }
//        });


        //添加一个InfoWindow的点击事件
//        aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//
//                Log.e("Test", "onInfoWindowClick: 标题为：" + marker.getTitle() + "  的InfoWindow被点击了");
//
//            }
//        });
//        root.findViewById(R.id.bt_1).setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(getApplicationContext(),"Button 3 clicked",Toast.LENGTH_LONG).show();
//                Log.e("test", "onClick: 11111");
//            }
//        });


        return root;
    }

    private void setMapAttribute() {

        LatLng latLng = new LatLng(31.288477,120.617335);//construct a location; location can be searched on https://lbs.amap.com/tools/picker
//        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11)); //zoom rate
        aMap.setMapLanguage(AMap.ENGLISH);
        //设置默认缩放级别
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11)); //zoom rate
        //隐藏的右下角缩放按钮
        uiSettings.setZoomControlsEnabled(true);
        //设置marker点击事件监听
        aMap.setOnMarkerClickListener(this);
        //设置InfoWindow点击事件监听
        aMap.setOnInfoWindowClickListener(this);
        //设置自定义信息窗口
        aMap.setInfoWindowAdapter(this);
        //设置地图点击事件监听
        aMap.setOnMapClickListener(this);
    }




    @Override
    public boolean onMarkerClick(Marker marker) {
        clickMaker = marker;
        //点击当前marker展示自定义窗体

        //返回true 表示接口已响应事,无需继续传递
//        当点击的时候打开infoWindow
        if (marker.isInfoWindowShown()){
            marker.hideInfoWindow();
        }
        else {
            marker.showInfoWindow();}

//                Log.e("test", "onMarkerClick: test");
//                //这里在添加点击监听事件后，原来的InfoWindow被取消了，可以在回调方法中手动实现
//

        return true;


    }


    @Override
    public void onInfoWindowClick(Marker marker) {

        Log.e("Test", "onInfoWindowClick: 标题为：" + marker.getTitle() + "  的InfoWindow被点击了");


    }
    /**
     * 监听自定义窗口infoWindow事件回调
     */
    @Override
    public View getInfoWindow(Marker marker) {
//        if (infoWindow == null) {
//            infoWindow = LayoutInflater.from(this.getContext()).inflate(R.layout.inforwindow_layout, null);
//        }
        infoWindow = LayoutInflater.from(this.getContext()).inflate(R.layout.inforwindow_layout, null);
        render(marker, infoWindow);
        return infoWindow;
    }

    /**
     * 自定义infoWindow窗口
     */
    private void render(Marker marker, View infoWindow) {
//        TextView title = infoWindow.findViewById(R.id.info_window_title);
        TextView content = infoWindow.findViewById(R.id.info_window_content);
//        title.setText(marker.getTitle());
        content.setText(marker.getSnippet());
    }

    /**
     * 不能修改整个InfoWindow的背景和边框，返回null
     */
    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    /**
     * 地图点击事件
     * 点击地图区域让当前展示的窗体隐藏
     */
    @Override
    public void onMapClick(LatLng latLng) {
        //判断当前marker信息窗口是否显示
        if (clickMaker != null && clickMaker.isInfoWindowShown()) {
            clickMaker.hideInfoWindow();
        }

    }




//    public interface InfoWindowAdapter {
//        View getInfoWindow(Marker marker);
//        View getInfoContents(Marker marker);
//    }

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
