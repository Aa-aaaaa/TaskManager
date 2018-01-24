package com.example.asus.taskmanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.taskmanager.FoneService;
import com.example.asus.taskmanager.R;
import com.example.asus.taskmanager.ServerTask;
import com.example.asus.taskmanager.User;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder>
{
    private Context context;
    private List<ServerTask> feedList;

    public FeedAdapter (Context context, List<ServerTask> serverTaskList)
    {
        this.context = context;
        this.feedList = serverTaskList;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.feed_list_layout, null);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        ServerTask serverTask = feedList.get(position);

        holder.tvTaskName.setText(serverTask.getName());
        holder.tvOwnerName.setText(serverTask.getDescription());
/*        holder.tvTaskName.setText(serverTask.getName());
        User user = new User();
        FoneService.getUser(serverTask.getOwner(), context, user);
        //TODO: do it with callback
        holder.tvOwnerName.setText(user.getFirstName() + " " + user.getLastName());*/
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    class FeedViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvOwnerName;
        TextView tvTaskName;
        public FeedViewHolder(View itemView) {
            super(itemView);
            tvOwnerName = itemView.findViewById(R.id.tvOwnerName);
            tvTaskName = itemView.findViewById(R.id.tvTaskName);
        }
    }
}
