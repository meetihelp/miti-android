package com.miti.meeti.ui.social.pref;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.miti.meeti.R;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;

import java.util.HashMap;
import java.util.Map;

import static com.miti.meeti.apicompat.mitihelper.getuniqueid;

public class social_pref_ipip extends Fragment implements View.OnClickListener{
    private OnFragmentInteractionListener mListener;
    public ViewGroup v1;
    public int perpage=5;
    public int count=0;
    HashMap<Integer, Integer> map= new HashMap<>();
    HashMap<Integer, Integer> map1= new HashMap<Integer, Integer>(){{
        put(R.id.radioButton1,1);
        put(R.id.radioButton2,2);
        put(R.id.radioButton3,3);
        put(R.id.radioButton4,4);
        put(R.id.radioButton5,5);
    }};
    public String []question={"Am the life of the party.","Feel little concern for others.","Am always prepared.",
            "Get stressed out easily.","Have a rich vocabulary.","Don't talk a lot.","Am interested in humans.",
            "Leave my belongings around.","Am relaxed most of the time.","Have difficulty understanding abstract ideas."
            ,"Feel comfortable around people.","A bit rude to people","Pay attention to details.","Worry about things.",
            "Have a vivid imagination.","Keep myself in the background.","Sympathize with others' feelings.","Make a mess of things.",
            "Seldom feel blue.","Am not interested in abstract ideas.","Start conversations.","Am not interested in other people's problems.",
            "Get chores done right away.","Am easily disturbed.","Have excellent ideas.","Have little to say.","Have a soft heart.",
            "Often forget to put things back in their proper place.","Get upset easily.","Do not have a good imagination.",
            "Talk to a lot of different people at parties.","Am not really interested in others.","Like order and discipline","Have a lot of moodswings",
            "Am quick to understand things.","Don't like to draw attention to myself.","Take time out for others.","Neglect my duties.","Have frequent mood swings.",
            "Use difficult words.","Don't mind being the center of attention.","Feel others' emotions.","Follow a schedule.","Get irritated easily.","Spend time reflecting on things.",
            "Am quiet around strangers.","Make people feel at ease.","Am exacting in my work.","Often feel blue.","Am full of ideas."};
    public social_pref_ipip() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void createScreen(int index){
        map.clear();
        this.v1.removeAllViews();
        for(int i=index*this.perpage;i<(index+1)*perpage;i++){
            View view = LayoutInflater.from(this.getContext()).inflate(R.layout.ipip_helper, null);
            TextView t1=view.findViewById(R.id.textView5);
            RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radiogroup1);
            int idxs=getuniqueid();
            map.put(i,idxs);
            radioGroup.setId(idxs);
            t1.setText(this.question[i]);
            this.v1.addView(view);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_social_pref_ipip, container, false);
        Bundle bundle=getArguments();
        int screen=bundle.getInt("screen");
        this.v1=(ViewGroup)v.findViewById(R.id.llipip);
        createScreen(screen);
        Button b1=v.findViewById(R.id.button4);
        b1.setOnClickListener(this);
        return v;
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
    @Override
    public void onClick(View v) {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                if(count==0){
                    setEnabled(false);
                }else{
                    count=count-1;
                    createScreen(count);
                }
            }
        };
        if(count!=0){
            requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        }
        if(count>5){

        }else{
            for(int i=count*this.perpage;i<(count+1)*perpage;i++){
                int idx=map.get(i);
                RadioGroup radioGroup = (RadioGroup) this.v1.findViewById(idx);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if(selectedId==-1){
                    ToastHelper.ToastFun(v1.getContext(),"Fill all");
                    return;
                }else{
                    Log.e("Control",Integer.toString(map1.get(selectedId)));
                }

            }
            count=count+1;
            this.createScreen(count);

        }

    }
}
