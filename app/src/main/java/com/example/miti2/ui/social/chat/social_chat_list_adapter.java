package com.example.miti2.ui.social.chat;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miti2.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class social_chat_list_adapter extends RecyclerView.Adapter<social_chat_list_adapter.ViewHolder> {

    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> description = new ArrayList<>();
    Context context;

    public social_chat_list_adapter(Context applicationContext, ArrayList<String> id, ArrayList<String> title, ArrayList<String> description) {

        this.context = applicationContext;
        this.id = id;
        this.title = title;
        this.description = description;

    }

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

    @Override
    public social_chat_list_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.temp, parent, false);

        social_chat_list_adapter.ViewHolder viewHolder = new social_chat_list_adapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.idTextView.setText(id.get(position));
        holder.titleTextView.setText(title.get(position));
        holder.descriptionTextView.setText(description.get(position));
        //action_social_chat_list_to_social_chat_content
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //implement onClick
                Navigation.findNavController(v).navigate(R.id.action_social_chat_list_to_social_chat_content);
            }
        });

    }

    @Override
    public int getItemCount() {
        return id.size();
    }
}