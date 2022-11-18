package com.example.travelplan.ui.notifications;

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
import com.example.travelplan.PlanlistActivity;
import com.example.travelplan.R;
import com.example.travelplan.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    private Button buttonFavorite;
    private Button buttonPlanlist;


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
        return root;


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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
