package com.example.travelplan.ui.notifications;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.travelplan.Favorite;
import com.example.travelplan.PlanlistActivity;
import com.example.travelplan.R;
import com.example.travelplan.databinding.FragmentNotificationsBinding;
import com.example.travelplan.ui.favorite.SaveCheckBox;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.TemporalAdjuster;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    private Button buttonFavorite;
    private Button buttonPlanlist;
    private ImageView avatar;

    private ImageView image0;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;

    int[] image = new int[]{
            R.drawable.avatar0,
            R.drawable.avatar1,
            R.drawable.avatar2,
            R.drawable.avatar3,
            R.drawable.avatar4
    };

    int currentInt = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        buttonFavorite= (Button)root.findViewById(R.id.collection);
        buttonFavorite.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v){
                ToFavorite(v);
            }
        });
        buttonPlanlist= (Button)root.findViewById(R.id.planlist);
        buttonPlanlist.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View v){
                ToPlanlist(v);
            }
        });

        avatar = (ImageView) root.findViewById(R.id.Avatar);
        avatar.setImageResource(image[0]);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showpop(view);
                //showdialog(0);
            }
        });


//        image0.setImageResource(image[0]);
//        image1.setImageResource(image[1]);
//        image2.setImageResource(image[2]);
//
//        for(int i=0; i<changedimage.length; i++){
//            changedimage[i].setImageResource(image[i]);
//        }
        return root;


    }
    public void showpop(View v){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.avatarpop, null, false);
        ImageView image0 = (ImageView)view.findViewById(R.id.image0);
        ImageView image1 = (ImageView)view.findViewById(R.id.image1);
        ImageView image2 = (ImageView)view.findViewById(R.id.image2);
        ImageView image3 = (ImageView)view.findViewById(R.id.image3);
        ImageView image4 = (ImageView)view.findViewById(R.id.image4);
        final PopupWindow popupWindow = new PopupWindow(view,
                1100, 900, true);
        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        //popupWindow.setBackgroundDrawable(new ColorDrawable(0xF));
        popupWindow.showAsDropDown(v, 0, 0);
        //参照View，x轴的偏移量，y轴的偏移量

        //点击事件（换头像

        image0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog(0);
            }
        });
        image1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog(1);
            }
        });
        image2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog(2);
            }
        });
        image3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog(3);
            }
        });
        image4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog(4);
            }
        });
    }

    public void showdialog(int n){
        androidx.appcompat.app.AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Change");
        builder.setMessage("Change this?");
        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeAvatar(n);
            }

        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }


    public void changeAvatar(int n){
        avatar.setImageResource(image[n]);
        Toast.makeText(getContext(), "Changed!", Toast.LENGTH_SHORT).show();

    }

    //这个是跳转到collection的

    public void ToFavorite(View view) {

        this.startActivity(new Intent(getActivity(), Favorite.class));
    }

    //这是跳转到planlist的
    public void ToPlanlist(View view) {

        this.startActivity(new Intent(getActivity(), PlanlistActivity.class));
    }


    @Override
        public void onDestroyView () {
            super.onDestroyView();
            binding = null;
        }




}
