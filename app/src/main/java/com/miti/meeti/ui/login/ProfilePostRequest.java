package com.miti.meeti.ui.login;

import androidx.navigation.Navigation;

import com.miti.meeti.R;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;

public class ProfilePostRequest extends POSTRequest {
    @Override
    protected void onPostExecute(RequestHelper result) {
        if(result==null){
            ToastHelper.ToastFun(profile_creation.v1.getContext(),"Request failed try again");
        }else if(result.getData().contains("200")){
            Navigation.findNavController(profile_creation.v1).navigate(R.id.action_profile_creation_to_social_pref_interest2);
        }
    }
}
