package com.example.asus.taskmanager.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
        View view = LayoutInflater.from(context).inflate(R.layout.feed_list_layout, null);



        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FeedViewHolder holder, final int position) {
        FeedPost post = feedList.get(position);

        holder.tvOwnerName.setText(post.getUser().getFirstName() + " " + post.getUser().getLastName());
        holder.tvTaskName.setText(post.getServerTask().getName());
        holder.bLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskList.getInstance(context).like(feedList.get(position).getServerTask().getId(), new TaskList.PerformObject() {
                    @Override
                    public void perform(Object object) {
                        holder.bLike.setImageResource(android.R.drawable.btn_star_big_on);
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
        return feedList.size();
    }

    class FeedViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvOwnerName;
        TextView tvTaskName;
        ImageButton bLike;
        public FeedViewHolder(View itemView) {
            super(itemView);
            tvOwnerName = itemView.findViewById(R.id.tvOwnerName);
            tvTaskName = itemView.findViewById(R.id.tvTaskName);
            bLike = itemView.findViewById(R.id.bLike);
        }
    }
}
