package com.miti.meeti.ui.newsfeed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.miti.meeti.R;

import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {
    private List<String>temp=new ArrayList<String>();
    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View Itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item,parent,false);
        return new FeedHolder(Itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedHolder holder, int position) {
        String currentString=temp.get(position);
        holder.temp.setText(currentString);
    }

    @Override
    public int getItemCount() {
        return temp.size();
    }

    public void setTemp(List<String>temp){
        this.temp=temp;
        notifyDataSetChanged();
    }
    public void setTemp1(List<String>temp1){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffCallback(this.temp, temp1));
//        this.temp=temp1;
        diffResult.dispatchUpdatesTo(this);
        this.temp.clear();
        this.temp.addAll(temp1);
    }
    class FeedHolder extends RecyclerView.ViewHolder{
        private TextView temp;
        public FeedHolder(@NonNull View itemView) {
            super(itemView);
            temp=itemView.findViewById(R.id.feed_text);
        }
    }
}
