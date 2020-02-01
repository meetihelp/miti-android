package com.miti.meeti.ui.newsfeed;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.chip.Chip;
import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.AllUrl;
import com.miti.meeti.NetworkObjects.Feed;
import com.miti.meeti.NetworkObjects.FeedReaction;
import com.miti.meeti.R;
import com.miti.meeti.database.Feed.FeedDb;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {
    private List<FeedDb>temp=new ArrayList<>();
    private HashMap<Integer,String> like=new HashMap<>();
    private Chip chip;
    private Gson gson=new Gson();
    public FeedAdapter(){
        super();
        setHasStableIds(true);
    }
    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View Itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item,parent,false);
        return new FeedHolder(Itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedHolder holder, final int position) {
        final FeedDb currentFeed=temp.get(position);
        holder.temp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("ArticeURL",currentFeed.ArticeURL);
                bundle.putString("ImageURL",currentFeed.ImageURL);
                bundle.putString("Flag",currentFeed.Flag);
                bundle.putString("Title",currentFeed.Title);
                bundle.putInt("Id",currentFeed.Id);
                Navigation.findNavController(newfeed.v).navigate(R.id.action_miti_newsfeed_to_feedView,bundle);
            }
        });
        holder.temp2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("ArticeURL",currentFeed.ArticeURL);
                bundle.putString("ImageURL",currentFeed.ImageURL);
                bundle.putString("Flag",currentFeed.Flag);
                bundle.putString("Title",currentFeed.Title);
                bundle.putInt("Id",currentFeed.Id);
                Navigation.findNavController(newfeed.v).navigate(R.id.action_miti_newsfeed_to_feedView,bundle);
            }
        });
        holder.like.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                POSTRequest postRequest=new POSTRequest();
                FeedReaction.request_body response=new FeedReaction().new request_body(currentFeed.Id,"Like");
                String jsonInString=gson.toJson(response);
                postRequest.execute(AllUrl.url_newsfeed().get(1),jsonInString, MainActivity.cookieViewModel.getCookie1());
//                like.put(position,"like");
                ToastHelper.ToastFun(newfeed.v.getContext(),"Liked");
//                notifyItemChanged(position);
            }
        });
        holder.dislike.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                POSTRequest postRequest=new POSTRequest();
                FeedReaction.request_body response=new FeedReaction().new request_body(currentFeed.Id,"Dislike");
                String jsonInString=gson.toJson(response);
                postRequest.execute(AllUrl.url_newsfeed().get(1),jsonInString, MainActivity.cookieViewModel.getCookie1());
//                chip.setChipIconTintResource(R.color.mitiRed);
                ToastHelper.ToastFun(newfeed.v.getContext(),"DisLiked");
//                like.put(position,"dislike");
//                notifyItemChanged(position);
            }
        });
        holder.temp.setText(currentFeed.Summary.replaceAll("[^\\x00-\\x7F]", ""));
        holder.temp2.setText(currentFeed.Title.replaceAll("^[ \t]+|[ \t]+$", ""));
        //url=currentFeed.image_url
        String likestatus=like.get(position);
        if(likestatus!=null){
            if(likestatus.contains("like")){
                holder.setUnset(0);
                holder.setUnset(3);
            }else{
                holder.setUnset(1);
                holder.setUnset(2);
            }
        }
        if(newfeed.autism){
            Typeface font = ResourcesCompat.getFont(newfeed.v.getContext(),R.font.popcorn);
            Typeface font1 = ResourcesCompat.getFont(newfeed.v.getContext(),R.font.pacifico);
            TextView tv1=holder.temp2;
            TextView tv2=holder.temp;
            tv1.setTypeface(font1);
            tv2.setTypeface(font);
        }else{
            Typeface font = ResourcesCompat.getFont(newfeed.v.getContext(),R.font.slabo_13px);
            Typeface font1 = ResourcesCompat.getFont(newfeed.v.getContext(),R.font.montserrat);
            TextView tv1=holder.temp2;
            tv1.setTypeface(font);
            TextView tv2=holder.temp;
            tv2.setTypeface(font1);
        }
        Mlog.e(currentFeed.ImageURL);
        Glide.with(newfeed.v.getContext()).load(currentFeed.ImageURL).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.temp1);
        holder.temp3.setText(currentFeed.Label);
    }

    @Override
    public int getItemCount() {
        return temp.size();
    }

    @Override
    public long getItemId(int position) {
        FeedDb currentFeed=temp.get(position);
        return currentFeed.Id;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    public void setTemp(List<FeedDb>temp){
        this.temp=temp;
        notifyDataSetChanged();
    }
//    public void setTemp1(List<FeedDb>temp1){
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
        private Chip like;
        private Chip dislike;
        public void setUnset(int l){
            if(l==0){
                like.setChipIconTintResource(R.color.mitiRed);
            }
            else if(l==1){
                like.setChipIconTintResource(R.color.mitiOrange);
            }
            else if(l==2){
                dislike.setChipIconTintResource(R.color.mitiRed);
            }else if(l==3){
                dislike.setChipIconTintResource(R.color.mitiOrange);
            }
        }
        public FeedHolder(@NonNull View itemView) {
            super(itemView);
            like=itemView.findViewById(R.id.feed_like);
            dislike=itemView.findViewById(R.id.feed_dislike);
            temp=itemView.findViewById(R.id.feed_text);
            temp2=itemView.findViewById(R.id.feed_heading);
            temp3=itemView.findViewById(R.id.label_chip);
            temp1=(ImageView) itemView.findViewById(R.id.imageView2);
        }
    }
}
