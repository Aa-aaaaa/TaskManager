package com.example.asus.taskmanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.taskmanager.FeedPost;
import com.example.asus.taskmanager.R;
import com.example.asus.taskmanager.ServerTask;
import com.example.asus.taskmanager.TaskList;
import com.example.asus.taskmanager.User;
import com.example.asus.taskmanager.Utils;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder>
{
    private Context context;
    private List<FeedPost> feedList;

    public FeedAdapter (Context context, List<FeedPost> feedList)
    {
        this.context = context;
        this.feedList = feedList;
    }

    public void setFeedList(List<FeedPost> feedList)
    {
        this.feedList = feedList;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.feed_list_layout, null);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FeedViewHolder holder, int position) {
        FeedPost post = feedList.get(position);

        holder.tvOwnerName.setText(post.getUser().getFirstName() + " " + post.getUser().getLastName());
        holder.tvTaskName.setText(post.getServerTask().getName());
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
