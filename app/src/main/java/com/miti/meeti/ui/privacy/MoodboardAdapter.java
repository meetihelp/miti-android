package com.miti.meeti.ui.privacy;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.miti.meeti.R;
import com.miti.meeti.database.Diary.Moodboard;
import com.miti.meeti.mitiutil.Logging.Mlog;

import java.util.ArrayList;
import java.util.List;

public class MoodboardAdapter extends RecyclerView.Adapter<MoodboardAdapter.MoodboardHolder> {
    private List<Moodboard> temp=new ArrayList<>();
    @NonNull
    @Override
    public MoodboardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View Itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.moodboard_item,parent,false);
        return new MoodboardAdapter.MoodboardHolder(Itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MoodboardHolder holder, int position) {
        StaggeredGridLayoutManager.LayoutParams lp =
                (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
//        holder.itemView.setBackgroundResource(R.color.mitiOrange); //without theme));
        Moodboard currentFeed=temp.get(position);
        Mlog.e("Aaya to main in bind view holder",Integer.toString(position),currentFeed.Content,currentFeed.Mimetype);
        if(currentFeed.Mimetype.equals("text")){
            Mlog.e("currentFeed.Content");
            holder.temp.setText(currentFeed.Content);
        }else if(currentFeed.Mimetype.equals("image")){
            Glide.with(PrivacyFragment.v.getContext()).load(currentFeed.ImagePath).into(holder.temp1);
        }
    }

    @Override
    public int getItemCount() {
        return temp.size();
    }

    public void setTemp(List<Moodboard>temp){
        this.temp=temp;
        notifyDataSetChanged();
    }
    //    public void setTemp1(List<Moodboard>temp1){
//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffCallback(this.chat_list_helper, temp1));
////        this.chat_list_helper=temp1;
//        diffResult.dispatchUpdatesTo(this);
//        this.chat_list_helper.clear();
//        this.chat_list_helper.addAll(temp1);
//    }
    class MoodboardHolder extends RecyclerView.ViewHolder{
        private TextView temp;
        private ImageView temp1;
        public MoodboardHolder(@NonNull View itemView) {
            super(itemView);
            temp=(TextView) itemView.findViewById(R.id.moodboard_content);
            temp1=(ImageView) itemView.findViewById(R.id.moodboard_image);
        }
    }
}

