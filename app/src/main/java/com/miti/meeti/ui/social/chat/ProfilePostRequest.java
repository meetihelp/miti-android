package com.miti.meeti.ui.social.chat;

import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.AllUrl;
import com.miti.meeti.NetworkObjects.GetProfile;
import com.miti.meeti.R;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;

public class ProfilePostRequest extends POSTRequest {
    private Gson gson=new Gson();
    public static void getprofile(String userid){
        Gson gson=new Gson();
        String response=gson.toJson(new GetProfile().new request_body(userid));
        ProfilePostRequest temp=new ProfilePostRequest();
        temp.execute(AllUrl.url_profile().get(3),response, MainActivity.cookieViewModel.getCookie1());
    }
    private void setData(GetProfile.response_object temp, View v){
        TextView t=v.findViewById(R.id.b_InterestIndoorPassive1);
        t.setText(temp.ProfileResponse.InterestIndoorPassive1);
        t=v.findViewById(R.id.b_InterestIndoorPassive2);
        t.setText(temp.ProfileResponse.InterestIndoorPassive2);
        t=v.findViewById(R.id.b_InterestIndoorActive1);
        t.setText(temp.ProfileResponse.InterestIndoorActive1);
        t=v.findViewById(R.id.b_InterestIndoorActive2);
        t.setText(temp.ProfileResponse.InterestIndoorActive2);
        t=v.findViewById(R.id.b_InterestOutdoorPassive1);
        t.setText(temp.ProfileResponse.InterestIndoorPassive1);
        t=v.findViewById(R.id.b_InterestOutdoorPassive2);
        t.setText(temp.ProfileResponse.InterestIndoorPassive2);
        t=v.findViewById(R.id.b_InterestOutdoorActive1);
        t.setText(temp.ProfileResponse.InterestOutdoorActive1);
        t=v.findViewById(R.id.b_InterestOutdoorActive2);
        t.setText(temp.ProfileResponse.InterestOutdoorActive2);
        t=v.findViewById(R.id.b_InterestOthers1);
        t.setText(temp.ProfileResponse.InterestOthers1);
        t=v.findViewById(R.id.b_InterestOthers2);
        t.setText(temp.ProfileResponse.InterestOthers2);
    }
    private void setData1(GetProfile.response_object temp, View v){
        TextView t=v.findViewById(R.id.b_Name);
        t.setText(temp.ProfileResponse.Name);
        t=v.findViewById(R.id.b_Sex);
        t.setText(temp.ProfileResponse.Sex);
        t=v.findViewById(R.id.b_Gender);
        t.setText(temp.ProfileResponse.Gender);
        t=v.findViewById(R.id.b_DateOfBirth);
        t.setText(temp.ProfileResponse.DateOfBirth);
        t=v.findViewById(R.id.b_Job);
        t.setText(temp.ProfileResponse.Job);
    }
    @Override
    protected void onPostExecute(RequestHelper result) {
        String response=result.getData();
        if(response==null){
            return;
        }
        GetProfile.response_object temp=gson.fromJson(response,GetProfile.response_object.class);
        if(temp==null){
            return;
        }
        if(temp.Code==2001){
            View v=BottomDialogFragment.view;
            setData(temp,v);
        }else if(temp.Code==200){
            View v=BottomDialogFragment.view;
            setData1(temp,v);
            setData(temp,v);
        }
        BottomDialogFragment.p.setVisibility(View.GONE);
    }
}
