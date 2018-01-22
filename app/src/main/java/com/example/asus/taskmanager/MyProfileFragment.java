package com.example.asus.taskmanager;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.taskmanager.Activities.MainActivity;
import com.example.asus.taskmanager.Activities.MyProfileActivity;

public class MyProfileFragment extends Fragment {

    public interface OnMyProfileDataListener {
        void goLoginActivityListener();
    }

    private OnMyProfileDataListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (OnMyProfileDataListener) activity;
    }

    private void go_login() {
        mListener.goLoginActivityListener();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_profile_fragment, container, false);

        TextView tvFullName = view.findViewById(R.id.tvFullName);
        TextView tvEmail = view.findViewById(R.id.tvEmail);

        tvFullName.setText(MainActivity.getUser().getToken());
        //tvEmail.setText(MainActivity.getUser().getUsername());

        Button bLogout = view.findViewById(R.id.bLogout);
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_login();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
