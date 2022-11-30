package com.example.travelplan.ui.notifications;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.travelplan.AboutUs;
import com.example.travelplan.Favorite;
import com.example.travelplan.Helper;
import com.example.travelplan.PlanlistActivity;
import com.example.travelplan.R;
import com.example.travelplan.databinding.FragmentNotificationsBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.TemporalAdjuster;
import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class NotificationsFragment extends Fragment {

    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    private boolean mChecked = false;

    private FragmentNotificationsBinding binding;

    private Button buttonFavorite;
    private Button buttonPlanlist;
    private ImageView avatar;

    private ImageView image0;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;

    private TextView aboutus,helper;
    private TextView name;

    int[] image = new int[]{
            R.drawable.avatar0,
            R.drawable.avatar1,
            R.drawable.avatar2,
            R.drawable.avatar3,
            R.drawable.avatar4
    };

    private SharedPreferences sp;

    int currentInt = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        sp = getActivity().getSharedPreferences("Personal", MODE_PRIVATE);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        buttonFavorite = (Button) root.findViewById(R.id.collection);
        buttonFavorite.setOnClickListener(new OnClickListener() {
            @Override

            public void onClick(View v) {
                ToFavorite(v);
            }
        });
        buttonPlanlist = (Button) root.findViewById(R.id.planlist);
        buttonPlanlist.setOnClickListener(new OnClickListener() {

            @Override

            public void onClick(View v) {
                ToPlanlist(v);
            }
        });

        avatar = (ImageView) root.findViewById(R.id.Avatar);
        int i = sp.getInt("avatar", -1);
        if (i != -1) {
            avatar.setImageResource(image[i]);
        }
        avatar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showpop(view);
            }
        });

        name = (TextView) root.findViewById(R.id.name);
        String nickName = sp.getString("name", "");
        if (!TextUtils.isEmpty(nickName)) {
            name.setText(nickName);
        }
        name.setOnClickListener(view -> {
            showInput(name.getText().toString());
        });

        aboutus = (TextView) root.findViewById(R.id.aboutus);
        aboutus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ToAboutus(view);
            }
        });

        helper = (TextView) root.findViewById(R.id.helper);
        helper.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ToHelper(view);
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


    // dialog
    private void showInput(String nickName) {
        final EditText editText = new EditText(getContext());
        editText.setText(nickName);
        new AlertDialog.Builder(getContext()).setTitle("Input the username").setView(editText)
                .setPositiveButton("confrim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        name.setText(editText.getText().toString());
                        sp.edit().putString("name", editText.getText().toString()).apply();
                        Toast.makeText(getContext(), "Username change into：" + editText.getText().toString()
                                , Toast.LENGTH_LONG).show();
                    }
                }).create().show();
    }


    public void showpop(View v) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.avatarpop, null, false);
        ImageView image0 = (ImageView) view.findViewById(R.id.image0);
        ImageView image1 = (ImageView) view.findViewById(R.id.image1);
        ImageView image2 = (ImageView) view.findViewById(R.id.image2);
        ImageView image3 = (ImageView) view.findViewById(R.id.image3);
        ImageView image4 = (ImageView) view.findViewById(R.id.image4);
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

    public void showdialog(int n) {
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
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void changeAvatar(int n) {
        avatar.setImageResource(image[n]);
        sp.edit().putInt("avatar", n).apply();
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

    //跳转关于我们
    public void ToAboutus(View view) {

        this.startActivity(new Intent(getActivity(), AboutUs.class));
    }

    public void ToHelper(View view) {

        this.startActivity(new Intent(getActivity(), Helper.class));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
