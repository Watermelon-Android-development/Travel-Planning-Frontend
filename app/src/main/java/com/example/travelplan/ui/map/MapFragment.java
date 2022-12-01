package com.example.travelplan.ui.map;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.example.travelplan.R;
import com.example.travelplan.TravelDatabaseHelper;
import com.example.travelplan.databinding.FragmentMapBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


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
    private SharedPreferences sp;





    class getAllLocs extends AsyncTask<Void, Void, Boolean> {

        // 方法1：onPreExecute（）
        // 作用：执行 线程任务前的操作
        @Override
        protected void onPreExecute() {

            if (isFristRun()){
//                AlertDialog alertDialog2 = new AlertDialog.Builder(getContext())
//                        .setTitle("Click on the image to add to route")
////                        .setMessage("有多个按钮")
//                        .setIcon(R.drawable.click_on_image)
//
//                        .setPositiveButton("Got it", new DialogInterface.OnClickListener()
//                                {//添加按钮
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
////                                Toast.makeText(getContext(),"这是确定按钮", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        )
//
//                        .create();
//                alertDialog2.show();
                Bitmap  bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.click_on_image);
                showDialog(getContext(),bitmap);
            };

        }

        // 方法2：doInBackground（）
        // 作用：接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
        // 此处通过计算从而模拟“加载进度”的情况
        @Override
        protected Boolean doInBackground(Void... voids) {

            data = travelDatabaseHelper.getAllSites();
            Log.e("test22", "data: "+data.get(0).getxCoor() );

            String location_string =sp.getString("name","");
            List<String> location_list = new ArrayList<String>();
            location_list.addAll(Arrays.asList(location_string.split(",")));
            List<Integer> int_list =new ArrayList<Integer>();
//            location_list.add("??");
            if (location_list.contains("")){
                location_list.remove("");
            }
            for (int i = 0; i < location_list.size(); i++) {
                int index =Integer.parseInt(location_list.get(i));
                int_list.add(index);

            }


            for (int i = 0; i < 13;i++){
//                Log.e("test", "doInBackground: "+i );
                Double lat =data.get(i).getyCoor(); //latitude
                Double lng =data.get(i).getxCoor(); //longitude
                LatLng latLng3  = new LatLng(lat,lng);
                //定义Marker样式
                MarkerOptions options = new MarkerOptions();
                options.position(latLng3);//定位设置

//                int imageID =data.get(i).getImgID();

                String imageID = String.valueOf(data.get(i).getImgID());

//
                if (int_list.contains(i)){
                    options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_red));
                    options.draggable(false);
                }
                else{options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_blue));
                    options.draggable(true);}



                options.title(imageID+","+i);//标题内容设置  存储图片ID
                options.snippet(data.get(i).getName());
//                    ArrayList<BitmapDescriptor> arr1 =  new ArrayList<>();
//                    arr1.add(0,BitmapDescriptorFactory.fromResource(R.mipmap.icon_blue));
//
////                arr1.add(1,BitmapDescriptorFactory.fromResource(R.mipmap.icon_red));
//                    options.icons(arr1);
//               options.title(lst_title.get(i)).snippet(lst2.get(i));//标题内容设置
                aMap.addMarker(options);
            }
//            SharedPreferences.Editor editor = sp.edit();


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


        sp = getActivity().getSharedPreferences("Personal", MODE_PRIVATE);


            //提交


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


        new getAllLocs().execute();

//


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
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12)); //zoom rate
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

    /*
     * 弹出图片
     */
    private void showDialog(Context context, Bitmap bitmap){
        Dialog dia = new Dialog(context);
        dia.setContentView(R.layout.dialog);
        ImageView imageView = (ImageView) dia.findViewById(R.id.ivdialog);
        //可以set任何格式图片
        imageView.setImageBitmap(bitmap);
        dia.show();
        //选择true的话点击其他地方可以使dialog消失，为false的话不会消失
        dia.setCanceledOnTouchOutside(true); // Sets whether this dialog is
        Window w = dia.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        lp.y = 40;
        dia.onWindowAttributesChanged(lp);
    }

    private boolean isFristRun() {
        //实例化SharedPreferences对象（第一步）
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(
                "share", MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        Editor editor = sharedPreferences.edit();
        if (!isFirstRun) {
            return false;
        } else {
            //保存数据 （第三步）


            editor.putBoolean("isFirstRun", false);
            //提交当前数据 （第四步）
            editor.commit();
            return true;
        }
    }



    @Override
    public void onInfoWindowClick(Marker marker) {

//        Log.e("Test", "onInfoWindowClick: 标题为：" + marker.getTitle() + "  的InfoWindow被点击了");
//        marker.setIcon(R.drawable.icon_loc);
        if(marker.isDraggable())
        {
            Log.e("sp_before", "sp: "+sp.getString("name", "") );
            Log.e("Test", "蓝色变红色; "+marker.getId());
            String id=marker.getTitle().split(",")[1];  //get infoWin ID
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_red));
            marker.setDraggable(false);
            Toast toast=Toast.makeText(this.getContext(), "添加到route", Toast.LENGTH_LONG);
            showMyToast(toast, 2*1000);


            SharedPreferences.Editor editor = sp.edit();
            String location_string =sp.getString("name","");
            List<String> location_list = new ArrayList<String>();
            location_list.addAll(Arrays.asList(location_string.split(",")));
            if (location_list.contains("")){
                location_list.remove("");
            }

            int len = location_list.size();
            boolean temp =true;
            for (int i = 0; i < len; i++) {
                if (location_list.get(i).equals(id)){
                    temp=false;
                }
            }
            if(temp){
                editor.putString("name",sp.getString("name","")+id+",");
                editor.apply();

            }


          Log.e("final_sp", "sp: "+sp.getString("name", "") );


        }
        else{
            Log.e("sp_before", "sp: "+sp.getString("name", "") );
            Log.e("Test", "红色变蓝色");
            String id=marker.getTitle().split(",")[1];
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_blue));
            marker.setDraggable(true);
            Toast toast=Toast.makeText(this.getContext(), "从route删除", Toast.LENGTH_LONG);
            showMyToast(toast, 2*1000);

            SharedPreferences.Editor editor = sp.edit();
            String location_string =sp.getString("name","");
            List<String> location_list = new ArrayList<String>(); //content in sp
            location_list.addAll(Arrays.asList(location_string.split(",")));
            if (location_list.contains("")){
                location_list.remove("");
            }
            int len =location_list.size();
            int index =-23;
            for (int i = 0; i < len; i++) {
                if (location_list.get(i).equals(id)){
                    index=i;
                }
            }
            if (index > -23) {
                location_list.remove(index);
            }
            int len_af= location_list.size();
            String final_list = "";
            for (int i = 0; i < len_af; i++) {
                final_list += location_list.get(i)+",";
            }
            editor.putString("name",final_list);
            editor.apply();
            Log.e("final_sp", "sp: "+sp.getString("name", "") );







        }
//        Log.e("Test", "onInfoWindowClick: 标题为：" + marker.getSnippet()+ "  的InfoWindow被点击了");
//        marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_red));

    }

    public void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }

    /*
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
        ImageView imageView=infoWindow.findViewById(R.id.iv_1);

       int imageID=Integer.parseInt(marker.getTitle().split(",")[0]);

        imageView.setImageResource(imageID);
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
    public void onPause(

    ) {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }


}
