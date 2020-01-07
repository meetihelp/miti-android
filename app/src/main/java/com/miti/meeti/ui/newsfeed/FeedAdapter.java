package com.miti.meeti.ui.newsfeed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.miti.meeti.NetworkObjects.Feed;
import com.miti.meeti.R;

import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {
    private List<Feed.feed_object>temp=new ArrayList<>();
    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View Itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item,parent,false);
        return new FeedHolder(Itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedHolder holder, int position) {
        Feed.feed_object currentFeed=temp.get(position);
        holder.temp.setText(currentFeed.Summary);
        holder.temp2.setText(currentFeed.Title.replaceAll("^[ \t]+|[ \t]+$", ""));
        //url=currentFeed.image_url
        String url="https://www.thehindu.com/business/Economy/vmkj2f/article30442644.ece/ALTERNATES/FREE_960/Nirmala-Sitharaman";
        Glide.with(newfeed.v.getContext()).load(url).into(holder.temp1);
        holder.temp3.setText(currentFeed.Label);
    }

    @Override
    public int getItemCount() {
        return temp.size();
    }

    public void setTemp(List<Feed.feed_object>temp){
        this.temp=temp;
        notifyDataSetChanged();
    }
//    public void setTemp1(List<Feed.feed_object>temp1){
//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffCallback(this.chat_list_helper, temp1));
////        this.chat_list_helper=temp1;
//        diffResult.dispatchUpdatesTo(this);
//        this.chat_list_helper.clear();
//        this.chat_list_helper.addAll(temp1);
//    }
    class FeedHolder extends RecyclerView.ViewHolder{
        private TextView temp;
        private ImageView temp1;
        private TextView temp2;
        private Chip temp3;
        public FeedHolder(@NonNull View itemView) {
            super(itemView);
            temp=itemView.findViewById(R.id.feed_text);
            temp2=itemView.findViewById(R.id.feed_heading);
            temp3=itemView.findViewById(R.id.label_chip);
            temp1=(ImageView) itemView.findViewById(R.id.imageView2);
        }
    }
}
