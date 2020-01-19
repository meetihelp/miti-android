package com.miti.meeti.ui.social.messageRequest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miti.meeti.MainActivity;
import com.miti.meeti.R;
import com.miti.meeti.database.Request.MessageRq;
import com.miti.meeti.ui.social.chat.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageRequest.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageRequest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageRequest extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static FragmentActivity myContext;
    public static View v;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private SentAdapter sentAdapter;
    private RecyclerView recyclerView1;
    private RecAdapter recAdapter;
    private OnFragmentInteractionListener mListener;
    private List<MessageRq>dataset=new ArrayList<>();
    private List<MessageRq>dataset1=new ArrayList<>();
    public MessageRequest() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageRequest.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageRequest newInstance(String param1, String param2) {
        MessageRequest fragment = new MessageRequest();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        MainActivity.SetNavigationVisibiltity(false);
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
        v=inflater.inflate(R.layout.fragment_message_request, container, false);
        LiveData<List<MessageRq>>all= MainActivity.messageRqViewModel.getAll();
        recyclerView=v.findViewById(R.id.sent);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(myContext));
        sentAdapter=new SentAdapter();
        recyclerView.setAdapter(sentAdapter);

        recyclerView1=v.findViewById(R.id.received);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(myContext));
        recAdapter=new RecAdapter();
        recyclerView1.setAdapter(recAdapter);
        final Observer<List<MessageRq>> nameObserver = new Observer<List<MessageRq>>() {
            @Override
            public void onChanged(@Nullable final List<MessageRq> newName) {
                // Update the UI, in this case, a TextView.
                dataset.clear();
                dataset1.clear();
                for(MessageRq temp:newName){
                    if(temp.Tag.contains("sen")){
                        dataset.add(temp);
                    }else if(temp.Tag.contains("rec")){
                        dataset1.add(temp);
                    }
                }
                sentAdapter.setDataset(dataset);
                recAdapter.setDataset(dataset1);
            }
        };
        all.observe(this,nameObserver);
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
        super.onAttach(context);
        myContext=(FragmentActivity)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
}
