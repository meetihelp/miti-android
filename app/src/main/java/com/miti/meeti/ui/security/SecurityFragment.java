package com.miti.meeti.ui.security;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.miti.meeti.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.checkSelfPermission;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SecurityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SecurityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecurityFragment extends Fragment implements  View.OnClickListener {
    private Context mContext;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private List<Contacts> contacts = new ArrayList<>();
    private static EditText UserInput;
    private static ChipGroup mChipGroupFamily;
    private static Button mButtonFamily;
    private static ChipGroup mChipGroupFriends;
    private static Button mButtonFriends;
    private static ChipGroup mChipGroupAcquintence;
    private static Button mButtonAcquintence;
    private static Button mDialogCloseButton;
    private static LinearLayout linearLayout;
    private static int trustChainId=0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static View v23;
    private OnFragmentInteractionListener mListener;

    public SecurityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecurityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecurityFragment newInstance(String param1, String param2) {
        SecurityFragment fragment = new SecurityFragment();
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
        View view= inflater.inflate(R.layout.fragment_security, container, false);
        v23=view;
//
//        FragmentCommunication communication=new FragmentCommunication() {
//            @Override
//            public void respond(int position,String name,String job) {
//                SecurityFragment fragmentB=new SecurityFragment();
//                Bundle bundle=new Bundle();
//                bundle.putString("NAME",name);
//                bundle.putString("JOB",job);
//                fragmentB.setArguments(bundle);
//                FragmentManager manager=getFragmentManager();
//                FragmentTransaction transaction=manager.beginTransaction();
//                transaction.replace(R.id.dumper,fragmentB).commit();
//            }}
        final Dialog dialog=new Dialog(view.getContext(),R.style.CustomDialog);
        dialog.setContentView(R.layout.security_dialog_content);
//
//
        recyclerView=(RecyclerView)dialog.findViewById(R.id.recyclerView);
        UserInput=(EditText)dialog.findViewById(R.id.txtName);
        mChipGroupFamily=(ChipGroup)view.findViewById(R.id.chipGroupFamily);
        mChipGroupFriends=(ChipGroup)view.findViewById(R.id.chipGroupFriends);
        mChipGroupAcquintence=(ChipGroup)view.findViewById(R.id.chipGroupAcquintence);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        mButtonFamily=(Button)view.findViewById(R.id.buttonFamily);
        mButtonFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trustChainId=1;
                dialog.show();
//                linearLayout.setVisibility(View.VISIBLE);
            }
        });
        mButtonFriends=(Button)view.findViewById(R.id.buttonFriends);
        mButtonFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trustChainId=2;
                dialog.show();
//                linearLayout.setVisibility(View.VISIBLE);
            }
        });
        mButtonAcquintence=(Button)view.findViewById(R.id.buttonAcquintence);
        mButtonAcquintence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trustChainId=3;
                dialog.show();
//                linearLayout.setVisibility(View.VISIBLE);
            }
        });
        mDialogCloseButton=(Button)dialog.findViewById(R.id.dialogCloseButton);
        mDialogCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        getContacts();
        recyclerAdapter=new RecyclerAdapter(view.getContext(),contacts);
        recyclerView.setAdapter(recyclerAdapter);
//
        UserInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String userInput=s.toString();
                List<Contacts> NewContacts=new ArrayList<>();
                for(Contacts contact: contacts){
                    if(contact.getProfileName().contains(userInput)){
                        NewContacts.add(contact);
                    }
                }

                recyclerAdapter=new RecyclerAdapter(getContext(),NewContacts);
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context){
//        super.onAttach(context);
//        this.mContext=context;
//    }
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


//    public static void onItemSelected(Contacts contact) {
//        Chip chip=new Chip(v23.getContext());
//        chip.setText(contact.getProfileName());
//        chip.setTextSize(25);
//        chip.setChipIcon(ContextCompat.getDrawable(v23.getContext(),contact.getPicId()));
//        chip.setCloseIconVisible(true);
//        chip.setChipIconSize(100);
//        chip.setChipStartPadding(0);
//        chip.setCheckable(false);
//        chip.setClickable(false);
//        TrusteChainHandler(chip);
////        chip.setOnCloseIconClickListener(this);
//    }

    private void getContacts(){
//        List <String> Names= Arrays.asList(getResources().getStringArray(R.array.contact));
        List<String>Names=getPhoneNumber();
        int[] imageId={R.mipmap.gaurav};
        int count=0;
        for(String name:Names){
            contacts.add(new Contacts(name,imageId[0]));
            count++;
        }
    }

    @Override
    public void onClick(View v) {
//        Chip chip=(Chip)v;
//        mChipGroupFamily.removeView(chip);
    }

    static public void TrusteChainHandler(Contacts contact){
        Chip chip=new Chip(v23.getContext());
        chip.setText(contact.getProfileName());
        chip.setTextSize(25);
        chip.setChipIcon(ContextCompat.getDrawable(v23.getContext(),contact.getPicId()));
        chip.setCloseIconVisible(true);
        chip.setChipIconSize(100);
        chip.setChipStartPadding(0);
        chip.setCheckable(false);
        chip.setClickable(false);
        if(trustChainId==1) {
            int flag=1;
            if(mChipGroupFamily.getChildCount()>=6){
                flag=0;
                Toast.makeText(v23.getContext(),"Maximum allowed is 6",Toast.LENGTH_SHORT).show();
            }
            for(int i=0;i<mChipGroupFamily.getChildCount();i++){
                Chip c=(Chip)mChipGroupFamily.getChildAt(i);
                if(c.getText()==chip.getText()){
                    Toast.makeText(v23.getContext(),"Contact already added",Toast.LENGTH_SHORT).show();
                    flag=0;
                    break;
                }
            }
            if(flag==1) {
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Chip c = (Chip) v;
                        mChipGroupFamily.removeView(c);
                    }
                });
                mChipGroupFamily.addView(chip);
                mChipGroupFamily.setVisibility(View.VISIBLE);
            }
        }
        else if(trustChainId==2){
            int flag=1;
            if(mChipGroupFriends.getChildCount()>=6){
                flag=0;
                Toast.makeText(v23.getContext(),"Maximum allowed is 6",Toast.LENGTH_SHORT).show();
            }
            for(int i=0;i<mChipGroupFriends.getChildCount();i++){
                Chip c=(Chip)mChipGroupFriends.getChildAt(i);
                if(c.getText()==chip.getText()){
                    Toast.makeText(v23.getContext(),"Contact already added",Toast.LENGTH_SHORT).show();
                    flag=0;
                    break;
                }
            }
            if(flag==1) {
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Chip c = (Chip) v;
                        mChipGroupFriends.removeView(c);
                    }
                });
                mChipGroupFriends.addView(chip);
                mChipGroupFriends.setVisibility(View.VISIBLE);
            }
        }else {
            int flag=1;
            if(mChipGroupAcquintence.getChildCount()>=6){
                Toast.makeText(v23.getContext(),"Maximum allowed is 6",Toast.LENGTH_SHORT).show();
                flag=0;
            }
            for(int i=0;i<mChipGroupAcquintence.getChildCount();i++){
                Chip c=(Chip)mChipGroupAcquintence.getChildAt(i);
                if(c.getText()==chip.getText()){
                    Toast.makeText(v23.getContext(),"Contact already added",Toast.LENGTH_SHORT).show();
                    flag=0;
                    break;
                }
            }
            if(flag==1) {
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Chip c = (Chip) v;
                        mChipGroupAcquintence.removeView(c);
                    }
                });
                mChipGroupAcquintence.addView(chip);
                mChipGroupAcquintence.setVisibility(View.VISIBLE);
            }
        }
    }

    public List<String> getPhoneNumber(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && checkSelfPermission(getContext(),Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},1);
            return null;
        }
        else {
            return getContact();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getContact();
            }
        }
    }

    public List<String> getContact(){
        List<String> arrayList=new ArrayList<>();
        Cursor cursor=getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while (cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            arrayList.add(name+"\n"+phone);
        }
        return arrayList;
    }
}