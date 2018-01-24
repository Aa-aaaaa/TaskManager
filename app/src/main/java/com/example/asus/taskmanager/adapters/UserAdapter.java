package com.example.asus.taskmanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.taskmanager.R;
import com.example.asus.taskmanager.TaskList;
import com.example.asus.taskmanager.User;
import com.example.asus.taskmanager.Utils;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>
{
    private Context context;
    private List<User> userList;

    public UserAdapter(Context context, List<User> userList)
    {
        this.context = context;
        this.userList = userList;
    }

    public void setUserList(List<User> userList)
    {
        this.userList = userList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_list_layout, null);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, int position) {
        final User user = userList.get(position);
        holder.tv.setText(user.getFirstName() + " " + user.getLastName());
        holder.bSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.subscribe(user.getId(), new TaskList.PerformObject() {
                    @Override
                    public void perform(Object object) {
                        holder.bSubscribe.setText("Subscribed");
                    }
                }, new Utils.OnErrorCallback() {
                    @Override
                    public void perform() {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv;
        Button bSubscribe;
        public UserViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvName);
            bSubscribe = itemView.findViewById(R.id.bSubscribe);
        }
    }
}
