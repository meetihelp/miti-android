package com.miti.meeti.ui.social.messageRequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.miti.meeti.R;
import com.miti.meeti.database.Request.MessageRq;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;

import java.util.ArrayList;
import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {
    private List<MessageRq>dataset=new ArrayList<>();
    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_request_helper, parent, false);
        RecAdapter.ViewHolder viewHolder = new RecAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecAdapter.ViewHolder holder, final int position) {
        MessageRq tempxu=dataset.get(position);
//        Mlog.e("inMeesage list adapter",tempxu.Name);
        holder.titleTextView.setText(tempxu.Name);
        if(tempxu.Phone!=null){
            holder.descriptionTextView.setText(tempxu.Phone);
        }
        holder.card.setClickable(true);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                Mlog.e("RecAdapter","phone",dataset.get(position).Phone);
                bundle.putString("phone",dataset.get(position).Phone);
                bundle.putString("name",dataset.get(position).Name);
                Navigation.findNavController(MessageRequest.v).navigate(R.id.action_messageRequest_to_acceptReject,bundle);
            }
        });
    }

    public void setDataset(List<MessageRq>dataset){
//        Mlog.e("RecAdapter","setDataset",Integer.toString(dataset.size()));
        this.dataset=dataset;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
//        Mlog.e("RecAdapter","getItemCount");
        return this.dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //        public TextView idTextView;
        public TextView titleTextView;
        public ImageView imageView;
        public TextView descriptionTextView;
        public ImageView check;
        public LinearLayout linearLayout;
        public TextView chatType;
        public MaterialCardView card;
        public ViewHolder(View itemView) {
            super(itemView);

//            idTextView = itemView.findViewById(R.id.social_no);
            titleTextView = itemView.findViewById(R.id.social_title);
            descriptionTextView = itemView.findViewById(R.id.chatlist_lastmessage);
            check=itemView.findViewById(R.id.message_list_check);
            check.setVisibility(View.GONE);
            descriptionTextView.setTextColor(MessageRequest.myContext.getResources().getColor(R.color.mitiTextSkyBlue));
            card = (MaterialCardView) itemView.findViewById(R.id.social_card);
            linearLayout = itemView.findViewById(R.id.chatlist_linearlayout);
            chatType=itemView.findViewById(R.id.chatlist_type);
            imageView=itemView.findViewById(R.id.chatlist_image);
        }
    }
}
