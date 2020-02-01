package com.miti.meeti.ui.social.pooling;

import android.os.Handler;
import android.view.View;

import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.AllUrl;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.miti.meeti.mitiutil.try123;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;

public class GetInGroupPool extends POSTRequest {
    public static class request{
        public String Interest;
        public String RequestId;
        public request(String interest){
            this.Interest=interest;
            this.RequestId= try123.randomAlphaNumeric(32);
        }
    }
    public class response{
        public Integer Code;
        public String Message;
    }
    public static void helper(String interest){
        GetInGroupPool getInGroupPool=new GetInGroupPool();
        Gson gson=new Gson();
        try {
            getInGroupPool.execute(AllUrl.url_social().get(0),gson.toJson(new request(interest)), MainActivity.MeetiCookie);
        }catch (Exception e){}
    }
    @Override
    protected void onPostExecute(RequestHelper result) {
        if(result!=null){
            response lk=new Gson().fromJson(result.getData(),response.class);
            if(lk==null){
                GroupPool.bottomSheetDialog.dismiss();
                ToastHelper.ToastFun(GroupPool.v.getContext(),"Try again");
                return;
            }
            if(lk.Code==200){
                GroupPool.bottomSheetDialog.dismiss();
                GroupPool.checkView.setVisibility(View.VISIBLE);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Navigation.findNavController(GroupPool.v).navigateUp();
                    }
                }, 1000);
            }else{
                GroupPool.bottomSheetDialog.dismiss();
                ToastHelper.ToastFun(GroupPool.v.getContext(),"Try again");
            }
        }else{
            GroupPool.bottomSheetDialog.dismiss();
            ToastHelper.ToastFun(GroupPool.v.getContext(),"Try again");
        }

    }
}
