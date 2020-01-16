package com.miti.meeti.ui.privacy;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.miti.meeti.MainActivity;
import com.miti.meeti.R;
import com.miti.meeti.database.Diary.Moodboard;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.uihelper.MitiDiff;
import com.miti.meeti.ui.social.chat.BottomDialogFragment;
import com.miti.meeti.ui.social.chat.social_chat_list;

import java.util.ArrayList;
import java.util.HashMap;
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
    public void onBindViewHolder(@NonNull final MoodboardHolder holder, final int position) {
        StaggeredGridLayoutManager.LayoutParams lp =
                (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
//        holder.itemView.setBackgroundResource(R.color.mitiOrange); //without theme));
        Moodboard currentFeed=temp.get(position);
        holder.temp1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("resourceid", temp.get(position).Mimetype);
                if(temp.get(position).Mimetype.contains("text")){
                    args.putString("data", temp.get(position).Content);
                }else{
                    args.putString("data", temp.get(position).ImagePath);
                }
                MyDialogFragment bottomSheetDialog = MyDialogFragment.newInstance();
                bottomSheetDialog.setArguments(args);
                bottomSheetDialog.show(PrivacyFragment.myContext.getSupportFragmentManager(),"hithere");
            }
        });
        holder.temp2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity.moodboardViewModel.delete(temp.get(position));
            }
        });
        Mlog.e("Aaya to main in bind view holder",Integer.toString(position),currentFeed.Content,currentFeed.Mimetype);
        if(currentFeed.Mimetype.equals("text")){
            Mlog.e("currentFeed.Content");
            holder.temp.setText(currentFeed.Content);
        }else if(currentFeed.Mimetype.equals("image")){
            Glide.with(PrivacyFragment.v.getContext()).load(currentFeed.ImagePath).into(holder.temp1);
        }
    }

    @Override
    public long getItemId(int position) {
        return temp.get(position).uid;
    }

    @Override
    public int getItemCount() {
        return temp.size();
    }

    public void setTemp(List<Moodboard>temp){
        if(this.temp.size()>temp.size()){
            this.temp=temp;
            notifyDataSetChanged();
            return;
        }
        List<Moodboard>diff=new MitiDiff<Integer,Moodboard>().getx(this.temp,temp,"uid");
        this.temp=temp;
        if(diff.size()>0){
            notifyItemRangeInserted(0,diff.size());
        }else{
            notifyDataSetChanged();
        }

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
        private ImageView temp2;
        private MaterialCardView moodcard;
        public MoodboardHolder(@NonNull View itemView) {
            super(itemView);
            temp=(TextView) itemView.findViewById(R.id.moodboard_content);
            temp1=(ImageView) itemView.findViewById(R.id.moodboard_image);
            temp2=(ImageView) itemView.findViewById(R.id.delete_mood_object);
            temp2.setVisibility(View.GONE);
            moodcard=(MaterialCardView)itemView.findViewById(R.id.Miti_Moodboard_card);
            temp2.setClickable(true);
            temp1.setLongClickable(true);
            temp1.setClickable(true);
            temp.setLongClickable(true);
            temp1.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    temp2.setVisibility(View.VISIBLE);
                    return true;
                }
            });
        }
    }
    public static class MyDialogFragment extends DialogFragment {
        private View v;
        private ScaleGestureDetector mScaleDetector;
        private static final int INVALID_POINTER_ID = -1;
        static MyDialogFragment newInstance() {
            MyDialogFragment f = new MyDialogFragment();
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.moodboard_dialog, container, false);
            Bundle mArgs = getArguments();
            String myValue = mArgs.getString("resourceid");
            v.findViewById(R.id.delete_mood_object).setVisibility(View.GONE);
            if(myValue.contains("image")){
                Glide.with(v.getContext()).load(mArgs.getString("data")).into((PhotoView) v.findViewById(R.id.moodboard_image));
            }else if(myValue.contains("text")){
                TextView temp=(TextView) v.findViewById(R.id.moodboard_content);
                temp.setText(mArgs.getString("data"));
            }
            return v;
        }

    }
}

