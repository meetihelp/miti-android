package com.miti.meeti.ui.social.messageRequest;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.miti.meeti.MainActivity;
import com.miti.meeti.R;
import com.miti.meeti.database.Chat.ChatListDb;
import com.miti.meeti.database.Contact.ContactDb;
import com.miti.meeti.mitiutil.Logging.Mlog;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewMessage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewMessage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewMessage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static FragmentActivity myContext;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private MessageListAdapter recyclerAdapter;
    public static View v;
    public static String from;
    public static String type;
    public static String data;
    private List<ListModel>dataset=new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    public NewMessage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewMessage.
     */
    // TODO: Rename and change types and number of parameters
    public static NewMessage newInstance(String param1, String param2) {
        NewMessage fragment = new NewMessage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        MainActivity.toolbar_text.setText("Message");
        inflater.inflate(R.menu.moodboard_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle=getArguments();
        if(bundle!=null){
            from=bundle.getString("from");
            type=bundle.getString("type");
            data=bundle.getString("content");
        }
        List<ChatListDb>temp=MainActivity.chatListDbViewModel.getold();
        for(ChatListDb tempx:temp){
            ListModel tempy=new ListModel(tempx);
            dataset.add(tempy);
        }


        List<ContactDb>tempz=getContact();
        for(ContactDb temp123:tempz){
            ListModel tempo=new ListModel(temp123);
            dataset.add(tempo);
        }
        Mlog.e("inNewMessage",Integer.toString(dataset.size()));
        v=inflater.inflate(R.layout.fragment_new_message, container, false);
        recyclerView=v.findViewById(R.id.newMessage_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(myContext));
        recyclerAdapter=new MessageListAdapter();
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setDataset(dataset);
        TextInputEditText UserInput=v.findViewById(R.id.list_search);
//        String from=bundle.getString("from");
        UserInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String userInput=s.toString();
                Mlog.e(s.toString());
                List<ListModel> NewContacts=new ArrayList<>();
                for(ListModel contact: dataset){
                    if(contact.Name.toLowerCase().trim().contains(userInput.toLowerCase().trim())){
                        NewContacts.add(contact);
                    }
                }
                Mlog.e("inNewMessage",Integer.toString(NewContacts.size()));
                recyclerAdapter.setDataset(NewContacts);
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        myContext=(FragmentActivity)context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        MainActivity.toolbar_text.setText("MEETi");
        mListener = null;
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
    public List<ContactDb> getContact(){
        List<ContactDb> arrayList=new ArrayList<>();
        Cursor cursor=getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while (cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            ContactDb temp=new ContactDb(phone,name,-1);
            arrayList.add(temp);
        }
        return arrayList;
    }
}
