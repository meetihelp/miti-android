package com.miti.meeti.ui.social.chat;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.miti.meeti.NetworkObjects.ChatList;
import com.miti.meeti.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class social_chat_list_adapter extends RecyclerView.Adapter<social_chat_list_adapter.ViewHolder> {

    List<ChatList.chatlist_object> chatlist = new ArrayList<>();
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView idTextView;
        public TextView titleTextView;
        public TextView descriptionTextView;
        public MaterialCardView card;
        public ViewHolder(View itemView) {
            super(itemView);

            idTextView = itemView.findViewById(R.id.social_no);
            titleTextView = itemView.findViewById(R.id.social_title);
            descriptionTextView = itemView.findViewById(R.id.social_description);
            card = (MaterialCardView) itemView.findViewById(R.id.social_card);
        }
    }
    public void setChatList(List<ChatList.chatlist_object> chatList){
        this.chatlist=chatList;
//        Log.e("Control-setchatlist->",Integer.toString(this.chatlist.size()));
        notifyDataSetChanged();
    }
    @Override
    public social_chat_list_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.temp, parent, false);

        social_chat_list_adapter.ViewHolder viewHolder = new social_chat_list_adapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        Log.e("Control-ONbindd for->",chatlist.get(position).TempUserId);
        holder.idTextView.setText(chatlist.get(position).TempUserId);
        holder.titleTextView.setText(chatlist.get(position).ChatType);
        holder.descriptionTextView.setText(chatlist.get(position).LastUpdate);
        //action_social_chat_list_to_social_chat_content
        final Bundle bundle=new Bundle();
        bundle.putString("chatid",chatlist.get(position).ChatId);
        bundle.putString("chattype",chatlist.get(position).ChatType);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //implement onClick
                Navigation.findNavController(v).navigate(R.id.action_social_chat_list2_to_social_chat_content2,bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.chatlist.size();
    }
}