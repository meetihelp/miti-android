package com.miti.meeti.ui.social.chat;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.GetChatContent;
import com.miti.meeti.NetworkObjects.SendChatContent;
import com.miti.meeti.R;
import com.miti.meeti.database.Chat.ChatDb;
import com.miti.meeti.database.Chat.ChatDbViewModel;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link social_chat_content.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link social_chat_content#newInstance} factory method to
 * create an instance of this fragment.
 */
public class social_chat_content extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String chatid;
    private List<ChatDb>messagesold;
    public static MessagesListAdapter<Message> adapterx;
    public static ChatDbViewModel chatDbViewModel;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public social_chat_content() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment social_chat_content.
     */
    // TODO: Rename and change types and number of parameters
    public static social_chat_content newInstance(String param1, String param2) {
        social_chat_content fragment = new social_chat_content();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        chatid=getArguments().getString("chatid");
        View v=inflater.inflate(R.layout.fragment_social_chat_content, container, false);
        chatDbViewModel= ViewModelProviders.of(this).get(ChatDbViewModel.class);
        chatDbViewModel.getmax(chatid);
        final Observer<List<ChatDb>> nameObserver = new Observer<List<ChatDb>>() {
            @Override
            public void onChanged(@Nullable final List<ChatDb> newName) {
                onChanged1(newName);
            }
        };
        chatDbViewModel.getchatbyid(chatid).observe(this,nameObserver);
        addinmessagelist(chatDbViewModel.getchatbyid(chatid).getValue());
        MainActivity.SetNavigationVisibiltity(false);
        MessageInput inputView=v.findViewById(R.id.input);
        MessagesList inputlist=v.findViewById(R.id.messagesList);
        System.out.println("Aaya me - Apoorva");
        Author temp=new Author("apoorva","apoorva kumar","");
        adapterx = new MessagesListAdapter<>("apoorva", null);
        inputlist.setAdapter(adapterx);
        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                //validate and send message
                Author temp=new Author("apoorva","apoorva kumar","");
                Message tempx=new Message("apoorva",input.toString(),temp,new Date());
                SendChatContent.request_body tempk=new SendChatContent().new request_body("Text",input.toString(),chatid);
                ChatContentRequest.sendmessage(tempk);
                adapterx.addToStart(tempx, true);
                return true;
            }
        });
        return v;
    }
    public static void dbcallback(int index){
        ChatContentRequest.getmessage(new GetChatContent().new request_body(chatid,10,index));
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

    public void onChanged1(@Nullable final List<ChatDb>messages) {
//        Set<ChatDb> ad = new HashSet<ChatDb>(messages);
//        Set<ChatDb> ad1 = new HashSet<ChatDb>(messagesold);
//        ad.removeAll(ad1);
//        messages.clear();
//        messages.addAll(ad);
        Mlog.e("Control->Inchat",Integer.toString(messages.size()));
        addinmessagelist(messages);
    }
    public void addinmessagelist(@Nullable final List<ChatDb>messages){
        if(messages==null){
            return;
        }
        List<Message>temp12=new ArrayList<>();
        for (ChatDb tempx:messages){
            Author temp45=new Author(tempx.UserId,"","");
            Message temp34=new Message(tempx.MessageId,tempx.MessageContent,temp45,new Date());
            temp12.add(temp34);
        }
        social_chat_content.adapterx.addToEnd(temp12,false);
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
