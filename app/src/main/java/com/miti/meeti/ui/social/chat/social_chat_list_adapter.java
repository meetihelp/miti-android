package com.miti.meeti.ui.social.chat;


import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.miti.meeti.MainActivity;
import com.miti.meeti.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class social_chat_list_adapter extends RecyclerView.Adapter<social_chat_list_adapter.ViewHolder> {

    List<DefaultDialog> chatlist = new ArrayList<>();
    public class ViewHolder extends RecyclerView.ViewHolder {

//        public TextView idTextView;
        public TextView titleTextView;
        public ImageView imageView;
        public TextView descriptionTextView;
        public LinearLayout linearLayout;
        public TextView chatType;
        public MaterialCardView card;
        public ViewHolder(View itemView) {
            super(itemView);

//            idTextView = itemView.findViewById(R.id.social_no);
            titleTextView = itemView.findViewById(R.id.social_title);
            descriptionTextView = itemView.findViewById(R.id.chatlist_lastmessage);
            card = (MaterialCardView) itemView.findViewById(R.id.social_card);
            linearLayout = itemView.findViewById(R.id.chatlist_linearlayout);
            chatType=itemView.findViewById(R.id.chatlist_type);
            imageView=itemView.findViewById(R.id.chatlist_image);
        }
    }
    public void setChatList(List<DefaultDialog> chatList){
        this.chatlist=chatList;
//        Log.e("Control-setchatlist->",Integer.toString(this.chatlist.size()));
        notifyDataSetChanged();
    }
    @Override
    public social_chat_list_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_helper, parent, false);

        social_chat_list_adapter.ViewHolder viewHolder = new social_chat_list_adapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        Log.e("Control-ONbindd for->",chatlist.get(position).TempUserId);
//        holder.idTextView.setText(chatlist.get(position).getId());
        holder.titleTextView.setText(chatlist.get(position).getDialogName());
//        holder.descriptionTextView.setText(chatlist.get(position).LastUpdate);
        //action_social_chat_list_to_social_chat_content
        final Bundle bundle=new Bundle();
        bundle.putString("chatid",chatlist.get(position).getId());
//        holder.chatType.setText(chatlist.get(position).Type.substring(0,1));
        holder.imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("userId", chatlist.get(position).userid);
               BottomDialogFragment bottomSheetDialog = BottomDialogFragment.getInstance();
               bottomSheetDialog.setArguments(args);
                bottomSheetDialog.show(social_chat_list.myContext.getSupportFragmentManager(),"hithere");
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //implement onClick
                Navigation.findNavController(v).navigate(R.id.action_social_chat_list_to_social_chat_content,bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.chatlist.size();
    }
}