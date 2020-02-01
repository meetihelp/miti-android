package com.miti.meeti.ui.social.messageRequest;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.AllUrl;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.miti.meeti.mitiutil.try123;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;

public class AcceptRejectPOST extends POSTRequest {
    public static class request{
        public String Phone;
        public String Action;
        public String RequestId;
        public request(){}
        public request(String phone,String action){
            this.Phone=phone;
            this.Action=action;
            this.RequestId= try123.randomAlphaNumeric(32);
        }
    }
    public static void helper(String phone,String action){
        Gson gson=new Gson();
        String req=gson.toJson(new request(phone,action));
        AcceptRejectPOST temp=new AcceptRejectPOST();
        temp.execute(AllUrl.url_chat().get(8),req, MainActivity.MeetiCookie);
    }
    @Override
    protected void onPostExecute(RequestHelper result) {
        AcceptReject.bottomSheetDialog.dismiss();
        if(result==null){
            ToastHelper.ToastFun(AcceptReject.myContext,"Try again");
        }else{
            String response=result.getData();
            if(response==null){
                ToastHelper.ToastFun(AcceptReject.myContext,"Try again");
            }else if(response.contains("200")){
                ToastHelper.ToastFun(AcceptReject.myContext,"Done");
            }
        }
    }
}
