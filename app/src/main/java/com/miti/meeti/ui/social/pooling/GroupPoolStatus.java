package com.miti.meeti.ui.social.pooling;

import android.os.Bundle;

import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.R;
import com.miti.meeti.database.Keyvalue.KeyvalueViewModel;
import com.miti.meeti.database.Keyvalue.keyvalue;
import com.miti.meeti.mitiutil.network.GETRequest;
import com.miti.meeti.mitiutil.network.Keyvalue;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;
import com.miti.meeti.ui.social.SocialFragment;

import java.util.List;

public class GroupPoolStatus extends GETRequest {
    public static void helper(){
        GroupPoolStatus getPoolStatus=new GroupPoolStatus();
        getPoolStatus.execute(Keyvalue.GetHashMap(new Keyvalue("url","/groupPoolStatus"),
                new Keyvalue("Miti-Cookie", MainActivity.MeetiCookie)));
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

        }
        SocialFragment.bottomSheetDialog.dismiss();
    }
}
