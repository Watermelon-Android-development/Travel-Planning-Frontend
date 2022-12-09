package com.example.travelplan;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.travelplan.ui.map.MapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.travelplan.databinding.ActivityHomePageBinding;

public class HomePage extends AppCompatActivity {

    private ActivityHomePageBinding binding;
    private double longitude = 0, latitude = 0;
    private AMapLocationClient mLocationClient = null;
    private boolean ifAskedPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_Map, R.id.navigation_Profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home_page);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!ifAskedPermission) {
            getLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            ifAskedPermission = true;
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationClient.startLocation();
            } else {
//                Toast.makeText(this, "Location permission has been denied by you. \n" +
//                        "The Center Building of XJTLU will be used as your starting point.", Toast.LENGTH_LONG).show();
                AlertDialog alertDialog = new AlertDialog.Builder(HomePage.this)
                        //标题
                        .setTitle("Reminder")
                        //内容
                        .setMessage("Location permission has been denied by you.\n" +
                        "The Center Building of XJTLU will be used as your starting point.")
                        //图标
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("confirm", null)
                        .create();
                alertDialog.show();
                longitude = MapFragment.CB_LONGITUDE;
                latitude = MapFragment.CB_LATITUDE;
                MapFragment.setLocation(longitude, latitude);
            }
        }
    }

    private void getLocation() {
        Context c = getApplicationContext();
        AMapLocationClient.updatePrivacyShow(c, true, true);
        AMapLocationClient.updatePrivacyAgree(c, true);
        //初始化定位
        try {
            mLocationClient = new AMapLocationClient(c);
        } catch (Exception e) {
            e.printStackTrace();
        }

        AMapLocationListener mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        ifAskedPermission = true;
                        latitude = amapLocation.getLatitude();//获取纬度
                        longitude = amapLocation.getLongitude();//获取经度
                        if (mLocationClient != null) mLocationClient.stopLocation();
                    } else {
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                        if (amapLocation.getErrorCode() == 12 && !ifAskedPermission && Build.VERSION.SDK_INT >= 23) {
                            //如果用户并没有同意该权限
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                        } else {
                            longitude = MapFragment.CB_LONGITUDE;
                            latitude = MapFragment.CB_LATITUDE;
                        }
                    }
                }
                MapFragment.setLocation(longitude, latitude);
            }
        };
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setMockEnable(true);
        mLocationOption.setHttpTimeOut(10000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

}
