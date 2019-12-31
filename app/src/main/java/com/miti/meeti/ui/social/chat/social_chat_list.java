package com.miti.meeti.ui.social.chat;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miti.meeti.NetworkObjects.ChatList;
import com.miti.meeti.R;
import com.miti.meeti.database.Chat.ChatListViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link social_chat_list.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link social_chat_list#newInstance} factory method to
 * create an instance of this fragment.
 */
public class social_chat_list extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static int chatlist_offset;
    public static ChatListViewModel chatListViewModel;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private social_chat_list_adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private OnFragmentInteractionListener mListener;

    public social_chat_list() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment social_chat_list.
     */
    // TODO: Rename and change types and number of parameters
    public static social_chat_list newInstance(String param1, String param2) {
        social_chat_list fragment = new social_chat_list();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //set chatlistoffset;
        chatlist_offset=getChatlist_offset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_social_chat_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
//        String []myDataset={"apoorva","kumar"};
        mAdapter = new social_chat_list_adapter();
        recyclerView.setAdapter(mAdapter);
        chatListViewModel= ViewModelProviders.of(this).get(ChatListViewModel.class);
        chatListViewModel.getTodos().observe(this, new Observer<List<ChatList.chatlist_object>>() {
            @Override
            public void onChanged(List<ChatList.chatlist_object> newChatlist) {
                Log.e("Control","On changed called for social chat list ");
                mAdapter.setChatList(newChatlist);
            }
        });
        ChatListRequest.getinitialnews();
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public int getChatlist_offset(){
        return 0;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
