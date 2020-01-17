package com.miti.meeti.ui.social.pref;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.R;
import com.miti.meeti.database.Keyvalue.KeyvalueViewModel;
import com.miti.meeti.database.Keyvalue.keyvalue;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.miti.meeti.mitiutil.uihelper.CheckView;
import com.miti.meeti.mitiutil.uihelper.MitiLoadingDialog;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;
import com.miti.meeti.ui.social.pooling.GetInPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.miti.meeti.apicompat.mitihelper.getuniqueid;

public class social_pref_ipip extends Fragment implements View.OnClickListener{
    private OnFragmentInteractionListener mListener;
    public ViewGroup v1;
    public int perpage=5;
    public Integer count;
    public Button skip;
    public TextView pageno;
    public LinearLayout layout;
    public static View v;
    public CheckView cv;
    public Button b1;
    public static MitiLoadingDialog bottomSheetDialog;
    public static FragmentActivity myContext;

    @Override
    public void onAttach(@NonNull Context context) {
        myContext=(FragmentActivity)context;
        super.onAttach(context);
    }

    HashMap<Integer, Integer> map= new HashMap<>();
    HashMap<Integer, Integer> map1= new HashMap<Integer, Integer>(){{
        put(R.id.radioButton1,2);
        put(R.id.radioButton2,1);
        put(R.id.radioButton3,0);
        put(R.id.radioButton4,-1);
        put(R.id.radioButton5,-2);
    }};
    public String []question={"I am the life of the party.","I am always self involved.","I am always prepped and prepared.",
            "I get stressed out easily.","I have a rich vocabulary.","I don't talk a lot.","I am interested in humans and human behaviour.",
            "I leave my belongings around.","I am relaxed most of the time.","I have difficulty understanding abstract ideas."
            ,"I feel comfortable around people.","A bit rude to people I don't like","I pay attention to details.","I worry about things.",
            "I have a vivid imagination.","I keep myself in the background.","I sympathize with other's feelings.","I make a mess of things.",
            "I am always charged.","I am not interested in abstract ideas.","I love starting and orchestrating conversations.","I am not interested in other people's problems.",
            "I get work done right away.","I am easily distracted.","I have awesome and creative ideas.","I have little to say.","I have a soft heart.",
            "I often forget to put things back in their proper place.","I get upset easily.","I do not have a good imagination.",
            "I talk to a lot of different people at parties.","I am not really interested in others.","I like order and discipline.","I have a lot of moodswings.",
            "I am quick to understand things.","I don't like to draw attention to myself.","I take time out for others.","I neglect my duties.","I have frequent mood swings.",
            "I use difficult words.","I don't mind being the center of attention.","I Feel others' emotions. Baiscally I am an empath","I follow a schedule and routine.","I get irritated easily.","I spend time reflecting on things.",
            "I am quiet around strangers.","I make people feel at ease.","I am exacting in my work.","I often feel blue.","I am full of ideas."};
    final String[] Options = {"Keep filling", "Go to Pool"};
    public AlertDialog.Builder window;
    public social_pref_ipip() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.SetNavigationVisibiltity(false);
    }

    public void createScreen(int index){
        map.clear();
        pageno.setText(Integer.toString(index+1)+" of 10");
        if(index<6){
            pageno.setText(Integer.toString(index+1)+" of  10, Skip after page 5");
        }
        this.v1.removeAllViews();
        if(index>=5){
            skip.setVisibility(View.VISIBLE);
        }
        if(index==9){
            b1.setText("Go to pool");
        }
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
        v=inflater.inflate(R.layout.fragment_social_pref_ipip, container, false);
        skip=v.findViewById(R.id.button_skip_ipip);
        setSkipListener();
        skip.setVisibility(View.GONE);
        bottomSheetDialog = MitiLoadingDialog.newInstance();
        pageno=v.findViewById(R.id.ipip_page_no);
        layout=v.findViewById(R.id.checkl_ipip);
        cv=v.findViewById(R.id.check_ipip);
        layout.setVisibility(View.GONE);
        window = new AlertDialog.Builder(v.getContext());
        Bundle bundle=getArguments();
        count=bundle.getInt("screen");
        if(count==null){
            count=0;
        }
        this.v1=(ViewGroup)v.findViewById(R.id.llipip);
        createScreen(count);
        b1=v.findViewById(R.id.button4);
        b1.setOnClickListener(this);
        return v;
    }
    public void setSkipListener(){
        skip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v1) {
                window.setTitle("Fill all to meet weird beautiful humans you might just click with");
                window.setItems(Options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            //first option clicked, do this...
                            dialog.dismiss();
                        }else if(which == 1){
                            RequestHelper rq=GetInPool.helper();
                            if(rq==null){
                                ToastHelper.ToastFun(myContext,"Try again");
                            }else{
                                Gson gson=new Gson();
                                GetInPool.response gip=gson.fromJson(rq.getData(),GetInPool.response.class);
                                if(gip==null){
                                    ToastHelper.ToastFun(myContext,"Try again");
                                }
                                if(gip.Code==200){
                                    KeyvalueViewModel keyvalueViewModel=MainActivity.keyvalueViewModel;
                                    keyvalueViewModel.insert(new keyvalue("pooling",gip.PoolStatus.Status));
                                    Navigation.findNavController(v).navigateUp();
                                }
                            }
                            return;
                            //second option clicked, do this...
                        }else{
                            //theres an error in what was selected

                        }
                    }
                });
                window.show();
            }
        });
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
    public void onClick(View v1) {
        List<Integer>temp=getselected(count);
        if(temp==null){
            return;
        }
        bottomSheetDialog.show(myContext.getSupportFragmentManager(),"ipipx");
        RequestHelper data=social_ipip_request.helper(temp,new Integer(count));
        if(!onPostExecute(data)){
            return;
        }
        bottomSheetDialog.dismiss();
//        Mlog.e(Integer.toString(temp.size()));
        count=count+1;
        this.createScreen(count);
    }
    public boolean onPostExecute(RequestHelper result){
        Gson gson=new Gson();
        String res=result.getData();
        social_ipip_request.response temp2=gson.fromJson(res,social_ipip_request.response.class);
        if(temp2==null){
            social_pref_ipip.bottomSheetDialog.dismiss();
            ToastHelper.ToastFun(social_pref_ipip.myContext,"Try again");
            return false;
        }else if(temp2.Code==200){
            return true;
        }else {
            ToastHelper.ToastFun(myContext,temp2.Message);
            Mlog.e("in social_pref_ipip",temp2.Message);
            return false;
        }
    }
    List<Integer>getselected(int count){
        List<Integer>temp=new ArrayList<>();
        for(int i=count*this.perpage;i<(count+1)*perpage;i++){
            int idx=map.get(i);
            RadioGroup radioGroup = (RadioGroup) this.v1.findViewById(idx);
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if(selectedId==-1){
                ToastHelper.ToastFun(v1.getContext(),"Fill all");
                return null;
            }else{
                Mlog.e(map1.get(selectedId).toString());
                temp.add(map1.get(selectedId));
            }

        }
        return temp;
    }
}
