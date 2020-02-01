package com.miti.meeti.ui.social.pooling;

import android.os.Bundle;

import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.AllUrl;
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

import java.util.List;

public class GroupPoolStatus extends POSTRequest {
    public static void helper(){
        Gson gson=new Gson();
        GroupPoolStatus getPoolStatus=new GroupPoolStatus();
        try{
            getPoolStatus.execute(AllUrl.url_social().get(3),gson.toJson(new Mitigps(MainActivity.Latitude,MainActivity.Longitude)),MainActivity.MeetiCookie);
        }catch (Exception e){}
    }
    public class response{
        public Integer Code;
        public String Message;
        public String IPIP;
        List<String> Interest;
    }
    @Override
    protected void onPostExecute(RequestHelper result) {
        Gson gson=new Gson();
        GroupPoolStatus.response json=gson.fromJson(result.getData(),GroupPoolStatus.response.class);
        if(json==null){
            ToastHelper.ToastFun(SocialFragment.v.getContext(),"Some error,try again");
        }else if(json.Code==200){
            Bundle bundle=new Bundle();
            bundle.putStringArray("Interest",json.Interest.toArray(new String[json.Interest.size()]));
            Navigation.findNavController(SocialFragment.v).navigate(R.id.action_miti_social_to_groupPool,bundle);
        }else if(json.Code==2003){
            Bundle bundle=new Bundle();
            bundle.putStringArray("Interest",json.Interest.toArray(new String[json.Interest.size()]));
            Navigation.findNavController(SocialFragment.v).navigate(R.id.action_miti_social_to_groupPool,bundle);
        }
        SocialFragment.bottomSheetDialog.dismiss();
    }
}
