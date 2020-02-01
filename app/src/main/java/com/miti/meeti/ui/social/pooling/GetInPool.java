package com.miti.meeti.ui.social.pooling;

import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.AllUrl;
import com.miti.meeti.mitiutil.network.GETRequest;
import com.miti.meeti.mitiutil.network.Keyvalue;
import com.miti.meeti.mitiutil.network.RequestHelper;

public class GetInPool extends GETRequest {
    public class pool_object{
        public String PoolStatus;
        public String MatchUserId;
        public String Status;
    }
    public class response{
        public Integer Code;
        public String Message;
        public String IPIP;
        public pool_object PoolStatus;
    }
    public static RequestHelper helper(){
        GetInPool getRequest=new GetInPool();
        try{
            RequestHelper requestHelper=getRequest.execute(Keyvalue.GetHashMap(new Keyvalue("url","/"+ AllUrl.url_social().get(1)),
                    new Keyvalue("Miti-Cookie", MainActivity.MeetiCookie))).get();
            return requestHelper;
        }catch (Exception e){
            return null;
        }

    }
}
