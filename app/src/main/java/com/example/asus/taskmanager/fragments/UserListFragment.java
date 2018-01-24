package com.example.asus.taskmanager.fragments;

import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asus.taskmanager.R;
import com.example.asus.taskmanager.TaskList;
import com.example.asus.taskmanager.User;
import com.example.asus.taskmanager.Utils;
import com.example.asus.taskmanager.activities.MainActivity;
import com.example.asus.taskmanager.adapters.UserAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

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

        RecyclerView recycler = view.findViewById(R.id.rvUserList);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        final List<User> userList = new ArrayList<>();

        final UserAdapter adapter;
        adapter = new UserAdapter(getActivity(), userList);
        recycler.setAdapter(adapter);

        switch (userType) {
            case "followers":
            {
                User.getMyself(new TaskList.PerformObject() {
                    @Override
                    public void perform(Object object) {
                        MainActivity.getUser().setSubscribers(((User)object).getSubscribers());
                        List<Long> usersId = MainActivity.getUser().getSubscribers();
                        final int usersIdSize = usersId.size();
                        for (Long id : usersId) {
                            User.getUser(id, new TaskList.PerformObject() {
                                @Override
                                public void perform(Object object) {
                                    userList.add((User) object);
                                    if (userList.size() == usersIdSize) {
                                        adapter.setUserList(userList);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            }, new Utils.OnErrorCallback() {
                                @Override
                                public void perform() {

                                }
                            });
                        }
                    }
                }, new Utils.OnErrorCallback() {
                    @Override
                    public void perform() {
                        Log.e(TAG, "perform: Error Happened");
                    }
                });
                break;
            }
            case "following": {
                User.getMyself(new TaskList.PerformObject() {
                    @Override
                    public void perform(Object object) {
                        MainActivity.getUser().setSubscribedOn(((User)object).getSubscribedOn());
                        List<Long> usersId = MainActivity.getUser().getSubscribedOn();
                        final int usersIdSize = usersId.size();
                        Log.d("SUB", " " + usersIdSize);
                        for (Long id : usersId) {
                            User.getUser(id, new TaskList.PerformObject() {
                                @Override
                                public void perform(Object object) {
                                    userList.add((User) object);
                                    if (userList.size() == usersIdSize) {
                                        adapter.setUserList(userList);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            }, new Utils.OnErrorCallback() {
                                @Override
                                public void perform() {

                                }
                            });
                        }
                    }
                }, new Utils.OnErrorCallback() {
                    @Override
                    public void perform() {
                        Log.e(TAG, "perform: Error Happened");
                    }
                });
                break;
            }
            case "search": {
                User.getAllUsers(new TaskList.PerformObject() {
                                     @Override
                                     public void perform(Object object) {
                                         userList.addAll((List<User>) object);
                                         adapter.notifyDataSetChanged();
                                     }
                                 },
                        new Utils.OnErrorCallback() {
                            @Override
                            public void perform() {

                            }
                        }
                );
                break;
            }
        }

        return view;
    }
}
