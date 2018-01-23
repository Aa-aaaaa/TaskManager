package com.example.asus.taskmanager.fragments;

import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asus.taskmanager.R;
import com.example.asus.taskmanager.User;
import com.example.asus.taskmanager.adapters.UserAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment
{
    static private final String ARG_USER_TYPE = "user-type";
    private String userType;

    public static UserListFragment newInstance(String userType) {
        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_TYPE, userType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            userType = getArguments().getString(ARG_USER_TYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_list_fragment, container, false);

        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            userList.add(new User("Sergo", userType, "Sergo", "lol"));
            userList.add(new User("Marina", "Marina", "Marina", "kek"));
            userList.add(new User("Max", "Max", "Max", "azaza"));
        }


        RecyclerView recycler = view.findViewById(R.id.rvUserList);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        //TODO: two variants: following and followers - depends on userType, need server CHANGE userList
        UserAdapter adapter;
        adapter = new UserAdapter(getActivity(), userList);

        recycler.setAdapter(adapter);
        return view;
    }
}
