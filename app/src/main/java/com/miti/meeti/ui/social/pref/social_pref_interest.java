package com.miti.meeti.ui.social.pref;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.miti.meeti.NetworkObjects.PrefInterest;
import com.miti.meeti.R;
import com.miti.meeti.apicompat.mitihelper;
import com.miti.meeti.database.Cookie.CookieViewModel;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;

import java.util.ArrayList;
import java.util.List;

public class social_pref_interest extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
    private static int count=0;
    private CookieViewModel cookieViewModel;
    private ViewGroup v1;
    private OnFragmentInteractionListener mListener;
    static public String [][]array={
            {"Reading - Fiction or Non-Fiction","Chilling - Netflix, Spotify etc.","Pet - Caring and Playing","Diva - Fashion and Makeup","Gamer - Pubg, Fortnight and Good old vcop2"},
            {"Writing","Singing","Dancing","Cooking","Origami/Paper crafts","Chess"},
            {"Gardening","Shopping","Cars & Bikes","Architecture","Aviation","Museum"},
            {"Fitness","Travel","Photography","Cricket","Football","Environment - Concernist and Activist","Martial arts"},
            {"Collecting","Debating","Quizzing","Psychology and Philosophy","Parenting","Lgbtq","Law","History"},
            {"Left - liberty , equality, fraternity","Right - authority, hierarchy, property"}};
    private String [][]array1={
            {"Indoor - Passive"},
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
        this.v1.removeAllViews();
        this.v1.addView(t1);
        this.v1.addView(t2);
        int prev=t2.getId();
        for(int j=0;j<array[index].length;j++){
            CheckBox cb1=new CheckBox(this.v1.getContext());
            cb1.setText(array[index][j]);
            cb1.setTextColor(getResources().getColor(R.color.mitiOrange));
//            cb1.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
//            cb1.setScaleY(new Float(1.2));
            cb1.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) cb.getLayoutParams();
            params.bottomMargin=20;
            cb1.setLayoutParams(params);
            try{
                this.v1.addView(cb1);
            }catch (Exception e){

            }
        }
//            b.setId();
        this.v1.addView(b);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                if(count==0){
                    Navigation.findNavController(v1).navigateUp();
                }
                if(count>=1){
                    count=count-1;
                    createScreen(count);
                }
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
        try{
            this.count=getArguments().getInt("Preference");
        }catch (Exception e){
            this.count=0;
        }
        this.createScreen(this.count);
        return v;
    }

    public void removefromto(int from, int to){
        for(int i=from;i<to;i++){
            this.v1.removeViewAt(i);
        }
    }
    private int check_helper(View v,int cmax,int cmin){
        int noofchild=this.v1.getChildCount();
        int checked_countx=0;
        for(int i=2;i<noofchild-1;i++){
            View temp=this.v1.getChildAt(i);
            CheckBox temp1=(CheckBox)temp;
            if(temp1.isChecked()){
                checked_countx=checked_countx+1;
            }
        }
        if(checked_countx<cmin){
            ToastHelper.ToastFun(v.getContext(),"Please select atleast one");
            return -1;
        }
        if(checked_countx>cmax){
            ToastHelper.ToastFun(v.getContext(),"You can select at max "+Integer.toString(cmax));
            return -1;
        }
        return 1;
    }
    private int check_send(){
        List<String>allpref=new ArrayList<>();
        int noofchild=this.v1.getChildCount();
        for(int i=2;i<noofchild-1;i++){
            View temp=this.v1.getChildAt(i);
            CheckBox temp1=(CheckBox)temp;
            if(temp1.isChecked()){
                Mlog.e(array[count][i-2].split(" ")[0]);
                allpref.add(array[count][i-2].split(" ")[0]);
            }
        }
        if(allpref.size()==0){
            allpref.add("");
            allpref.add("");
        }else if(allpref.size()==1){
            allpref.add("");
        }
        PrefInterest.request_object tempyu=new PrefInterest().new request_object(allpref.get(0),allpref.get(1));
        Gson gson=new Gson();
        String data=gson.toJson(tempyu);
        cookieViewModel= ViewModelProviders.of(this).get(CookieViewModel.class);
        String MeetiCookie= cookieViewModel.getCookie1();
        InterestPost request=new InterestPost();
        request.execute("updatePreference",data,MeetiCookie);
        return 1;
    }
    @Override
    public void onClick(View v) {
        if(count==5){
            if(check_helper(v,1,1)!=1){
                return;
            }
        }else{
            if(check_helper(v,2,1)!=1){
                return;
            }
        }
        if(count==5){
            Navigation.findNavController(v).navigate(R.id.action_social_pref_interest2_to_mainActivity);
        }else{
            check_send();
            count=count+1;
            this.createScreen(count);
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
