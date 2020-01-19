package com.miti.meeti.ui.login;

import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.miti.meeti.R;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;

public class ProfilePostRequest extends POSTRequest {
    public class request{
        public Integer Code;
        public String Message;
    }
    @Override
    protected void onPostExecute(RequestHelper result) {
        if(result==null){
            ToastHelper.ToastFun(profile_creation.v1.getContext(),"Request failed try again");
            return;
        }
        Gson gson=new Gson();
        request temp=gson.fromJson(result.getData(),request.class);
        if(temp==null){
            ToastHelper.ToastFun(profile_creation.v1.getContext(),"Request failed try again");
            return;
        }
        if(temp.Code==200){
            Navigation.findNavController(profile_creation.v1).navigate(R.id.action_profile_creation_to_social_pref_interest2);
        }else{
            ToastHelper.ToastFun(profile_creation.v1.getContext(),temp.Message);
        }

    }
}
