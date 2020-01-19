package com.miti.meeti.ui.social.pooling;

import android.os.Bundle;

import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.Mitigps;
import com.miti.meeti.R;
import com.miti.meeti.database.Keyvalue.KeyvalueViewModel;
import com.miti.meeti.database.Keyvalue.keyvalue;
import com.miti.meeti.mitiutil.network.GETRequest;
import com.miti.meeti.mitiutil.network.Keyvalue;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;
import com.miti.meeti.ui.social.SocialFragment;

import static com.miti.meeti.ui.social.SocialFragment.tempd;

public class GetPoolStatus extends POSTRequest {
    Gson gson=new Gson();
    public static void helper(){
        Gson gson=new Gson();
        GetPoolStatus getPoolStatus=new GetPoolStatus();
        try{
            RequestHelper requestHelper=getPoolStatus.execute("getPoolStatus",gson.toJson(new Mitigps(MainActivity.Latitude,MainActivity.Longitude)),MainActivity.MeetiCookie).get();
        }catch (Exception e){}

    }
    public class response{
        public int Code;
        public String Message;
        public String MatchUserId;
        public String Status;
        public String CreatedAt;
        public String MatchTime;
        public int IPIP;
    }
    @Override
    protected void onPostExecute(RequestHelper result) {
        GetPoolStatus.response json=gson.fromJson(result.getData(),GetPoolStatus.response.class);
        if(json==null){
            ToastHelper.ToastFun(SocialFragment.v.getContext(),"Some error,try again");
        }else if(json.Code==200){
            Bundle bundle=new Bundle();
            bundle.putInt("screen",json.IPIP);
            if(json.Status.length()==0){
                Navigation.findNavController(SocialFragment.v).navigate(R.id.action_miti_social_to_social_pref_ipip,bundle);
            }else{
                KeyvalueViewModel keyvalueViewModel=MainActivity.keyvalueViewModel;
                keyvalueViewModel.insert(new keyvalue("pooling",json.Status));
                tempd.setText("Status : "+json.Status);
            }


        }else if(json.Code==2003){
            Bundle bundle=new Bundle();
            bundle.putInt("screen",json.IPIP);
            Navigation.findNavController(SocialFragment.v).navigate(R.id.action_miti_social_to_social_pref_ipip,bundle);
        }
        SocialFragment.bottomSheetDialog.dismiss();
    }
}
