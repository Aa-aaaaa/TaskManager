package com.example.asus.taskmanager.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.taskmanager.R;
import com.example.asus.taskmanager.activities.MainActivity;

public class MyProfileFragment extends Fragment {

    public interface OnMyProfileDataListener {
        void goLoginActivityListener();
        void goFriendsListener(String s);
    }

    private OnMyProfileDataListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (OnMyProfileDataListener) activity;
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
                mListener.goLoginActivityListener();
            }
        });

        view.findViewById(R.id.bGoFollowing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goFriendsListener("following");
            }
        });

        view.findViewById(R.id.bGoFollowers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goFriendsListener("followers");
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
