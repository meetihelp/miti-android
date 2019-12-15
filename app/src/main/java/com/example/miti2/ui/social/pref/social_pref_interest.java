package com.example.miti2.ui.social.pref;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.miti2.R;
import com.example.miti2.apicompat.mitihelper;

public class social_pref_interest extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
    private static int count=1;
    private ViewGroup v1;
    private OnFragmentInteractionListener mListener;
    static public String [][]array={
            {"Writing","Singing","Dancing","Cooking","Origami/Paper crafts","Chess"},
            {"Gardening","Shopping","Cars & Bikes","Architecture","Aviation","Museum"},
            {"Fitness","Travel","Photography","Cricket","Football","Environment - Concernist and Activist","Martial arts"},
            {"Collecting","Debating","Quizzing","Psychology and Philosophy","Parenting","Lgbtq","Law","History"},
            {"Left - liberty , equality, fraternity","Right - authority, hierarchy, property"}};
    private String [][]array1={
            {"Active Indoor"},
            {"Passive Outdoor"},
            {"Active Outdoor"},
            {"Others"},
            {"Ideology"}};
    public social_pref_interest() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
//    public static social_pref_interest newInstance(String param1, String param2) {
//        social_pref_interest fragment = new social_pref_interest();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
    public final void createScreen(int index){
        int noofchild=this.v1.getChildCount();
        TextView t1=(TextView) this.v1.getChildAt(0);
        t1.setText(array1[index][0]);
        TextView t2=(TextView) this.v1.getChildAt(1);
        CheckBox cb=(CheckBox) this.v1.getChildAt(2);
        Button b=(Button)this.v1.getChildAt(noofchild-1);
//            removefromto(2,noofchild-1);
        this.v1.removeAllViews();
        this.v1.addView(t1);
        this.v1.addView(t2);
        Log.e("Control","line 100 tak to sahi hai");
        int prev=t2.getId();
        for(int j=0;j<array[index].length;j++){
            CheckBox cb1=new CheckBox(v1.getContext());
            RelativeLayout.LayoutParams temp=new RelativeLayout.LayoutParams(cb.getLayoutParams());
            temp.addRule(RelativeLayout.BELOW,prev);
            Log.e("Control",temp.debug(""));
            cb1.setLayoutParams(temp);
            prev= mitihelper.getuniqueid();
            cb1.setId(prev);
            Log.e("Control",array[index][j]);
            Log.e("Control",Integer.toString(j));
            cb1.setText(array[index][j]);
            try{
                this.v1.addView(cb1);
            }catch (Exception e){

            }
        }
        RelativeLayout.LayoutParams temp=(RelativeLayout.LayoutParams)b.getLayoutParams();
        temp.addRule(RelativeLayout.BELOW,prev);
//            b.setId();
        b.setLayoutParams(temp);
        this.v1.addView(b);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                if(count==1){
                    setEnabled(false);
                }
                createScreen(count);
                count=count-1;
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_social_pref_interest, container, false);
        Button b1=v.findViewById(R.id.social_button_pref_submit);
        this.v1=(ViewGroup) v;
        b1.setOnClickListener(this);
        return v;
    }

    public void removefromto(int from, int to){
        for(int i=from;i<to;i++){
            this.v1.removeViewAt(i);
        }
    }
    @Override
    public void onClick(View v) {
        if(count>5){
            Navigation.findNavController(v).navigate(R.id.action_social_pref_interest2_to_mainActivity);
        }else{
            System.out.println(v);
            int index=count-1;
            this.createScreen(index);
            count=count+1;

        }

    }
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
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
}
