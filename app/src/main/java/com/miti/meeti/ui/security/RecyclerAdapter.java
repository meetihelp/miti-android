package com.miti.meeti.ui.security;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.miti.meeti.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>  {

    private Context context;
    private List<Contacts> contactsList;
    private FragmentCommunication itemSelectedListener;

    public RecyclerAdapter(Context context,List<Contacts> contacts){
        this.context=context;
        this.contactsList=contacts;
//        this.itemSelectedListener=()context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.profileName.setText(contactsList.get(position).getProfileName());
        holder.profilePic.setImageDrawable(ContextCompat.getDrawable(context,contactsList.get(position).getPicId()));
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView profilePic;
        TextView profileName;
        LinearLayout rootView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profileName=itemView.findViewById(R.id.profileName);
            profilePic=itemView.findViewById(R.id.profilePic);
            rootView=itemView.findViewById(R.id.rootView);
            rootView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            SecurityFragment.TrusteChainHandler(contactsList.get(getAdapterPosition()));
        }
    }

//    public void updateList(List<Contacts> newContacts){
//        contactsList.clear();
//        contactsList.addAll(newContacts);
//        notifyDataSetChanged();
//    }
}
